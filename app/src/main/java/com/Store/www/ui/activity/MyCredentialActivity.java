package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.Store.www.entity.CreateCredentialRequest;
import com.Store.www.entity.CreateCredentialResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.ui.imageManager.ImageLoader;
import com.Store.www.ui.imageManager.ImgSelActivity;
import com.Store.www.ui.imageManager.ImgSelConfig;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的证书界面
 */
public class MyCredentialActivity extends BaseToolbarActivity implements DialogHint.OnDialogOneButtonClickListener{
    @BindView(R.id.layout_credential_image)
    LinearLayout mLayoutCredentialImage;  //图片最外层的布局
    @BindView(R.id.iv_my_credential)  //展示证书控件
    ImageView mIvMyCredential;
    @BindView(R.id.layout_select_head_image)
    LinearLayout mLayoutSelectHeadImage;  //上传证书布局
    @BindView(R.id.tv_select_image)
    TextView mTvSelectImage;  //点击选择图片
    @BindView(R.id.iv_select_head_image)
    ImageView mIvSelectHeadImage;  //展示选择的头像
    @BindView(R.id.btn_create)
    Button mBtnCreate;
    @BindView(R.id.layout_save_image)
    LinearLayout mLayoutSaveImage;  //布局

    PopupWindow mPopupWindow; //底部弹窗
    private int IsCert;//是否有证书
    private String cert; //证书
    private ProgressDialog mProgressDialog;
    private File tempFile;
    private String mTitle;  //界面标题
    private String mType;   //类型 塑身衣 还是内衣
    private String cropImagePath;
    private String mCredential; //头像
    static final int ICON_ALBUM = 1;
    static final int ICON_CAMERA = 2;
    static final int CROP_CAMERA = 4;
    private static String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIbM4qrWdSwCVw";
    private static String accessKeySecret = "GpUH1RaNphDi6U2vMqioHnHzDQHmrv";
    private static String bucketName = "jwbucket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_credential;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        mType = getIntent().getStringExtra("type");
        initToolbar(this, true, mTitle);
        IsCert = getIntent().getIntExtra("isCert",0);
        cert = getIntent().getStringExtra("cert");
        if (IsCert!=0){
            mLayoutSelectHeadImage.setVisibility(View.GONE);
            mLayoutCredentialImage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(cert).into(mIvMyCredential);
        }else {
            mLayoutCredentialImage.setVisibility(View.GONE);
            mLayoutSelectHeadImage.setVisibility(View.VISIBLE);
        }

