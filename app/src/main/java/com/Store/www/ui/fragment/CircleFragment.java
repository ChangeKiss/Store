package com.Store.www.ui.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Store.www.base.BaseLazyLoadFragment;
import com.Store.www.ui.activity.LoginActivity;
import com.Store.www.utils.TransCodingUtils;
import com.Store.www.utils.UserPrefs;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.Store.www.R;
import com.Store.www.base.BaseFragment;
import com.Store.www.entity.BaseBenTwo;
import com.Store.www.entity.CircleResponse;
import com.Store.www.entity.PraiseRequest;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.activity.CircleLookCommentActivity;
import com.Store.www.ui.activity.FeedBackActivity;
import com.Store.www.ui.activity.PublishCircleActivity;
import com.Store.www.ui.adapter.CircleAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.ShareTools;
import com.mob.MobSDK;
import com.sch.share.WXShareMultiImageHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 圈子碎片
 */

public class CircleFragment extends BaseLazyLoadFragment implements OnRefreshListener,OnLoadMoreListener,CircleAdapter.OnclickListener{
    @BindView(R.id.circle_lR)
    LRecyclerView mCircleLr;  //展示圈子的列表
    @BindView(R.id.nodata)
    RelativeLayout mNodata;
    @BindView(R.id.tv_nodata)
    TextView mTvNoData;
    Unbinder unbinder;

    CircleAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private List<File> files = new ArrayList<>(); //文件集合 用来分享图片的
    private List<Bitmap> imageList = new ArrayList<>(); //图片集合 用来分享图片的
    // 待分享图片。
    private  List<String> mImageList = new ArrayList<>();
    private Bitmap bitmap = null;
    private String shareContent; //分享的文本内容
    private ProgressDialog dialog;
    private String shareType = "share";


    @Override
    public void onLazyLoad() {
        if (TextUtils.isEmpty(UserPrefs.getInstance().getUserId())){
            mActivityUtils.startActivity(LoginActivity.class,"login", "login");
        }else {
            initAdapter();
        }
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_circle,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void initEvent() {
        requestStoragePermission();
        MobSDK.init(mContext,"245a466822227","a7a415e1869609aaeac6d8cdc08b7411");  //初始化MOB分享*****
    }


    /**
     * 申请内存权限
     */
    private void requestStoragePermission() {
        // 申请内存权限。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !WXShareMultiImageHelper.hasStoragePermission(mContext)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new CircleAdapter(mContext,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        mCircleLr.setLayoutManager(new LinearLayoutManager(mContext));
        mCircleLr.setAdapter(viewAdapter);
        mCircleLr.setOnRefreshListener(this); //下拉刷新
        mCircleLr.setOnLoadMoreListener(this);  //上拉加载
        getCircleList(mUserId,0,mCountPerPage,mPageIndex);
    }

    //请求圈子列表数据
    private void getCircleList(int userId,int type,int countPerPage,int pageIndex){
        if (pageIndex==1){
            DialogLoading.shows(mContext,R.string.hint_loading);
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getCircleList(userId,type,countPerPage,pageIndex).enqueue(new UICallBack<CircleResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(CircleResponse bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        mCircleLr.refreshComplete(mCountPerPage);
                        break;
                    case 8:
                        mCircleLr.setNoMore(true);
                        mPageIndex = 1;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("圈子的Resume");
        imageList.clear(); //将图片集合清空
        mImageList.clear();
        if (shareType.equals("loadingWeChar")&& dialog!=null&& dialog.isShowing()){ //分享类型为微信
            dialog.dismiss();
        }

    }

    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getCircleList(mUserId,0,mCountPerPage,mPageIndex);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex=1;
        getCircleList(mUserId,0,mCountPerPage,mPageIndex);
    }



    //发起点赞请求
    private void requestPraise(final int position,int userId, int circleId, final ImageView praiseImageView, final TextView praiseTextView, final int likeCount){
        PraiseRequest request = new PraiseRequest(userId,circleId);
        RetrofitClient.getInstances().requestPraise(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        int myCount = likeCount+1;
                        mAdapter.getDataList().get(position).setIsLike(1);
                        mAdapter.getDataList().get(position).setLikeCount(myCount);
                        praiseImageView.setImageResource(R.mipmap.family_dz_hover_icon);
                        praiseTextView.setText(myCount+"");
                        showToast("~Thanks♪(･ω･)ﾉ~");
                        break;
                }
            }
        });
    }

