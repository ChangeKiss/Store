package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/10/24.
 * 扫描记录时间响应体
 */

public class ScanRecordTimeResponse {


    /**
     * returnValue : 1
     * data : [{"date":1540345699000,"count":1},{"date":1540428605000,"count":2}]
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
         * date : 1540345699000
         * count : 1
         */

        private long date;
        private int count;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


    }
}
