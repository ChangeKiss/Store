package com.Store.www.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BankCardListResponse;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.DeleteBankCardRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.SelectBankCardAdapter;
import com.Store.www.ui.commom.DialogHint;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择银行卡界面
 */
public class SelectBankCardActivity extends BaseToolbarActivity implements SelectBankCardAdapter.OnClickListener,
        BaseToolbarActivity.OnToolBarRightClickListener,DialogHint.OnDialogTwoButtonClickListener {
    @BindView(R.id.iv_toolbar_right)
    TextView mTvToolbarRight;  //标题栏右侧的添加银行卡
    @BindView(R.id.rv_bank_card)
    RecyclerView mRv;

    SelectBankCardAdapter mAdapter;
    private int mSelectId;
    private String mType;
    private final int SELECT_CARD = 2;
    private int mCardId,backCardId;
    private boolean mIsSelect;
    private boolean noSelect = false;  //没有选中的了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_select_bank_card;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);  //将次界面添加至管理器中
        initToolbar(this, true, R.string.add_bank_card,this);
        mTvToolbarRight.setVisibility(View.VISIBLE);
        mTvToolbarRight.setText(R.string.add);
        mSelectId = getIntent().getIntExtra("selectId", 0);  //选择的银行卡ID
        mType = getIntent().getStringExtra("type");
        initAdapter();
    }

    //初始化适配器
    private void initAdapter() {
        mAdapter = new SelectBankCardAdapter(this, this);
        mRv.setLayoutManager(new LinearLayoutManager(this));  //添加布局管理器
        mRv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCardList();
    }

    //获取银行卡信息
    private void getCardList() {
        RetrofitClient.getInstances().getBankCard(mUserId).enqueue(new UICallBack<BankCardListResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BankCardListResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mAdapter.getDataList().clear();
                        mAdapter.addAll(bean.getData());
                        if (mIsSelect){  //这个 参数是删除的时候的参数  如果是删除的被选中的
                            Intent intent = new Intent();  //发送广播
                            intent.setAction("bankCard");
                            sendBroadcast(intent);  //向注册广播的界面发送一个广播 更新数据
                            mSelectId = mAdapter.getDataList().get(0).getCardId();  //将第一张卡片设置为被选中的
                        }
                        mAdapter.setSelectId(mSelectId);
                        mAdapter.setmType(mType);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case 8:  //8 无数据
                        LogUtils.d("无数据 发送广播");
                        mAdapter.getDataList().clear();
                        mAdapter.notifyDataSetChanged();
                        showToast(bean.getErrMsg());
                        Intent intent = new Intent();  //发送广播
                        intent.setAction("bankCard");
                        sendBroadcast(intent);  //向注册广播的界面发送一个广播 更新数据
                        break;
                    default:
                        mAdapter.getDataList().clear();
                        mAdapter.notifyDataSetChanged();
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //item的点击事件
    @Override
    public void OnItemClickListener(int position, String cardName, String cardNumber, int cardId,int cardType,int isCardNumber) {
        Intent intent = new Intent();
        intent.putExtra("cardId", cardId);
        intent.putExtra("name", cardName);
        intent.putExtra("cardNumber", cardNumber);
        intent.putExtra("cardType",cardType);
        intent.putExtra("isCardNumber",isCardNumber);
        setResult(SELECT_CARD, intent);
        finish();
    }

    //长按删除银行卡
    @Override
    public void OnLongClickListener(int position, int cardId,boolean isSelect) {
        mIsSelect = isSelect;
        mCardId = cardId;
        DialogHint.showDialogWithTwoButton(this,R.string.delete_bank_card_ok_no,this);
    }

    //弹窗确认的点击事件
    @Override
    public void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId) {
        requestDeleteCard(mCardId);
        dialog.dismiss();
    }

    //弹窗取消的点击事件
    @Override
    public void setOnDialogCancelButtonClickListener(AlertDialog dialog) {
        dialog.dismiss();
    }

    //请求删除银行卡
    private void requestDeleteCard(int cardId){
        DeleteBankCardRequest request = new DeleteBankCardRequest(cardId);
        RetrofitClient.getInstances().requestDeleteBankCard(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast("删除成功");
                        LogUtils.d("长度=="+mAdapter.getDataList().size());
                        for (int i=0;i<mAdapter.getDataList().size();i++){
                            LogUtils.d("第"+i+"张被选中？"+mAdapter.getDataList().get(i).isSelect());
                            if (mAdapter.getDataList().size()>1&& mAdapter.getDataList().get(i).isSelect()){  //如果有选中的
                                LogUtils.d("01是否选中="+mIsSelect);
                                if (mIsSelect){
                                    mSelectId = mAdapter.getDataList().get(0).getCardId();  //将第一张卡片设置为被选中的
                                }else {
                                    LogUtils.d("02是否选中="+mIsSelect);
                                    mSelectId = mAdapter.getDataList().get(i).getCardId();  //将选中的卡片设置为被选中的
                                }
                            }else if (mAdapter.getDataList().size()==1&&!mAdapter.getDataList().get(i).isSelect()){  //如果没有选中的
                                mSelectId = mAdapter.getDataList().get(0).getCardId();  //将第一张卡片设置为被选中的
                            }
                        }
                        onResume();
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    @OnClick({R.id.iv_toolbar_left, R.id.iv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_left:
                break;
        }
    }


    @Override
    public void setOnToolBarRightClickListener() {
        mActivityUtils.startActivity(AddBankCardActivity.class);
    }


}
