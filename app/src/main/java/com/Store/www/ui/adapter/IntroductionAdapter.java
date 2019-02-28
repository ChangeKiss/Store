package com.Store.www.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Store.www.R;

import java.util.List;

/**
 * 引导页的适配器
 */

public class IntroductionAdapter extends PagerAdapter{
    List<Integer> ImageList;
    LayoutInflater inflater;
    OnClickListener mListener;
    public IntroductionAdapter(Context context,List<Integer> imageList,OnClickListener listener){
        this.ImageList=imageList;
        inflater = LayoutInflater.from(context);
        mListener =listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_introduction,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_page_splash);
        imageView.setImageResource(ImageList.get(position));
        if (ImageList.size()-1 ==position){  //如果是最后一张图片的时候
            imageView.setOnClickListener(new View.OnClickListener() {  //给图片添加点击事件
                @Override
                public void onClick(View v) {
                    mListener.OnItemClickListener(position);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public interface OnClickListener{
        void OnItemClickListener(int position);
    }


}
