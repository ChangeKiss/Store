package com.Store.www.net;

/**
 * Created by www on 2017/12/14.
 */


import com.Store.www.ui.commom.DialogLoading;
import com.Store.www.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 为了统一处理retrofit 的CallBack
 */
public abstract class UICallBack<T> implements Callback<T>{

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        DialogLoading.dismiss();
        // 处理回复
        if (response != null && response.isSuccessful()) {
            T bean = response.body();
            OnRequestSuccess(bean);
        } else {
            // 联网过程中的异常
            LogUtils.d("UICallback+onResponse=" + response.code());
            OnRequestFail(response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        DialogLoading.dismiss();
        // 联网过程中的异常
        LogUtils.d(t.toString());
        OnRequestFail("UICallback+onFailure=" + t.toString());
    }



    /**
     * 错误处理
     *
     * @param msg
     */
    public abstract void OnRequestFail(String msg);



    /**
     * 解析服务器回复数据
     *
     * @param bean
     */
    public abstract void OnRequestSuccess(T bean);
}