        //设置图片的长按事件， 长按保存图片到本地
        mIvMyCredential.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSelectImage();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop = true;
    }

    //点击事件
    @OnClick({R.id.tv_select_image,R.id.btn_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_image: //弹出选择框
                showIconEdit();
                break;
            case R.id.btn_create://生成证书
                mProgressDialog = ProgressDialog.show(MyCredentialActivity.this,"","证书生成中请稍后...");
                mProgressDialog.setCancelable(false);
                mBtnCreate.setEnabled(false);
                if (!TextUtils.isEmpty(mType) && mType.equals("bra")){  //内衣证书
                    CreateCredential(mUserId,mCredential);
                }else if (!TextUtils.isEmpty(mType) && mType.equals("shapeWear")){  //塑身衣证书
                    LogUtils.d("生成塑身衣证书");
                    CreateShapeWearCredential(mUserId,mCredential);
                }
                break;
        }
    }

    //弹出底部选择保存图片
    private void showSelectImage(){
        View view = getLayoutInflater().inflate(R.layout.popwindow_select_image,null);
        TextView save = (TextView) view.findViewById(R.id.tv_save_image);
        TextView cancel = (TextView) view.findViewById(R.id.tv_select_cancel);
        mPopupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.showAtLocation(mLayoutSaveImage, Gravity.BOTTOM,0,0);
        save.setOnClickListener(new View.OnClickListener() {  //保存图片的点击事件
            @Override
            public void onClick(View v) {
                mIvMyCredential.setDrawingCacheEnabled(true);
                Bitmap imageBitmap = mIvMyCredential.getDrawingCache();
                if (imageBitmap != null) {
                    //把图片保存到本地
                    saveImageToGallery(mContext,imageBitmap);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() { //取消的点击事件
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }




    /**
     * 保存图片到图库
     * @param context
     * @param bmp
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "/Kivie");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context,"保存失败",Toast.LENGTH_LONG);
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context,"保存失败",Toast.LENGTH_LONG);
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            showToast("保存成功");
            mPopupWindow.dismiss();
        } catch (FileNotFoundException e) {
            showToast("保存失败");
            mPopupWindow.dismiss();
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));
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
                if (!ActivityUtils.cameraIsCanUse()){  //如果相机权限未开启  弹出提示框 提醒用户开启相机权限
                    DialogHint.showDialogWithOneButton(MyCredentialActivity.this,R.string.please_open_camera,MyCredentialActivity.this);
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
                ImgSelConfig config = new ImgSelConfig.Builder(MyCredentialActivity.this, loader)
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
                        .cropSize(1,1, 500, 500)
                        // 第一个是否显示相机
                        .needCamera(false)
                        // 最大选择图片数量
                        .maxNum(1)
                        .build();

                ImgSelActivity.startActivity(MyCredentialActivity.this, config, ICON_ALBUM);
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

/*
    private void refreshUserInfo(int type) {
        switch (type) {
            case 4:
                Glide.with(this)
                        .load(mIcon)
                        .error(R.mipmap.default_head)
                        .transform(new GlideCircleTransform(this))
                        .into(mIvSelectHeadImage);
                UserPrefs.getInstance().setIcon(mIcon);
                break;
        }
    }*/


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
        //展示图片
        if (path!=null){
            mBtnCreate.setEnabled(true);
        }
        Glide.with(this).load(path).into(mIvSelectHeadImage);
        final long mObjectId = System.currentTimeMillis();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, mObjectId + "", path);

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.d("UploadSuccess");
                LogUtils.d("图片=="+"http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+mObjectId );
                /*postIconChange("http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+mObjectId);*/
                mCredential = "http://jwbucket.oss-cn-shanghai.aliyuncs.com/"+mObjectId;

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

    /**
     * 生成内衣证书网络请求
     * @param id
     * @param imagePath
     */
    private void CreateCredential(int id, final String imagePath){
        CreateCredentialRequest request = new CreateCredentialRequest(id,imagePath);
        RetrofitClient.getInstances().requestCreate(request).enqueue(new UICallBack<CreateCredentialResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
                mProgressDialog.dismiss();
                mBtnCreate.setEnabled(true);
            }

            @Override
            public void OnRequestSuccess(CreateCredentialResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            final String imageUrl = bean.getData();
                            mLayoutSelectHeadImage.setVisibility(View.GONE);
                            Glide.with(mContext).load(imageUrl).into(mIvMyCredential);
                            mLayoutCredentialImage.setVisibility(View.VISIBLE);
                            mIvMyCredential.setVisibility(View.VISIBLE);
                            mProgressDialog.dismiss();
                            mBtnCreate.setEnabled(true);
                            break;
                        case 3:
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;
                        case 6:
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;
                        default:
                            mLayoutSelectHeadImage.setVisibility(View.VISIBLE);
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;

                    }
                }
            }
        });
    }

    /**
     * 生成塑身衣证书网络请求
     */
    private void CreateShapeWearCredential(int id, final String imagePath){
        CreateCredentialRequest request = new CreateCredentialRequest(id,imagePath);
        RetrofitClient.getInstances().requestCreateShapeWearCredential(request).enqueue(new UICallBack<CreateCredentialResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop){
                    checkNet();
                    mProgressDialog.dismiss();
                    mBtnCreate.setEnabled(true);
                }
            }

            @Override
            public void OnRequestSuccess(CreateCredentialResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            final String imageUrl = bean.getData();
                            mLayoutSelectHeadImage.setVisibility(View.GONE);
                            Glide.with(mContext).load(imageUrl).into(mIvMyCredential);
                            mLayoutCredentialImage.setVisibility(View.VISIBLE);
                            mIvMyCredential.setVisibility(View.VISIBLE);
                            mProgressDialog.dismiss();
                            mBtnCreate.setEnabled(true);
                            break;
                        case 3:
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;
                        case 6:
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;
                        default:
                            mLayoutSelectHeadImage.setVisibility(View.VISIBLE);
                            showToast(bean.getErrMsg());
                            mProgressDialog.dismiss();
                            finish();
                            break;

                    }
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

    //一个按钮的弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        dialog.dismiss();
    }

    //一个按钮的弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId) {
        dialog.dismiss();
    }
}
