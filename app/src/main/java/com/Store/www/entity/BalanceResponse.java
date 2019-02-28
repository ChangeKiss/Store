package com.Store.www.entity;

/**
 * 余额响应体
 */

public class BalanceResponse {

    /**
     * returnValue : 1
     * data : {"currentBalance":5270000,"withdrawBalance":5270000,"freezeBalance":0,"disableBalance":0,"usableBalance":5270000}
     */

    private int returnValue;
    private DataBean data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * currentBalance : 5270000
         * withdrawBalance : 5270000
         * freezeBalance : 0
         * disableBalance : 0
         * usableBalance : 5270000
         */

        private int currentBalance;
        private int withdrawBalance;
        private int freezeBalance;
        private int disableBalance;
        private int usableBalance;

        public int getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(int currentBalance) {
            this.currentBalance = currentBalance;
        }

        public int getWithdrawBalance() {
            return withdrawBalance;
        }

        public void setWithdrawBalance(int withdrawBalance) {
            this.withdrawBalance = withdrawBalance;
        }

        public int getFreezeBalance() {
            return freezeBalance;
        }

        public void setFreezeBalance(int freezeBalance) {
            this.freezeBalance = freezeBalance;
        }

        public int getDisableBalance() {
            return disableBalance;
        }

        public void setDisableBalance(int disableBalance) {
            this.disableBalance = disableBalance;
        }

        public int getUsableBalance() {
            return usableBalance;
        }

        public void setUsableBalance(int usableBalance) {
            this.usableBalance = usableBalance;
        }
    }
}
