package com.Store.www.entity;

import java.util.List;

/**
 * 地址管理响应体
 */

public class LocationResponse extends BaseBean{

    /**
     * data : [{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":570937,"agentId":252699,"isDefault":null,"street":"江东区","city":"宁波市","province":"浙江省","country":"中国","address":"浙江-温州-鹿城区-qqq","isUsed":0,"receiveName":"测试用","phone":"18857738393"},{"createTime":null,"updateTime":null,"creater":"胡巍-测试用","updater":null,"isDelete":null,"id":574476,"agentId":252699,"isDefault":null,"street":"西城区","city":"北京市市辖区","province":"北京市","country":"中国","address":"天安门广场","isUsed":1,"receiveName":"胡2","phone":"18857738393"},{"createTime":null,"updateTime":null,"creater":"胡巍-测试用","updater":null,"isDelete":null,"id":574480,"agentId":252699,"isDefault":null,"street":"东城区","city":"北京市市辖区","province":"北京市","country":"中国","address":"外地号急急急嗯ill","isUsed":0,"receiveName":"胡二","phone":"18857722222"}]
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
         * createTime : null
         * updateTime : null
         * creater : null
         * updater : null
         * isDelete : null
         * id : 570937
         * agentId : 252699
         * isDefault : null
         * street : 江东区
         * city : 宁波市
         * province : 浙江省
         * country : 中国
         * address : 浙江-温州-鹿城区-qqq
         * isUsed : 0
         * receiveName : 测试用
         * phone : 18857738393
         */

        private Object createTime;
        private Object updateTime;
        private Object creater;
        private Object updater;
        private Object isDelete;
        private int id;
        private int agentId;
        private Object isDefault;
        private String street;
        private String city;
        private String province;
        private String country;
        private String address;
        private int isUsed;
        private String receiveName;
        private String phone;

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreater() {
            return creater;
        }

        public void setCreater(Object creater) {
            this.creater = creater;
        }

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
            this.updater = updater;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public Object getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(Object isDefault) {
            this.isDefault = isDefault;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(int isUsed) {
            this.isUsed = isUsed;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
