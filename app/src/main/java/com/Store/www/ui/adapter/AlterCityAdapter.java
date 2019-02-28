package com.Store.www.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseListViewAdapter;
import com.Store.www.entity.AlterAddressResponse;

/**
 * 修改城市适配器
 */

public class AlterCityAdapter extends BaseListViewAdapter<AlterAddressResponse.DataBean.CityListBean> {

    private int flag;

    public AlterCityAdapter(Context context) {
        super(context);
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view ==null){
            view = layoutInflater.inflate(R.layout.item_alter_city,null);
            viewHolder = new ViewHolder();
            viewHolder.mCityName = (TextView) view.findViewById(R.id.tv_alter_city);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mCityName.setText(getItem(i).getName());
        return view;
    }

    class ViewHolder{
        public TextView mCityName;
    }
}
