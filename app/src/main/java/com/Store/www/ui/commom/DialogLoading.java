package com.Store.www.ui.commom;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.TextView;

import com.Store.www.R;

/**
 * 加载数据的弹窗
 */

public class DialogLoading {

    public static Dialog progressDialog=null;
    private static Dialog getInstance(Context context, int resId){
        if(progressDialog==null){
            progressDialog = new Dialog(context,resId);
        }
        return progressDialog;
    }

    public static void shows(Context context, String text) {
        getInstance(context, R.style.progress_dialog_style);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(text);
        progressDialog.show();
    }

    public static void shows(Context context, @StringRes int text) {
        getInstance(context, R.style.progress_dialog_style);
        progressDialog.setContentView(R.layout.dialog_loading);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(text);
        progressDialog.show();
    }

    public static void dismiss() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }

    public static boolean isDismiss() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                return false;
            }
        }
        return true;
    }
}
