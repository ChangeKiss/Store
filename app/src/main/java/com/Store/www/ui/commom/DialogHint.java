package com.Store.www.ui.commom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.Store.www.R;


/**
 * 一个和两个按钮的弹窗
 */

public class DialogHint {
    private Activity mActivity;
    private Fragment mFragment;

    public DialogHint(Activity activity) {
        mActivity = activity;
    }

    public DialogHint(Fragment fragment) {
        mFragment = fragment;
    }

    private
    @Nullable
    Activity getActivity() {
        if (mActivity != null) return mActivity;
        if (mFragment != null) {
            return mFragment == null ? null : mFragment.getActivity();
        }
        return null;
    }

    public static Dialog showDialogFromBottom(Context context, View contentView) {
        Dialog dialog = new Dialog(context, R.style.mydialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);
        //设置宽度占满屏幕，并置底
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
        return dialog;
    }

    public static void showDialogWithTwoButton(Context context, @StringRes final int titleId, final OnDialogTwoButtonClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_two_button, null);
        Button btnCancel = (Button) view.findViewById(R.id.btn_dialog_cancel);
        Button btnOk = (Button) view.findViewById(R.id.btn_dialog_ok);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(titleId);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create();
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDialogCancelButtonClickListener(dialog);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDialogOkButtonClickListener(dialog, titleId);
            }
        });
    }


    public static void showDialogWithOneButton(Context context, @StringRes final int titleId, final OnDialogOneButtonClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_dialog_ok);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(titleId);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDialogOkButtonClickListener(dialog, titleId);
            }
        });
    }

    public static void showDialogWithOneButton(Context context, final String titleId, final OnDialogOneButtonClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_dialog_ok);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(titleId);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnDialogOkButtonClickListener(dialog, titleId);
            }
        });
    }

    public static void showDialogWithOneButton(Context context, @StringRes final int titleId) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null);
        Button btnOk = (Button) view.findViewById(R.id.btn_dialog_ok);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(titleId);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }



    public interface OnDialogTwoButtonClickListener {
        void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId);

        void setOnDialogCancelButtonClickListener(AlertDialog dialog);
    }

    public interface OnDialogOneButtonClickListener {
        void setOnDialogOkButtonClickListener(AlertDialog dialog, int titleId);
        void setOnDialogOkButtonClickListener(AlertDialog dialog, String titleId);
    }
}

