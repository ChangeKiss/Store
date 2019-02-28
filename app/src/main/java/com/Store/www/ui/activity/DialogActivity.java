package com.Store.www.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.Store.www.MyReceive;
import com.Store.www.R;
import com.Store.www.base.RoundAngleImageView;
import com.Store.www.utils.UserPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * dialog的Activity 可作为全局dialog弹出
 */
public class DialogActivity extends Activity {
    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    @BindView(R.id.ll_dialog)
    LinearLayout mLlDialog;  //包裹的布局
    @BindView(R.id.iv_message_dialog)
    RoundAngleImageView mIvMessageDialog;  //展示图片
    @BindView(R.id.iv_message_close)
    ImageView mIvMessageClose;   //关闭按钮
    private String imageUrl;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imageUrl = getIntent().getStringExtra("img_url");
        params = (LinearLayout.LayoutParams) mIvMessageDialog.getLayoutParams();
        params .width = UserPrefs.getInstance().getWidth()*350/375;
        params.height = UserPrefs.getInstance().getWidth()*480/375;
        mIvMessageDialog.setLayoutParams(params);
        if (!TextUtils.isEmpty(imageUrl)){
            Glide.with(this).load(imageUrl).into(mIvMessageDialog);
        }

    }

    //点击事件
    @OnClick({R.id.iv_message_dialog, R.id.ll_dialog, R.id.iv_message_close, R.id.ll_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message_dialog:
                //CommonWebActivity.startWebActivity(MyApplication.getmContext(), MyReceive.ad_Url);
                Intent intent = new Intent(DialogActivity.this,CommonWebActivity.class);
                intent.putExtra("url",MyReceive.ad_Url);
                intent.putExtra("type","common");
                startActivity(intent);
                finish();
                break;
            case R.id.ll_dialog:

                break;
            case R.id.iv_message_close:
                finish();
                break;
            case R.id.ll_root:

                break;
        }
    }
}
