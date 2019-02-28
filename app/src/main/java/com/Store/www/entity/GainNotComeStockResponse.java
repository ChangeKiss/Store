package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/15.
 * 获取未出库商品响应体
 */

public class GainNotComeStockResponse {

    /**
     * returnValue : 1
     * totalNum : 8
     * list : [{"color":"品红","size":"L","name":"蕾丝女士能量裤","count":1,"id":184},{"color":"品红","size":"XL","name":"蕾丝女士能量裤","count":1,"id":185},{"color":"品红","size":"2XL","name":"蕾丝女士能量裤","count":1,"id":186},{"color":"品红","size":"M","name":"蕾丝女士能量裤","count":5,"id":187}]
     */

    private int returnValue;
    private int totalNum;
    private List<ListBean> list;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * color : 品红
         * size : L
         * name : 蕾丝女士能量裤
         * count : 1
         * id : 184
         */

        private String color;
        private String size;
        private String name;
        private int count;
        private int id;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
