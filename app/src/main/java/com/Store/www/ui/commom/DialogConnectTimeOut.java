package com.Store.www.ui.commom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Store.www.R;


/**
 * @author: haifeng
 * @description: 连接超时排队弹窗
 */
public class DialogConnectTimeOut {

    public final static int COUNT = 1;  //handler 消息值

    public static AlertDialog HintDialog;  //提示弹窗

    public static Dialog ShowTimeOutDialog(Context context,TimeOutDialogButtonClickListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_queuing_time,null);
        TextView mTvCountDownHint = view.findViewById(R.id.tv_count_down_hint);  //倒计时提示
        TextView mTvCancel = view.findViewById(R.id.tv_cancel);  //取消按钮
        TextView mTvAgainConnect = view.findViewById(R.id.tv_again_connect);  //再次请求按钮
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create();
        dialog.show();
        HintDialog = dialog;
        Handler handler = new Handler(){
            int CountDownTime = 30;
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case COUNT:
                        //mTvCountDownHint.setText("当前访问人数过多,请"+CountDownTime+"秒后再次访问");
                        CountDownTime --;
                        if (CountDownTime==0){
                            CountDownTime = 0;
                            mTvCountDownHint.setVisibility(View.GONE);
                            mTvCancel.setVisibility(View.VISIBLE);
                            mTvAgainConnect.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        handler.post(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(COUNT);
                handler.postDelayed(this,1000);
            }
        });
        mTvCancel.setOnClickListener(v -> {  //取消点击事件
            listener.OnCancelClickListener(dialog);
        });
        mTvAgainConnect.setOnClickListener(v -> {  //再次请求点击事件
            listener.OnDismissClickListener(dialog);
        });
        return dialog;
    }

    public interface TimeOutDialogButtonClickListener{
        void OnCancelClickListener(AlertDialog dialog);
        void OnDismissClickListener(AlertDialog dialog);
    }

}
