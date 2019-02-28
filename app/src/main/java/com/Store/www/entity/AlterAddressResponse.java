package com.Store.www.entity;

import java.util.List;

/**
 * 修改地址响应体
 */

public class AlterAddressResponse {


    /**
     * returnValue : 1
     * data : {"cityList":[{"id":2,"parentId":0,"name":"北京市"},{"id":3,"parentId":0,"name":"上海市"},{"id":4,"parentId":0,"name":"天津市"},{"id":5,"parentId":0,"name":"重庆市"},{"id":6,"parentId":0,"name":"河北省"},{"id":7,"parentId":0,"name":"山西省"},{"id":8,"parentId":0,"name":"内蒙古"},{"id":9,"parentId":0,"name":"辽宁省"},{"id":10,"parentId":0,"name":"吉林省"},{"id":11,"parentId":0,"name":"黑龙江省"},{"id":12,"parentId":0,"name":"江苏省"},{"id":13,"parentId":0,"name":"浙江省"},{"id":14,"parentId":0,"name":"安徽省"},{"id":15,"parentId":0,"name":"福建省"},{"id":16,"parentId":0,"name":"江西省"},{"id":17,"parentId":0,"name":"山东省"},{"id":18,"parentId":0,"name":"河南省"},{"id":19,"parentId":0,"name":"湖北省"},{"id":20,"parentId":0,"name":"湖南省"},{"id":21,"parentId":0,"name":"广东省"},{"id":22,"parentId":0,"name":"广西"},{"id":23,"parentId":0,"name":"海南省"},{"id":24,"parentId":0,"name":"四川省"},{"id":25,"parentId":0,"name":"贵州省"},{"id":26,"parentId":0,"name":"云南省"},{"id":27,"parentId":0,"name":"西藏"},{"id":28,"parentId":0,"name":"陕西省"},{"id":29,"parentId":0,"name":"甘肃省"},{"id":30,"parentId":0,"name":"青海省"},{"id":31,"parentId":0,"name":"宁夏"},{"id":32,"parentId":0,"name":"新疆"},{"id":33,"parentId":0,"name":"台湾省"},{"id":34,"parentId":0,"name":"香港"},{"id":35,"parentId":0,"name":"澳门"},{"id":99,"parentId":0,"name":"海外"},{"id":3358,"parentId":0,"name":"钓鱼岛"}]}
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
        private List<CityListBean> cityList;

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public static class CityListBean {
            /**
             * id : 2
             * parentId : 0
             * name : 北京市
             */

            private int id;
            private int parentId;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
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
