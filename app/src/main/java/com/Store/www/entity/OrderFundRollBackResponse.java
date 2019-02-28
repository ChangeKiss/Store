package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 订单资金回退响应体
 */
public class OrderFundRollBackResponse {

    /**
     * returnValue : 1
     * data : {"list":[{"orderNo":"DD20181203247230","orderMoney":1000,"surplusMoney":1000,"paymentMethod":"其他","id":247230},{"orderNo":"DD20181203247231","orderMoney":1000,"surplusMoney":1000,"paymentMethod":"其他","id":247231},{"orderNo":"DD20181203247235","orderMoney":1000,"surplusMoney":1000,"paymentMethod":"其他","id":247235},{"orderNo":"DD20181204247255","orderMoney":49800000,"surplusMoney":49800000,"paymentMethod":"余额","id":247255},{"orderNo":"DD20181205247258","orderMoney":498000,"surplusMoney":498000,"paymentMethod":"其他","id":247258},{"orderNo":"DD20181205247265","orderMoney":10000,"surplusMoney":10000,"paymentMethod":"其他","id":247265},{"orderNo":"DD20181210248437","orderMoney":50000,"surplusMoney":50000,"paymentMethod":"余额","id":248437},{"orderNo":"DD20181210248438","orderMoney":50000,"surplusMoney":50000,"paymentMethod":"余额","id":248438},{"orderNo":"DD20181210248442","orderMoney":50000,"surplusMoney":50000,"paymentMethod":"余额","id":248442},{"orderNo":"DD20181214248756","orderMoney":1,"surplusMoney":1,"paymentMethod":"其他","id":248756}]}
     * errMsg : null
     */

    private int returnValue;
    private DataBean data;
    private String errMsg;

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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * orderNo : DD20181203247230
             * orderMoney : 1000
             * surplusMoney : 1000
             * paymentMethod : 其他
             * id : 247230
             */

            private String orderNo;
            private int orderMoney;
            private int surplusMoney;
            private String paymentMethod;
            private int id;
            private boolean isCheck;

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public int getOrderMoney() {
                return orderMoney;
            }

            public void setOrderMoney(int orderMoney) {
                this.orderMoney = orderMoney;
            }

            public int getSurplusMoney() {
                return surplusMoney;
            }

            public void setSurplusMoney(int surplusMoney) {
                this.surplusMoney = surplusMoney;
            }

            public String getPaymentMethod() {
                return paymentMethod;
            }

            public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }
        }
    }
}
