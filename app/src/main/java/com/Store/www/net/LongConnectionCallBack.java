package com.Store.www.net;

import com.Store.www.ui.commom.DialogLoading;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author: haifeng
 * @description:  retrofit 的第二个CallBack封装 主要用来判断连接超时 全部改的话太麻烦了 报表模块专用
 */
public abstract class LongConnectionCallBack<T> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        DialogLoading.dismiss();
        // 处理回复
        if (response != null && response.isSuccessful()) {
            T bean = response.body();
            OnRequestSuccess(bean);
        } else {
            OnRequestFail(response.code());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        DialogLoading.dismiss();
        OnError(ExceptionHandle.handleException(t));
        call.cancel();
    }



    /**
     * 错误处理
     *
     * @param code
     */
    public abstract void OnRequestFail(int code);

    /**
     * 错误码处理
     * @param throwable
     */
    public abstract void OnError(ExceptionHandle.ResponeThrowable throwable);

    /**
     * 解析服务器回复数据
     *
     * @param bean
     */
    public abstract void OnRequestSuccess(T bean);


}
