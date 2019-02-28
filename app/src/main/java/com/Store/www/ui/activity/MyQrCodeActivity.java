package com.Store.www.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.Store.www.R;
import com.Store.www.base.AutoFitTextView;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.PictureUtil;
import com.Store.www.utils.UserPrefs;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MyQrCodeActivity extends BaseToolbarActivity {
    @BindView(R.id.iv_toolbar_right_share)
    ImageView mIvToolbarRightShare;  //右上角的分享
    @BindView(R.id.iv_my_code)
    ImageView mIvMyCode;  //二维码
    @BindView(R.id.tv_qr_code_hint)
    AutoFitTextView mTvQrCodeHint;

    LinearLayout.LayoutParams paramsTwo;
    private String CodeImageUrl; //二维码的链接
    private String ShareUrl,mTitle,mContext;  //分享的链接,标题，内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initLayout() {
        return R.layout.activity_my_qr_code;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.my_qr_code);
        mIvToolbarRightShare.setVisibility(View.VISIBLE);
        CodeImageUrl = getIntent().getStringExtra("codeUrl");
        ShareUrl = getIntent().getStringExtra("shareUrl");
        mTitle = getIntent().getStringExtra("title");
        mContext = getIntent().getStringExtra("context");
        LogUtils.d("二维码="+CodeImageUrl);
        //动态设置图片的宽高
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params;
                params = mIvMyCode.getLayoutParams();
                paramsTwo = (LinearLayout.LayoutParams) mTvQrCodeHint.getLayoutParams();
                params.width = (UserPrefs.getInstance().getWidth()/2);
                params.height = (UserPrefs.getInstance().getWidth()/2);
                paramsTwo.width = UserPrefs.getInstance().getWidth()/2;
                mTvQrCodeHint.setLayoutParams(paramsTwo);  //设置文本框的宽
                mIvMyCode.setLayoutParams(params);
                LogUtils.d("宽="+params.width);
                LogUtils.d("高="+params.height);
                Glide.with(MyQrCodeActivity.this).load(CodeImageUrl).into(mIvMyCode);
                mTvQrCodeHint.setVisibility(View.VISIBLE);
            }
        },500);
    }


    @OnClick({R.id.iv_toolbar_right_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_right_share: //分享
                showShare();
                break;
        }
    }

    //分享
    private void showShare() {
        String imagePath = PictureUtil.imagePath(MyQrCodeActivity.this, R.mipmap.program_icon, "Kivie");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(mTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mContext);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(imagePath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(ShareUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtils.d("回调成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtils.d("回调失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtils.d("回调取消");
            }
        });
        // 启动分享GUI
        oks.show(this);

    }
}
