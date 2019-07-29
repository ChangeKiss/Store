package com.Store.www.ui.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.IconEditRequest;
import com.Store.www.entity.MyPayContentResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.commom.GlideCircleTransform;
import com.Store.www.ui.imageManager.ImageLoader;
import com.Store.www.ui.imageManager.ImgSelActivity;
import com.Store.www.ui.imageManager.ImgSelConfig;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心界面
 */
public class MyCenterActivity extends BaseToolbarActivity implements DialogHint.OnDialogOneButtonClickListener{
    @BindView(R.id.iv_alter_icon)
    CircleImageView mIvAlterIcon;  //头像
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;  //代理名
    @BindView(R.id.tv_grade_number)
    TextView mTvGradeNumber;  //代理等级
    @BindView(R.id.tv_shape_wear_grade)
    TextView mTvShapeWearGrade;  //代理塑身衣等级
    @BindView(R.id.tv_agency_number)
    TextView mTvAgencyNumber;  //代理编号
    @BindView(R.id.tv_my_phone)
    TextView mTvMyPhone;   //手机号码
    @BindView(R.id.tv_my_email)
    TextView mTvMyEmail; //邮箱
    @BindView(R.id.tv_my_wxi)
    TextView mTvMyWxi;  //微信
    @BindView(R.id.tv_my_pay) //收款资料
    TextView mTvMyPay;
    @BindView(R.id.layout_wxi)
    LinearLayout mLayoutWxi;  //点击修改微信
    @BindView(R.id.layout_agency_lv)
    LinearLayout mLayoutAgencyLv;  //代理等级提升

