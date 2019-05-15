package com.Store.www.test;

import java.util.List;

/**
 * Created by www on 2017/12/13.
 */
//测试用

    public class Mode {

        private List<DateBean> Bean;

    public List<DateBean> getBean() {
        return Bean;
    }

    public void setBean(List<DateBean> bean) {
        Bean = bean;
    }

    public static class DateBean {
            private String money;
            private int height;
            private String url;
            private String name;
            private String time;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

}

