package com.Store.www.ui.activity;
/**
 * 发布圈子界面
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.Store.www.entity.PublishCircleRequest;
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
import com.Store.www.utils.TransCodingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PublishCircleActivity extends BaseToolbarActivity implements BaseToolbarActivity.OnToolBarRightClickListener,
        TextWatcher, AgentApplyAdapter.OnImageClickListener {
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;  //右上角发布
    @BindView(R.id.et_publish_content)
    EditText mEtPublishContent;
    @BindView(R.id.rv_publish_image)
    RecyclerView mRvPublishImage;
    private static final int IMAGE_MULTI_SELECTOR = 1;
    private String CircleContent;  //发布内容
    private int mType = 1; //发布时的类型
    private ProgressDialog mProgressDialog;
    AgentApplyAdapter mAdapter;
    List<String> mImageData = new ArrayList<>();    //本地图片集合
    List<String> mImagePostData = new ArrayList<>();    //上传到OSS的图片集合
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIuLid3ovvgdGR";
    private static String accessKeySecret = "H5wC17dWuciVtLYjjx5SRoaucyRMbI";
    private static String bucketName = "fuatee";
    private String mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/compress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_publish_circle;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, "",this);
        mTvToolbarRight.setVisibility(View.VISIBLE);
        mTvToolbarRight.setText(R.string.publish);
        mTvToolbarRight.setOnClickListener(this);
        mEtPublishContent.addTextChangedListener(this);
        initAdapter();
    }

    //初始化图片选择适配器
    private void initAdapter() {
        mImageData.add("显示加号");
        mAdapter = new AgentApplyAdapter(this, this);
        mAdapter.setDataList(mImageData);
        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvPublishImage.setLayoutManager(manager);
        mRvPublishImage.setAdapter(mAdapter);
    }

    //图片的点击事件
    @Override
    public void setOnImageClickListener(View view, int position) {
        if (position == 0) { //如果点击第0个时，添加图片
            //调出图片选择器
            openImageSelect();
        } else {  //否则查看大图
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
            List<String> photoUrls = new ArrayList<>();
            for (int i = 1; i < mImageData.size(); i++) {
                photoUrls.add(mImageData.get(i));
            }
            ImagePagerActivity.startImagePagerActivity(this, photoUrls, position - 1, imageSize);
        }

    }

    //删除图片的点击事件
    @Override
    public void setOnDeleteClickListener(int position) {
        mImageData.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    //打开图片选择器
    private void openImageSelect() {
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
                .maxNum(10 - mImageData.size())
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //输入文本框的监听
    @Override
    public void afterTextChanged(Editable s) {
        CircleContent = mEtPublishContent.getText().toString();
        LogUtils.d("输入内容=" + CircleContent);
    }

    //把照片上传到OSS
    private void postPictureToOSS(String path) {
        LogUtils.e("postPictureToOSS+path=" + path);
        final long mObjectId = System.currentTimeMillis();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, mObjectId + "", path);

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.e("UploadSuccess");
                mImagePostData.add("http://jwbucket.oss-cn-shanghai.aliyuncs.com/" + mObjectId + "");
                LogUtils.e("mObjectId" + mObjectId);
                LogUtils.d("本地图片集合长度==" + mImageData.size());
                LogUtils.d("OSS图片集合长度==" + mImagePostData.size());
                if (mImagePostData.size() == mImageData.size() - 1) {
                    mProgressDialog.dismiss();
                    //发起请求
                    requestPublishCircle(mImagePostData);
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

    //发起发布圈子请求
    private void requestPublishCircle(List<String> imageData) {
        PublishCircleRequest.CircleInfoBean infoBean = new PublishCircleRequest.CircleInfoBean();

        infoBean.setContent(TransCodingUtils.Encode(CircleContent)); //将字符串或者特殊标题编码发给服务器

        infoBean.setType(mType);
        infoBean.setImages(imageData);
        PublishCircleRequest request = new PublishCircleRequest(mUserId, infoBean);
        RetrofitClient.getInstances().requestPublish(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        showToast(R.string.publish_ok);
                        finish();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    @Override
    public void setOnToolBarRightClickListener() {
        FileUtils.createDir(mDirPath);  //先创建文件夹，否则会报错
        LogUtils.d("ImageDataSize=1=" + mImageData.size());
        if (CircleContent != null && mImageData.size() > 1) { //发图片和文字
            LogUtils.d("发文字和图片");
            mType = 1;
            mProgressDialog = ProgressDialog.show(PublishCircleActivity.this, "", "发布中请稍后...");
            mProgressDialog.setCancelable(false); //设置弹窗不可被取消
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("ImageDataSize=2=" + mImageData.size());
                    for (int i = 1; i < mImageData.size(); i++) {
                        File compressFile = new File(mDirPath, "/" + System.currentTimeMillis() + ".jpg");
                        CompressImage.compressImage(mImageData.get(i), compressFile);
                        LogUtils.e("compressFile.length()=" + compressFile.length());
                        postPictureToOSS(compressFile.getPath());
                    }
                }
            }).start();
        } else if (CircleContent != null && mImageData.size() == 1) {//只发文字
            LogUtils.d("只发文字");
            mType = 0;
            requestPublishCircle(mImagePostData);
        } else if (CircleContent == null && mImageData.size() > 1) { //只发图片
            LogUtils.d("只发图片");
            CircleContent = "";
            mType = 1;
            mProgressDialog = ProgressDialog.show(PublishCircleActivity.this, "", "发布中请稍后...");
            mProgressDialog.setCancelable(false); //设置弹窗不可被取消
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("ImageDataSize=2=" + mImageData.size());
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

    @OnClick({R.id.et_publish_content, R.id.rv_publish_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_publish_content:
                break;
            case R.id.rv_publish_image:
                break;
        }
    }
}