    private File tempFile;
    private String cropImagePath; //路径
    private String mIcon; //头像
    private String mPayContent,payContent;  //收款资料,修改的收款资料
    private String Wxi;
    static final int ICON_ALBUM = 1;
    static final int ICON_CAMERA = 2;
    static final int CROP_CAMERA = 4;
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIuLid3ovvgdGR";
    private static String accessKeySecret = "H5wC17dWuciVtLYjjx5SRoaucyRMbI";
    private static String bucketName = "fuatee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_center;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.user_center);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
        mTvUserName.setText(UserPrefs.getInstance().getUserName());
        mTvGradeNumber.setText(UserPrefs.getInstance().getLevel());
        mTvAgencyNumber.setText(UserPrefs.getInstance().getCode());
        mTvMyPhone.setText(UserPrefs.getInstance().getPhone());
        mTvMyEmail.setText(UserPrefs.getInstance().getEmail());
        Wxi = UserPrefs.getInstance().getWxi();
        Glide.with(this)
                .load(UserPrefs.getInstance().getIcon())
                .error(R.mipmap.default_head)
                .transform(new GlideCircleTransform(this))
                .into(mIvAlterIcon);
        mTvMyWxi.setText(UserPrefs.getInstance().getWxi());
        /*if (UserPrefs.getInstance().getCorsetLevelName()!=null){
            mTvShapeWearGrade.setText(UserPrefs.getInstance().getCorsetLevelName());
        }else {
            mTvShapeWearGrade.setText("暂无塑身衣等级");
        }*/
        getMyPay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    //弹出提示框选择相机还是相册来修改头像
    private void showIconEdit() {
        final View iconEditView = LayoutInflater.from(this).inflate(R.layout.dialog_icon_edit, null);
        TextView cameraView = (TextView) iconEditView.findViewById(R.id.tv_information_edit_camera);
        TextView albumView = (TextView) iconEditView.findViewById(R.id.tv_information_edit_album);
        TextView cancelView = (TextView) iconEditView.findViewById(R.id.tv_information_edit_cancel);
        final Dialog iconDialog = DialogHint.showDialogFromBottom(this, iconEditView);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconDialog.dismiss();
            }
        });
        //点击拍摄上传
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ActivityUtils.cameraIsCanUse()){  //点击开启相机时判断用户是否开启了相机权限，没开启就弹窗提示
                    DialogHint.showDialogWithOneButton(MyCenterActivity.this,R.string.please_open_camera,MyCenterActivity.this);
                }else {
                    showCameraAction();
                    iconDialog.dismiss();
                }
            }
        });

        //点击从相册上传
        albumView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgSelConfig config = new ImgSelConfig.Builder(MyCenterActivity.this, loader)
                        // 是否多选
                        .multiSelect(false)
                        .title("选择照片")
                        // 确定按钮背景色
                        //.btnBgColor(Color.parseColor(""))
                        // 确定按钮文字颜色
                        .btnTextColor(Color.WHITE)
                        // 使用沉浸式状态栏
                        .statusBarColor(0xff2b2b2b)
                        // 返回图标ResId
                        .backResId(R.mipmap.back_icon)
                        .title("选择照片")
                        .titleColor(Color.WHITE)
                        .titleBgColor(0xff2b2b2b)
                        .allImagesText("更多图片")
                        .needCrop(true)
                        .cropSize(1, 1, 200, 200)
                        // 第一个是否显示相机
                        .needCamera(false)
                        // 最大选择图片数量
                        .maxNum(1)
                        .build();

                ImgSelActivity.startActivity(MyCenterActivity.this, config, ICON_ALBUM);
                iconDialog.dismiss();
            }
        });
    }

    //展示相机
    private void showCameraAction() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            tempFile = new File(FileUtils.createRootPath(this), System.currentTimeMillis() + ".jpg");
            if (!tempFile.exists()) {
                tempFile.getParentFile().mkdirs();
            }
            Uri iconUri = Uri.fromFile(tempFile);
            if (iconUri != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
            }
            startActivityForResult(cameraIntent, ICON_CAMERA);
        } else {
            Toast.makeText(this, getString(R.string.open_camera_failure), Toast.LENGTH_SHORT).show();
        }
    }


    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };


    private void refreshUserInfo(int type) {
        switch (type) {
            case 4:
                Glide.with(this)
                        .load(mIcon)
                        .error(R.mipmap.default_head)
                        .transform(new GlideCircleTransform(this))
                        .into(mIvAlterIcon);
                UserPrefs.getInstance().setIcon(mIcon);
                break;
        }
    }


    //选择完成后展示图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case ICON_ALBUM:
                    if (resultCode == RESULT_OK && data != null) {
                        List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                        for (String path : pathList) {
                            postPictureToOSS(path);
                        }
                    }
                    break;
                case ICON_CAMERA:
                    if (tempFile != null) {
                        crop(tempFile.getPath());
                    }
                    break;
                case CROP_CAMERA:
                    postPictureToOSS(cropImagePath);
                    break;
            }
        }
    }

    //把头像上传到OSS
    private void postPictureToOSS(String path) {
        LogUtils.e("postPictureToOSS");
        final long mObjectId = System.currentTimeMillis();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, mObjectId + ".jpg", path);

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.d("UploadSuccess");
                LogUtils.d("图片==" + "http://fuatee.oss-cn-hangzhou.aliyuncs.com/" + mObjectId);
                postIconChange("http://fuatee.oss-cn-hangzhou.aliyuncs.com/" + mObjectId+"jpg");

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.d("ErrorCode=" + serviceException.getErrorCode());
                    LogUtils.d("RequestId=" + serviceException.getRequestId());
                    LogUtils.d("HostId=" + serviceException.getHostId());
                    LogUtils.d("RawMessage=" + serviceException.getRawMessage());
                }
            }
        });

    }

    //修改的头像传到服务器
    private void postIconChange(final String headPicture) {
        LogUtils.e("postIconChange=" + headPicture);
        IconEditRequest iconEditRequest = new IconEditRequest(mUserId, headPicture);
        RetrofitClient.getInstances().requestIconEdit(iconEditRequest).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                LogUtils.d("返回值===" + bean.getReturnValue());
                switch (bean.getReturnValue()) {
                    case 1:
                        showToast(R.string.hint_revise_success);
                        mIcon = headPicture;
                        refreshUserInfo(4);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }


            }
        });
    }


    /**
     * 调用系统的裁剪功能,改成裁剪大图的方法，传uri
     */
    private void crop(String imagePath) {
        File file = new File(FileUtils.createRootPath(this) + "/" + System.currentTimeMillis() + ".jpg");
        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_CAMERA);
    }

    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @OnClick({R.id.layout_pay_content,R.id.layout_alter_icon,R.id.layout_wxi,R.id.layout_phone_number,R.id.layout_agency_lv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_pay_content: //收款资料的点击事件
//                mActivityUtils.startActivity(AlterMaterialActivity.class,"hintContent",payContent);
                Intent alterPay = new Intent(MyCenterActivity.this,AlterMaterialActivity.class);
                alterPay.putExtra("hintContent",payContent);
                alterPay.putExtra("type","alterPay");
                startActivity(alterPay);
                break;
            case R.id.layout_alter_icon:  //修改头像的点击事件
                showIconEdit();
                break;
            case R.id.layout_wxi://微信的点击事件
                Intent alterWex = new Intent(MyCenterActivity.this,AlterMaterialActivity.class);
                alterWex.putExtra("Wxi",Wxi);
                alterWex.putExtra("type","alterWxi");
                startActivity(alterWex);
                break;
            case R.id.layout_phone_number:  //修改手机号码的点击事件
                mActivityUtils.startActivity(ChangePhoneActivity.class);
                break;
            case R.id.layout_agency_lv:
                mActivityUtils.startActivity(AgencyUpDataActivity.class,"title","代理等级");  //
                break;

        }
    }

    //获取自己的收款信息
    private void getMyPay(){
        RetrofitClient.getInstancesTest().getMyPay(mUserId).enqueue(new UICallBack<MyPayContentResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(MyPayContentResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            mPayContent = bean.getData();
                            payContent = mPayContent;
                            mTvMyPay.setText(mPayContent);
                        }
                        break;
                    case 2:
                        if (isTop){
                            mPayContent = bean.getErrMsg();
                            mTvMyPay.setText(mPayContent);
                        }
                        break;

                }
            }
        });
    }

    //一个按钮的弹窗确认点击事件
    @Override
    public void setOnDialogOkButtonClickListener(android.app.AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //一个按钮的弹窗确认点击事件
    @Override
    public void setOnDialogOkButtonClickListener(android.app.AlertDialog dialog, String titleId) {
        dialog.dismiss();
    }
}
