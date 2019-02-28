package com.Store.www.entity;

import java.util.List;

/**
 * 我的仓库响应体
 */

public class MyWarehouseResponse extends BaseBean{

    /**
     * data : [{"address":"北京市","name":"胡巍-测试用的云","count":109,"type":1,"repId":451}]
     * errMsg : 成功
     */

    private String errMsg;
    private List<DataBean> data;

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
         * address : 北京市
         * name : 胡巍-测试用的云
         * count : 109
         * type : 1
         * repId : 451
         */

        private String address;
        private String name;
        private int count;
        private int type;
        private int repId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getRepId() {
            return repId;
        }

        public void setRepId(int repId) {
            this.repId = repId;
        }
    }
}
