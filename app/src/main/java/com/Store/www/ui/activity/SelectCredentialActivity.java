package com.Store.www.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.JudgeCredentialResponse;
import com.Store.www.entity.ShapeWearCredentialResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.utils.ActivityCollector;

import butterknife.OnClick;

/**
 * 选择证书界面
 */
public class SelectCredentialActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_select_credential;
    }

    @Override
    public void initView() {
        initToolbar(this, true, R.string.my_certificate);
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isTop = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isTop =true;
    }

    @OnClick({R.id.layout_shape_wear_credential, R.id.layout_bar_credential})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_shape_wear_credential:  //塑身衣点击证书
                judgeShapeWearCredential(mUserId);
                break;
            case R.id.layout_bar_credential:  //内衣证书
                judgeBraCredential(mUserId);
                break;
        }
    }

    /**
     * 判断用户是否有内衣证书以及是否有资格生成证书
     * @param userId
     */
    private void judgeBraCredential(int userId) {
        RetrofitClient.getInstances().requestJudge(userId).enqueue(new UICallBack<JudgeCredentialResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(JudgeCredentialResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        if (isTop){
                            Intent intent = new Intent(mContext,MyCredentialActivity.class);
                            intent.putExtra("type","bra");
                            intent.putExtra("title","内衣证书");
                            intent.putExtra("isCert",bean.getIsCert());
                            intent.putExtra("cert",bean.getCert());
                            startActivity(intent);
                        }
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }

            }
        });
    }


    /**
     * 判断用户是否有塑身衣证书以及生成证书资格
     * @param userId
     */
    private void judgeShapeWearCredential(int userId){
        RetrofitClient.getInstances().getShapeWearCredential(userId).enqueue(new UICallBack<ShapeWearCredentialResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(ShapeWearCredentialResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            Intent intent = new Intent(mContext,MyCredentialActivity.class);
                            intent.putExtra("type","shapeWear");
                            intent.putExtra("title","塑身衣证书");
                            intent.putExtra("isCert",bean.getIsIShowCert());
                            intent.putExtra("cert",bean.getCert());
                            startActivity(intent);
                            break;
                        default:
                            showToast(bean.getErrMsg());
                            break;
                    }
                }
            }
        });
    }
}
