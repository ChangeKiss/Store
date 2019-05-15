package com.Store.www.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Store.www.MyApplication;
import com.Store.www.R;
import com.Store.www.base.BaseToolbarActivity;
import com.Store.www.entity.PhotoAlbumPictureResponse;
import com.Store.www.net.RetrofitClient;
import com.Store.www.net.UICallBack;
import com.Store.www.ui.adapter.PhotoAlbumPictureAdapter;
import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.ActivityCollector;
import com.Store.www.utils.ActivityUtils;
import com.Store.www.utils.LogUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.liji.imagezoom.util.ImageZoom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 相册照片界面
 */
public class PhotoAlbumPictureActivity extends BaseToolbarActivity implements OnRefreshListener, OnLoadMoreListener ,
                PhotoAlbumPictureAdapter.onClickListener,BaseToolbarActivity.OnToolBarRightClickListener{
    TextView mTvToolBarRight;
    LRecyclerView mLRv;
    RelativeLayout mNoData;

    private List<String> imageUrls = new ArrayList<>();
    PhotoAlbumPictureAdapter mAdapter;
    LRecyclerViewAdapter viewAdapter;
    private String mTitle;
    private int albumId; //相册ID
    private List<Bitmap> saveAlbum = new ArrayList<>();
    /**
     * 记录选中的图片
     */
    private List<String> checkList;
    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_photo_album_picture;
    }

    @Override
    public void initView() {
        ActivityCollector.addActivity(this);
        mTitle = getIntent().getStringExtra("title");
        albumId = getIntent().getIntExtra("id",0);
        mTvToolBarRight = findViewById(R.id.iv_toolbar_right);
        mLRv = findViewById(R.id.lv_picture);
        mNoData = findViewById(R.id.nodata);
        initToolbar(this,true,mTitle,this);
        checkList = new ArrayList<>();
        initAdapter();
        mTvToolBarRight.setVisibility(View.VISIBLE);
    }

    //初始化适配器
    private void initAdapter(){
        mAdapter = new PhotoAlbumPictureAdapter(mContext,this);
        viewAdapter = new LRecyclerViewAdapter(mAdapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLRv.setLayoutManager(manager);
        mLRv.setOnRefreshListener(this);
        mLRv.setOnLoadMoreListener(this);
        mLRv.setAdapter(viewAdapter);
        getPicture(albumId,mPageIndex,mCountPerPage,false);  //第一次加载数据
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

    /**
     * 获取相册照片
     * @param albumId
     * @param pageIndex
     * @param countPerPage
     * @param lodMore
     */
    private void getPicture(int albumId, int pageIndex, int countPerPage,boolean lodMore){
        if (pageIndex ==1){
            imageUrls.clear();
            DialogLoading.shows(mContext,"加载中...");
            mAdapter.getDataList().clear();
            mAdapter.notifyDataSetChanged();
        }
        RetrofitClient.getInstances().getPhotoAlbumPicture(albumId,pageIndex,countPerPage).enqueue(new UICallBack<PhotoAlbumPictureResponse>() {
            @Override
            public void OnRequestFail(String msg) {
                if (isTop)checkNet();
            }

            @Override
            public void OnRequestSuccess(PhotoAlbumPictureResponse bean) {
                if (isTop){
                    switch (bean.getReturnValue()){
                        case 1:
                            for (int i=0;i<bean.getData().size();i++){
                                imageUrls.add(bean.getData().get(i).getPhoto());
                            }
                            mAdapter.setImageUrls(imageUrls);
                            if (lodMore){  //上拉加载时
                                if (bean.getData().size()<mCountPerPage){  //如果上拉加载的数据小于10条
                                    mAdapter.addAll(bean.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mLRv.setNoMore(true);
                                    mLRv.refreshComplete(mCountPerPage);
                                }else {
                                    mAdapter.addAll(bean.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mLRv.refreshComplete(mCountPerPage);
                                }
                            }else {  //非上拉加载 或者第一次加载时
                                if (bean.getData().size()>0){
                                    mNoData.setVisibility(View.GONE);
                                    mAdapter.addAll(bean.getData());
                                    mAdapter.notifyDataSetChanged();
                                    mLRv.refreshComplete(mCountPerPage);
                                }else {
                                    mNoData.setVisibility(View.VISIBLE);
                                }
                            }
                            break;
                        default:

                            break;
                    }
                }
            }
        });
    }



    //上拉加载
    @Override
    public void onLoadMore() {
        mPageIndex++;
        getPicture(albumId,mPageIndex,mCountPerPage, true);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        mPageIndex = 1;
        getPicture(albumId,mPageIndex,mCountPerPage, false);
    }

    //点击事件
    @Override
    public void onItemClickListener(int position, String url, List<String> imageUrls) {
        if (isShowCheck){  //如果复选框是显示的
            LogUtils.d("url.name=="+imageUrls.get(position));
            if (checkList.contains(imageUrls.get(position))) {
                checkList.remove(imageUrls.get(position));
                new Thread(()->{
                    saveAlbum.remove(ActivityUtils.getBitMBitmap(imageUrls.get(position)));
                }).start();
            } else {
                checkList.add(imageUrls.get(position));
                new Thread(() -> {
                    saveAlbum.add(ActivityUtils.getBitMBitmap(imageUrls.get(position)));
                }).start();
            }
            LogUtils.d("长度=="+checkList.size());
            if (checkList.size()>=1){
                mTvToolBarRight.setText("保存");
            }else if (checkList.size() <=0){
                mTvToolBarRight.setText("取消");
            }
        }else {
            ImageZoom.show(mContext,position,imageUrls);
        }
    }

    //长按事件
    @Override
    public void onLongClickListener(int position, String url) {
        if (isShowCheck) {
            mAdapter.setShowCheckBox(false);
            mAdapter.notifyDataSetChanged();
            checkList.clear();
            mTvToolBarRight.setVisibility(View.GONE);
        } else {
            mAdapter.setShowCheckBox(true);
            mAdapter.notifyDataSetChanged();
            mTvToolBarRight.setVisibility(View.VISIBLE);
            mTvToolBarRight.setText("取消");
        }
        isShowCheck = !isShowCheck;
    }

    //标题栏右侧的点击事件
    @Override
    public void setOnToolBarRightClickListener() {
        if (mTvToolBarRight.getText().equals("取消")){
            mAdapter.setShowCheckBox(false);
            mAdapter.notifyDataSetChanged();
            checkList.clear();
            mTvToolBarRight.setVisibility(View.GONE);
            isShowCheck = false;
        }else if (mTvToolBarRight.getText().equals("保存")){
            saveImageToGallery(mContext,saveAlbum);
        }
    }

    private void saveImageToGallery(Context context, List<Bitmap> bmp) {

        // 首先保存图片

        File appDir = new File(Environment.getExternalStorageDirectory(), "/Fuatee");

        if (!appDir.exists()) {

            appDir.mkdir();

        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {

            FileOutputStream fos = new FileOutputStream(file);
            for (int i=0;i<bmp.size();i++){
                bmp.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            showToast("保存成功");
            mTvToolBarRight.setVisibility(View.GONE);
            mAdapter.setShowCheckBox(false);
            mAdapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToast("保存失败");
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse(MyApplication.getmDirPath())));

    }
}