    //发起取消点赞请求
    private void requestPraiseCancel(final int position,int userId, int circleId, final ImageView praiseImageView, final TextView praiseTextView, final int likeCount){
        PraiseRequest request = new PraiseRequest(userId,circleId);
        RetrofitClient.getInstances().requestPraiseCancel(request).enqueue(new UICallBack<BaseBenTwo>() {
            @Override
            public void OnRequestFail(String msg) {
                checkNet();
            }

            @Override
            public void OnRequestSuccess(BaseBenTwo bean) {
                switch (bean.getReturnValue()){
                    case 1:
                        int myCount = likeCount-1;
                        mAdapter.getDataList().get(position).setIsLike(0);
                        mAdapter.getDataList().get(position).setLikeCount(myCount);
                        praiseImageView.setImageResource(R.mipmap.family_dz_icon);
                        praiseTextView.setText("("+myCount+")");
                        showToast("(｡•ˇ‸ˇ•｡)");
                        break;
                }
            }
        });
    }

    //点赞的点击事件
    @Override
    public void PraiseClickListener(int position, ImageView imageView, TextView textView,int like,int circleId,int likeCount) {
        if (like==0){  //是否已点赞 0未点赞  1已点赞
            //点赞
            requestPraise(position,mUserId,circleId,imageView,textView,likeCount);
        }else {
            //取消点赞
            requestPraiseCancel(position,mUserId,circleId,imageView,textView,likeCount);
        }
    }

    //举报的点击事件
    @Override
    public void ReportClickListener(int position, int circleId) {
        Intent intent = new Intent(getActivity(), FeedBackActivity.class);
        intent.putExtra("type","report");
        intent.putExtra("id",circleId);
        startActivity(intent);

    }


    //分享的点击事件
    @Override
    public void ShareClickListener(int position, List<CircleResponse.DataBean.ImagesBean> Images,String content) {
        shareContent = TransCodingUtils.Decode(content);
        if (mAdapter.getDataList().get(position).getImages()==null){ //如果没有图片
            showShareContext();  //分享纯文本
        }else {//否则 调自定义多图分享至微信
            for (int i=0;i<Images.size();i++){
                mImageList.add(Images.get(i).getUrl());
            }
            //此处获取BitMap对象必须在异步线程中否则会报错
            new Thread(() -> {
                for (String url : mImageList){
                    imageList.add(ActivityUtils.getBitMBitmap(url));
//                        LogUtils.d("bitmap=="+ActivityUtils.getBitMBitmap(url));
                }
            }).start();
            loadImage(bitmapList -> shareToTimeline(imageList));
        }

    }

    //自定义分享
    private void shareWex(){
        if (!ShareTools.isWeixinAvilible(mContext)){
            showToast(R.string.loading_wex);
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    files.clear();
                    try {
                        for (int i=0;i<imageList.size();i++){
                            //File file = ShareTools.saveImageToSdCard(mContext,imageList.get(i));
                            //files.add(file);
                        }
                        Intent intent = new Intent();
                        ComponentName comp;
                        comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                        intent.putExtra("Kdescription", shareContent);
                        intent.setComponent(comp);
                        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        intent.setType("image/*");
                        ArrayList<Uri> imageUris = new ArrayList<Uri>();
                        for (File f : files) {
                            imageUris.add(Uri.fromFile(f));
                        }
                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // 分享到朋友圈。
    private void shareToTimeline(List<Bitmap> bitmapList) {
        WXShareMultiImageHelper.shareToTimeline(getActivity(), bitmapList,"请输入你想要表达的内容...");
    }

    private void loadImage(final OnLoadImageEndCallback callback) {
        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("正在加载图片...");
        dialog.show();
        new Thread(() -> {
            List<Bitmap> bitmapList = new ArrayList<>();
            for (String imageResId : mImageList) {
                bitmapList.add(ActivityUtils.getBitMBitmap(imageResId));
//                    LogUtils.d("bitmap=="+ActivityUtils.getBitMBitmap(imageResId));
            }
            getActivity().runOnUiThread(() -> dialog.cancel());
            callback.onEnd(bitmapList);
        }).start();
    }

    private interface OnLoadImageEndCallback {
        void onEnd(List<Bitmap> bitmapList);
    }


    //分享
    private void showShareContext() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent);

        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equals(WechatMoments.NAME)){
                    paramsToShare.setShareType(Platform.SHARE_TEXT);
                }
            }
        });

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
        oks.show(getActivity());

    }

}
