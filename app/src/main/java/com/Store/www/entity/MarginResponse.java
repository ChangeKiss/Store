package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/12/6.
 * 保证金响应体
 */

public class MarginResponse {

    /**
     * returnValue : 1
     * data : {"isPayBound":0,"levelName":"您没有等级","boundInfo":[{"boundMoney":500000,"name":"合伙人"},{"boundMoney":200000,"name":"钻石"},{"boundMoney":100000,"name":"一级"},{"boundMoney":50000,"name":"二级"}],"currentPayedBound":0,"needPayTotal":0}
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
         * isPayBound : 0
         * levelName : 您没有等级
         * boundInfo : [{"boundMoney":500000,"name":"合伙人"},{"boundMoney":200000,"name":"钻石"},{"boundMoney":100000,"name":"一级"},{"boundMoney":50000,"name":"二级"}]
         * currentPayedBound : 0
         * needPayTotal : 0
         */

        private int isPayBound;
        private String levelName;
        private int currentPayedBound;
        private int needPayTotal;
        private List<BoundInfoBean> boundInfo;

        public int getIsPayBound() {
            return isPayBound;
        }

        public void setIsPayBound(int isPayBound) {
            this.isPayBound = isPayBound;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getCurrentPayedBound() {
            return currentPayedBound;
        }

        public void setCurrentPayedBound(int currentPayedBound) {
            this.currentPayedBound = currentPayedBound;
        }

        public int getNeedPayTotal() {
            return needPayTotal;
        }

        public void setNeedPayTotal(int needPayTotal) {
            this.needPayTotal = needPayTotal;
        }

        public List<BoundInfoBean> getBoundInfo() {
            return boundInfo;
        }

        public void setBoundInfo(List<BoundInfoBean> boundInfo) {
            this.boundInfo = boundInfo;
        }

        public static class BoundInfoBean {
            /**
             * boundMoney : 500000
             * name : 合伙人
             */

            private int boundMoney;
            private String name;

            public int getBoundMoney() {
                return boundMoney;
            }

            public void setBoundMoney(int boundMoney) {
                this.boundMoney = boundMoney;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
