package com.Store.www.ui.costomPassword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import com.Store.www.R;

/**
 * Created by www on 2018/4/25.
 */

public class PopEnterPassword extends PopupWindow{


    private PasswordView pwdView;
    private View mMenuView;
    private Activity mContext;

    public PopEnterPassword(final Activity context) {

        super(context);

        this.mContext = context;



        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);



        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);



        //添加密码输入完成的响应

        pwdView.setOnFinishInput(new OnPasswordInputFinish() {

            @Override

            public void inputFinish(final String password) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        /*// 模拟耗时的操作。
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/

                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                                //Toast.makeText(mContext, "支付成功，密码为：" + password, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("password",password);
                                intent.setAction("withdraw");
                                context.sendBroadcast(intent);
                            }

                        });
                    }
                }).start();

            }

        });



        // 监听X关闭按钮

        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent();  //发送广播
                intent.setAction("close");  //点击了关闭弹窗
                context.sendBroadcast(intent);
            }

        });

        //监听 找回密码按钮
        pwdView.getTvFindPassword().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理找回密码的逻辑
                dismiss();
                //Toast.makeText(mContext,"点击了找回密码",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();  //发送广播
                intent.putExtra("password","alter");
                intent.setAction("alterPassword");  //修改密码  提现时用
                context.sendBroadcast(intent);
            }
        });



        // 监听键盘上方的返回

        pwdView.getVirtualKeyboardView().getLayoutBack().setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent();  //发送广播
                intent.setAction("close");  //点击了关闭弹窗
                context.sendBroadcast(intent);
            }

        });





        // 设置SelectPicPopupWindow的View

        this.setContentView(mMenuView);

        // 设置SelectPicPopupWindow弹出窗体的宽

        this.setWidth(LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体的高

        this.setHeight(LayoutParams.WRAP_CONTENT);

        // 设置SelectPicPopupWindow弹出窗体可点击

        this.setFocusable(true);

        // 设置SelectPicPopupWindow弹出窗体动画效果

        this.setAnimationStyle(R.style.pop_add_ainm);

        // 实例化一个ColorDrawable颜色为半透明

        ColorDrawable dw = new ColorDrawable(0x66000000);

        // 设置SelectPicPopupWindow弹出窗体的背景

        this.setBackgroundDrawable(dw);
    }


}
