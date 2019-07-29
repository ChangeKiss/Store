package com.Store.www.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.Store.www.entity.AgentApplyRequest;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.AgentApplyAdapter;
import com.Store.www.ui.imageManager.ImageLoader;
import com.Store.www.ui.imageManager.ImgSelActivity;
import com.Store.www.ui.imageManager.ImgSelConfig;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.CompressImage;
import com.Store.www.utils.FileUtils;
import com.Store.www.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 代理商申请界面
 */
public class AgentApplyActivity extends BaseToolbarActivity implements TextWatcher, AgentApplyAdapter.OnImageClickListener {
    @BindView(R.id.et_agent_QQ)
    EditText mEtAgentQQ;
    @BindView(R.id.et_agent_wxi)
    EditText mEtAgentWxi;
    @BindView(R.id.et_agent_wbo)
    EditText mEtAgentWbo;
    @BindView(R.id.et_agent_card)
    EditText mEtAgentCard;
    @BindView(R.id.et_agent_email)
    EditText mEtAgentEmail;
    @BindView(R.id.et_agent_feel)
    EditText mEtAgentFeel;
    @BindView(R.id.rl_image)
    RecyclerView mRy;
    @BindView(R.id.btn_apply)
    Button mBtnApply;

    private ProgressDialog mProgressDialog;
    AgentApplyAdapter mAdapter;
    private static final int IMAGE_MULTI_SELECTOR = 1;
    private String QQNumber, WxiNumber, WboNumber, CardNumber,mEmail, FellText;
    List<String> mImageData = new ArrayList<>();    //本地图片集合
    List<String> mImagePostData = new ArrayList<>();    //上传到OSS的图片集合
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIuLid3ovvgdGR";
    private static String accessKeySecret = "H5wC17dWuciVtLYjjx5SRoaucyRMbI";
    private static String bucketName = "fuatee";
    private String mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/compress";
    private File oldFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_agent_apply;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.apply_agent);
        mEtAgentQQ.addTextChangedListener(this);
        mEtAgentWxi.addTextChangedListener(this);
        mEtAgentWbo.addTextChangedListener(this);
        mEtAgentCard.addTextChangedListener(this);
        mEtAgentEmail.addTextChangedListener(this);
        mEtAgentFeel.addTextChangedListener(this);
        initImageAdapter();

    }

    //初始化图片适配器
    private void initImageAdapter() {
        mImageData.add("显示加号");//为0设置任意数据，0设置成加号
        mAdapter = new AgentApplyAdapter(this, this);
        mAdapter.setDataList(mImageData);
        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRy.setLayoutManager(manager);
        mRy.setAdapter(mAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入文本框监听
    @Override
    public void afterTextChanged(Editable s) {
        QQNumber = mEtAgentQQ.getText().toString().trim();
        WxiNumber = mEtAgentWxi.getText().toString().trim();
        WboNumber = mEtAgentWbo.getText().toString().trim();
        CardNumber = mEtAgentCard.getText().toString().trim();
        mEmail = mEtAgentEmail.getText().toString().trim();
        FellText = mEtAgentFeel.getText().toString().trim();
        boolean isEnabled = !TextUtils.isEmpty(QQNumber) && !TextUtils.isEmpty(WxiNumber) && !TextUtils.isEmpty(CardNumber);
        mBtnApply.setEnabled(isEnabled);
    }

    //添加的图片的点击事件
    @Override
    public void setOnImageClickListener(View view, int position) {
        if (position == 0) {
            showImageSelector();//调出图片选择器
        } else {
            //大图预览选择的图片
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
            List<String> photoUrls = new ArrayList<>();
            for (int i = 1; i < mImageData.size(); i++) {
                photoUrls.add(mImageData.get(i));

            }
            ImagePagerActivity.startImagePagerActivity(this, photoUrls, position - 1, imageSize);
        }
    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).error(R.mipmap.jzz_img).into(imageView);
        }
    };


    //把照片上传到OSS
    private void postPictureToOSS(String path) {
        LogUtils.e("postPictureToOSS+path=" + path);
        final long mObjectId = System.currentTimeMillis();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, mObjectId + ".jpg", path);

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.e("UploadSuccess");
                mImagePostData.add("http://fuatee.oss-cn-hangzhou.aliyuncs.com/"+mObjectId + ".jpg");
                LogUtils.e("mObjectId" + mObjectId);
                if (mImagePostData.size() == mImageData.size() - 1) {
                    mProgressDialog.dismiss();
                    //发起申请代理请求
                    requestAgent(QQNumber,userId,WxiNumber,WboNumber,CardNumber,mEmail,FellText,mImagePostData);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                mProgressDialog.dismiss();
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    mActivityUtils.showToast("请检查网络配置");
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.e("ErrorCode=" + serviceException.getErrorCode());
                    LogUtils.e("RequestId=" + serviceException.getRequestId());
                    LogUtils.e("HostId=" + serviceException.getHostId());
                    LogUtils.e("RawMessage=" + serviceException.getRawMessage());
                    mActivityUtils.showToast(serviceException.getRawMessage());
                }
            }
        });
    }

    //图片选择器
    private void showImageSelector() {
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                .btnText("确定")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#dcdcdc"))
                // 返回图标ResId
                .backResId(R.mipmap.back_icon)
                .title("选择照片")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#dcdcdc"))
                .allImagesText("更多图片")
                .needCrop(false)
                // 第一个是否显示相机
                .needCamera(false)
                // 最大选择图片数量
                .maxNum(7 - mImageData.size())
                .build();
        ImgSelActivity.startActivity(this, config, IMAGE_MULTI_SELECTOR);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_MULTI_SELECTOR:
                    if (resultCode == RESULT_OK && data != null) {
                        List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                        for (String path : pathList) {
                            mImageData.add(path);
                            LogUtils.d("path" + path);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

    //删除的点击事件
    @Override
    public void setOnDeleteClickListener(int position) {
        mImageData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    //申请代理的点击事件
    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        if (CardNumber.length()<18){
            showToast(R.string.card_number_hint);
            return;
        }
        mActivityUtils.hideSoftKeyboard();
        mImagePostData.clear();//把要传给服务器的图片集合先设空，在oss中会重新赋值
        if (mImageData.size() == 1) { //没有照片
            //直接发起代理申请
            requestAgent(QQNumber,userId,WxiNumber,WboNumber,CardNumber,mEmail,FellText,mImagePostData);
        }else {
            //上传图片到OSS
            FileUtils.createDir(mDirPath);  //先创建文件夹，否则会报错
            mProgressDialog = ProgressDialog.show(AgentApplyActivity.this, "", "上传中，请稍候...");
            mProgressDialog.setCancelable(false);
            mBtnApply.setText(R.string.message_loding);
            mBtnApply.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i < mImageData.size(); i++) {
                        File compressFile = new File(mDirPath, "/" + System.currentTimeMillis() + ".jpg");

                        CompressImage.compressImage(mImageData.get(i), compressFile);
                        LogUtils.e("compressFile.length()=" + compressFile.length());
                        postPictureToOSS(compressFile.getPath());
                    }
                }
            }).start();
        }

    }

    //发起代理申请请求
    private void requestAgent(String QQ,String userid,String wXi,String Wbo,String Card,
                              String email,String fell,List<String> imagePostData){
        AgentApplyRequest request = new AgentApplyRequest(QQ,userid,wXi,Wbo,Card,email,fell,imagePostData);
        RetrofitClient.getInstances().requestAgentApply(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast(R.string.agent_loding);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                }
            }
        });
    }


}
