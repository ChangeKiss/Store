package com.Store.www.entity;

import java.util.List;

/**
 * 获取银行卡列表的响应体
 */

public class BankCardListResponse {


    /**
     * returnValue : 1
     * data : [{"openBank":"农业银行·金穗通宝卡(银联卡)","cardId":54,"cardType":0,"cardNumber":"4376"},{"openBank":"浦东发展银行·VISA白金信用卡","cardId":55,"cardType":1,"cardNumber":"1304"},{"openBank":"农业银行·金穗通宝卡(银联卡)","cardId":56,"cardType":0,"cardNumber":"4376"}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openBank : 农业银行·金穗通宝卡(银联卡)
         * cardId : 54
         * cardType : 0
         * cardNumber : 4376
         * isCoupletNumber :0   是否有联行号 0没有 1有
         */

        private String openBank;
        private int cardId;
        private int cardType;
        private String cardNumber;
        private int isCoupletNumber;
        private boolean isSelect;

        public String getOpenBank() {
            return openBank;
        }

        public void setOpenBank(String openBank) {
            this.openBank = openBank;
        }

        public int getCardId() {
            return cardId;
        }

        public void setCardId(int cardId) {
            this.cardId = cardId;
        }

        public int getCardType() {
            return cardType;
        }

        public void setCardType(int cardType) {
            this.cardType = cardType;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public int getIsCoupletNumber() {
            return isCoupletNumber;
        }

        public void setIsCoupletNumber(int isCoupletNumber) {
            this.isCoupletNumber = isCoupletNumber;
        }


        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
