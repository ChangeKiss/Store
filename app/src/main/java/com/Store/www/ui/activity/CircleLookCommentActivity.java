package com.Store.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.LookCommentResponse;
import com.Store.www.entity.NewCommentRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.LookCommentAdapter;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 圈子查看评论界面
 */
public class CircleLookCommentActivity extends BaseToolbarActivity implements OnLoadMoreListener, TextWatcher {
    @BindView(R.id.rv_circle)
    LRecyclerView mRvCircle;  //圈子评论列表
    @BindView(R.id.et_circle_comment)
    EditText mEtCircleComment;  //文本输入框
    @BindView(R.id.tv_circle_comment_send)
    TextView mTvCircleCommentSend;
    LookCommentAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private CommonHeader mCommonHeader;  //头布局
    TextView mTvCommentCount; //评论数量
    private int mCircleId;  //圈子ID
    private String mContent;
    private InputMethodManager methodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_circle_look_comment;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        initToolbar(this, true, R.string.look_comment);
        mCircleId = getIntent().getIntExtra("circleId", 0);
        mCommonHeader = new CommonHeader(this, R.layout.layou_circle_comment_head); //初始化头布局
        mTvCommentCount = (TextView) mCommonHeader.findViewById(R.id.tv_comment_count);
        initUtil();
        initAdapter();
        getCircleComment(mCircleId,mPageIndex);
    }


    /**
     * 初始化必须工具
     */
    private void initUtil() {
        //初始化输入法
        methodManager = (InputMethodManager) getSystemService(mContext.INPUT_METHOD_SERVICE);
    }

    //初始化适配器
    private void initAdapter() {
        mAdapter = new LookCommentAdapter(this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        viewAdapter.addHeaderView(mCommonHeader);
        mRvCircle.setLayoutManager(new LinearLayoutManager(this));
        mRvCircle.setAdapter(viewAdapter);
        mRvCircle.setOnLoadMoreListener(this);  //设置上拉加载
        mRvCircle.setPullRefreshEnabled(false); //设置不可下拉刷新
        mEtCircleComment.addTextChangedListener(this); //添加文本框输入监听
    }

    //获取圈子评论
    private void getCircleComment(int circleId, int pageIndex) {
        RetrofitClient.getInstances().getCircleComment(circleId, mCountPerPage, pageIndex).enqueue(new UICallBack<LookCommentResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(LookCommentResponse bean) {
                switch (bean.getReturnValue()) {
                    case 1:
                        mAdapter.addAll(bean.getData());
                        mTvCommentCount.setText("全部评论(" + bean.getNum() + ")");
                        mAdapter.notifyDataSetChanged();
                        mRvCircle.refreshComplete(mCountPerPage);
                        break;
                    case 8:
                        mRvCircle.setNoMore(true);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getCircleComment(mCircleId, mPageIndex);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //文本框输入的监听
    @Override
    public void afterTextChanged(Editable s) {
        mContent = mEtCircleComment.getText().toString();
        LogUtils.d("输入内容="+mContent);
        if (!TextUtils.isEmpty(mContent)) {
            mTvCircleCommentSend.setEnabled(true);
        } else {
            mTvCircleCommentSend.setEnabled(false);
        }
    }

    @OnClick({R.id.tv_circle_comment_send,R.id.et_circle_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_circle_comment_send://发送评论
                mEtCircleComment.setFocusable(false); //设置失去焦点
                requestNewComment(mUserId,mCircleId,mContent);
                if (methodManager.isActive()){ //如果输入法是活动的
                    methodManager.hideSoftInputFromWindow(mEtCircleComment.getWindowToken(),0);  //就隐藏输入法
                }
                break;
            case R.id.et_circle_comment:
                mEtCircleComment.setFocusable(true); //设置可获取焦点
                mEtCircleComment.setFocusableInTouchMode(true); //设置触摸聚焦
                mEtCircleComment.requestFocus();  //请求焦点
                mEtCircleComment.findFocus();      //获取焦点
                methodManager.showSoftInput(mEtCircleComment,InputMethodManager.SHOW_FORCED);//显示输入法
                break;
        }
    }

    //发起请求新增评论
    private void requestNewComment(int userId,int circleId,String content){
        NewCommentRequest request = new NewCommentRequest(userId,circleId,content);
        RetrofitClient.getInstances().requestNewComment(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        showToast("评论成功");
                        mAdapter.getDataList().clear();
                        mAdapter.notifyDataSetChanged();
                        getCircleComment(mCircleId,mPageIndex);
                        mEtCircleComment.setText("");
                        mEtCircleComment.setHint(R.string.comment_one);
                        break;
                    default:
                        showToast(bean.getErrMsg());
                        break;
                }
            }
        });
    }
}
