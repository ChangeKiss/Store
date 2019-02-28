package com.Store.www.base;

import android.content.Context;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;

import java.io.File;

/**
 * Created by www on 2018/6/28.
 */

public class RecognizeService {

    public interface ServiceListener {
        public void onResult(String result);
    }
    public static void recBankCard(Context ctx, String filePath, final ServiceListener listener) {
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance(ctx).recognizeBankCard(param, new OnResultListener<BankCardResult>() {
            @Override
            public void onResult(BankCardResult result) {
                String res = result.getBankCardNumber();
                listener.onResult(res);
            }

            @Override
            public void onError(OCRError error) {
                listener.onResult(error.getMessage());
            }
        });
    }
}
