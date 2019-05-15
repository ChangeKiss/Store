package com.Store.www.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.PhotoAlbumPictureResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: haifeng
 * @description: 相册照片适配器
 */
public class PhotoAlbumPictureAdapter extends BaseRecyclerViewAdapter<PhotoAlbumPictureResponse.DataBean,PhotoAlbumPictureAdapter.ViewHolder> {

    onClickListener mListener;
    RelativeLayout.LayoutParams params;
    private SparseArray<Integer> heightArray;
    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;
    /**
     * 图片浏览需要的图片list
     */
    private List<String> imageUrls = new ArrayList<>();
    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public PhotoAlbumPictureAdapter(Context context, onClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        heightArray = new SparseArray<Integer>();
        View view = inflater.inflate(R.layout.item_image_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoAlbumPictureResponse.DataBean bean = mDataList.get(position);
        params = (RelativeLayout.LayoutParams) holder.mIvImage.getLayoutParams();
        params.height = UserPrefs.getInstance().getWidth()/bean.getWidth()*bean.getHeight();
        params.width = UserPrefs.getInstance().getWidth()/2-10;
        holder.mIvImage.setLayoutParams(params);

        if (showCheckBox){
            holder.mCbSave.setVisibility(View.VISIBLE);
            holder.mCbSave.setChecked(mCheckStates.get(position,false));  //防止显示错乱
        }else {
            holder.mCbSave.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            holder.mCbSave.setChecked(false);
            mCheckStates.clear();
        }
        //对checkbox的监听 保存选择状态 防止checkbox显示错乱
        holder.mCbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mCheckStates.put(position, true);
                } else {
                    mCheckStates.delete(position);
                }

            }
        });
        //item的点击事件
        holder.itemView.setOnClickListener(v -> {
            if (showCheckBox) {
                holder.mCbSave.setChecked(!holder.mCbSave.isChecked());
            }
            mListener.onItemClickListener(position,bean.getPhoto(),getImageUrls());
            //mListener.onItemClickListener(position,bean.getPhoto(),getImageUrls());
        });
        //item的长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onLongClickListener(position,bean.getPhoto());
                return false;
            }
        });
        Glide.with(mContext)
                .load(bean.getPhoto())
                .error(R.mipmap.jzz_img)
                .into(holder.mIvImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image_view)
        ImageView mIvImage;
        @BindView(R.id.cb_save)
        CheckBox mCbSave;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onClickListener{
        void onItemClickListener(int position, String url, List<String> imageUrls);
        void onLongClickListener(int position,String url);
    }
}
