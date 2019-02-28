package com.Store.www.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Store.www.R;
import com.Store.www.base.BaseRecyclerViewAdapter;
import com.Store.www.entity.AddShoppingrequest;
import com.Store.www.entity.IntroduceResponse;
import com.Store.www.utils.LogUtils;
import com.Store.www.utils.UserPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品下单的适配器
 */

@TargetApi(Build.VERSION_CODES.M)
public class OrdersAdapter extends BaseRecyclerViewAdapter<IntroduceResponse.DataBean.SkuDataBean,OrdersAdapter.ViewHolder>{

    private int[] MordersNumber;
    private int ordersNumber,sum,mCount;
    private OnOrderItemClickListener mListener;
    private int countValue;
    LinearLayout.LayoutParams params;  //设置宽度
    LinearLayout.LayoutParams paramsTwo;  //设置竖线的位置
    LinearLayout.LayoutParams paramsThree;  //设置颜色的位置



    List<AddShoppingrequest.SKUdataBean> been = new ArrayList<>();
    AddShoppingrequest.SKUdataBean mBean;

    public List<AddShoppingrequest.SKUdataBean> getBeen() {
        return been;
    }

    public void setBeen(List<AddShoppingrequest.SKUdataBean> been) {
        this.been = been;
    }

    public OrdersAdapter(Context context, OnOrderItemClickListener listener) {
        super(context);
        mListener = listener;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int[] getMordersNumber() {
        return MordersNumber;
    }

    public void setMordersNumber(int[] mordersNumber) {
        MordersNumber = mordersNumber;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_orders,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final IntroduceResponse.DataBean.SkuDataBean dataBean =mDataList.get(position);
        params = (LinearLayout.LayoutParams) holder.mLayoutNameSize.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mTvCommodityNumber.getLayoutParams();
        params = (LinearLayout.LayoutParams) holder.mLayoutNumberLocation.getLayoutParams();
        params.width= UserPrefs.getInstance().getWidth()/3;
        holder.mLayoutNameSize.setLayoutParams(params);
        holder.mTvCommodityNumber.setLayoutParams(params);
        holder.mLayoutNumberLocation.setLayoutParams(params);

        paramsTwo = (LinearLayout.LayoutParams) holder.mTvLine.getLayoutParams();
        paramsTwo.leftMargin= UserPrefs.getInstance().getWidth()/3/5/4;
        holder.mTvLine.setLayoutParams(paramsTwo);
        paramsThree = (LinearLayout.LayoutParams) holder.mTvOrderSize.getLayoutParams();
        paramsThree.width = UserPrefs.getInstance().getWidth()/3/2;
        holder.mTvOrderSize.setLayoutParams(paramsThree);
        holder.mTvOrdersName.setLayoutParams(paramsThree);


        final String colorName = dataBean.getColorName();
        String sizeName = dataBean.getSizeName();
        LogUtils.d("颜色==="+dataBean.getColorName());
        LogUtils.d("大小==="+dataBean.getSizeName());

        //如果输入文本框重绘时，将文本框的监听事件移出 ，否则数据会丢失
        if (holder.mOrdersNumber.getTag() instanceof TextWatcher){
            holder.mOrdersNumber.removeTextChangedListener((TextWatcher) holder.mOrdersNumber.getTag());
        }
        //holder.mOrdersNumber.setText(MordersNumber[position]+"");
        holder.mTvOrdersName.setText(colorName);
        holder.mTvLine.setText("|");

        holder.mTvOrderSize.setText(sizeName);
        holder.mTvCommodityNumber.setText(dataBean.getStock()+"");
        holder.mOrdersNumber.setText(MordersNumber[position]+""); //设置初始值
        holder.mOrdersNumber.setTag(position);
        mBean = new AddShoppingrequest.SKUdataBean();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)){
                    MordersNumber[position] = 0;
                }else {
                    MordersNumber[position] = Integer.parseInt(s.toString());
                    int numberThree = 0;
                    for (int i = 0; i < MordersNumber.length; i++) {
                        numberThree += MordersNumber[i];
                        LogUtils.d("数量==" + MordersNumber[i]);
                    }
                    LogUtils.d("总数=03=" + numberThree);
                    setMordersNumber(MordersNumber);
                    setSum( numberThree);
                    mListener.setOrdersNumber(position,getSum());
                }
            }
        };

        holder.mOrdersNumber.addTextChangedListener(textWatcher);
        holder.mOrdersNumber.setTag(textWatcher);

        holder.mIvMinus.setOnClickListener(new View.OnClickListener() { //减号的点击事件
            @Override
            public void onClick(View v) {
                int number = 0;
                ordersNumber = MordersNumber[position];
                if (ordersNumber > 0) {
                    ordersNumber--;
                    MordersNumber[position] = ordersNumber;
                    LogUtils.d("MordersNumber[i]" + MordersNumber[position]);
                    for (int i = 0; i < MordersNumber.length; i++) {
                        number += MordersNumber[i];
                    }
                    LogUtils.d("总数==" + number);
                    setSum(number);
                } else {
                    ordersNumber = 0;
                }
                LogUtils.d("当前数量==="+MordersNumber[position]);
                setMordersNumber(MordersNumber);
                holder.mOrdersNumber.setText("" + ordersNumber);
                LogUtils.d("商品总数=01="+getSum());
                mListener.setMinusClickListener(position,getSum());

            }
        });

        holder.mIvPlus.setOnClickListener(new View.OnClickListener() { //加号的点击事件
            @Override
            public void onClick(View v) {
                int numberTwo = 0;
                ordersNumber = MordersNumber[position];
                if (ordersNumber < dataBean.getStock()) {
                    ordersNumber++;

                    MordersNumber[position] = ordersNumber;
                    LogUtils.d("当前数量==" + MordersNumber[position]);
                    for (int i = 0; i < MordersNumber.length; i++) {
                        numberTwo += MordersNumber[i];
                        LogUtils.d("数量==" + MordersNumber[i]);
                    }
                    LogUtils.d("总数==" + numberTwo);
                    setSum( numberTwo);
                } else {
                    ordersNumber = dataBean.getStock();
                }
                LogUtils.d("当前数量==="+MordersNumber[position]);
                setMordersNumber(MordersNumber);
                holder.mOrdersNumber.setText("" + ordersNumber);
                LogUtils.d("商品总数=02="+getSum());
                mListener.setPlusClickListener(position,getSum());
            }
        });


    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_orders_name)
        TextView mTvOrdersName;  //商品名称
        @BindView(R.id.iv_minus)
        ImageView mIvMinus;
        @BindView(R.id.orders_number)
        EditText mOrdersNumber;
        @BindView(R.id.iv_plus)
        ImageView mIvPlus;
        @BindView(R.id.tv_commodity_number)
        TextView mTvCommodityNumber;
        @BindView(R.id.layout_number_location)
        LinearLayout mLayoutNumberLocation; //右边整个选择数量的布局
        @BindView(R.id.tv_line)
        TextView mTvLine;  //中间竖线
        @BindView(R.id.tv_order_size)
        TextView mTvOrderSize;  //尺码
        @BindView(R.id.layout_name_size)
        LinearLayout mLayoutNameSize;  //名称和尺码的布局



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnOrderItemClickListener {
        void setMinusClickListener(int position,int sum);
        void setPlusClickListener(int position,int sum);
        void setOrdersNumber(int position,int sum);
    }

}
