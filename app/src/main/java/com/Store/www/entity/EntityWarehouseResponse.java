package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/10.
 * 获取实体仓库响应体
 */

public class EntityWarehouseResponse {

    /**
     * returnValue : 1
     * data : [{"area":"新城区","address":"军事基地2","province":"陕西省","city":"西安市","num":0,"name":"郑盛的军火库1","id":1},{"area":"","address":"健康可口的","province":"北京市","city":"东城区","num":0,"name":"略略略","id":4},{"area":"","address":"会","province":"北京市","city":"东城区","num":0,"name":"你斤斤计较","id":5},{"area":"南关区","address":"我们的团队","province":"吉林省","city":"长春市","num":0,"name":"我们的","id":6}]
     */

    private int returnValue;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 新城区
         * address : 军事基地2
         * province : 陕西省
         * city : 西安市
         * num : 0
         * name : 郑盛的军火库1
         * id : 1
         */

        private String area;
        private String address;
        private String province;
        private String city;
        private int num;
        private String name;
        private int id;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
