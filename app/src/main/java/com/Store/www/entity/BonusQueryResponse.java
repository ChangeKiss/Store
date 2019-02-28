package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/7/9.
 * 分红查询响应体
 */

public class BonusQueryResponse {

    /**
     * returnValue : 1
     * data : {"list":[{"dateTotalBonus":2997079,"month":"201806","dateTeamBonus":2788032,"datePerBonus":209047},{"dateTotalBonus":660802,"month":201805,"dateTeamBonus":531270,"datePerBonus":129532},{"dateTotalBonus":295732,"month":201804,"dateTeamBonus":285283,"datePerBonus":10449},{"dateTotalBonus":4352234,"month":201803,"dateTeamBonus":3050386,"datePerBonus":1301848},{"dateTotalBonus":32035,"month":201802,"dateTeamBonus":32035,"datePerBonus":0},{"dateTotalBonus":133730,"month":201801,"dateTeamBonus":133730,"datePerBonus":0},{"dateTotalBonus":572683,"month":201712,"dateTeamBonus":484393,"datePerBonus":88290},{"dateTotalBonus":2182225,"month":201711,"dateTeamBonus":214330,"datePerBonus":1967895},{"dateTotalBonus":234301,"month":201710,"dateTeamBonus":234301,"datePerBonus":0},{"dateTotalBonus":253551,"month":201709,"dateTeamBonus":214311,"datePerBonus":39240},{"dateTotalBonus":7124921,"month":201708,"dateTeamBonus":6438653,"datePerBonus":686268},{"dateTotalBonus":2279291,"month":201707,"dateTeamBonus":1969007,"datePerBonus":310284},{"dateTotalBonus":4720284,"month":201706,"dateTeamBonus":4074084,"datePerBonus":646200},{"dateTotalBonus":627302,"month":201705,"dateTeamBonus":519230,"datePerBonus":108072}],"historyTotal":26466170,"totalBonus":0,"historyTeamBonus":20969045,"historyBraNum":32181,"chdMonth":"201807","perBonus":0,"monthAchievement":0,"historyPerBonus":5497125,"teamBonus":0,"chd":"201807分红","rate":905}
     * errMsg : 成功
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
        /**
         * list : [{"dateTotalBonus":2997079,"month":"201806","dateTeamBonus":2788032,"datePerBonus":209047},{"dateTotalBonus":660802,"month":201805,"dateTeamBonus":531270,"datePerBonus":129532},{"dateTotalBonus":295732,"month":201804,"dateTeamBonus":285283,"datePerBonus":10449},{"dateTotalBonus":4352234,"month":201803,"dateTeamBonus":3050386,"datePerBonus":1301848},{"dateTotalBonus":32035,"month":201802,"dateTeamBonus":32035,"datePerBonus":0},{"dateTotalBonus":133730,"month":201801,"dateTeamBonus":133730,"datePerBonus":0},{"dateTotalBonus":572683,"month":201712,"dateTeamBonus":484393,"datePerBonus":88290},{"dateTotalBonus":2182225,"month":201711,"dateTeamBonus":214330,"datePerBonus":1967895},{"dateTotalBonus":234301,"month":201710,"dateTeamBonus":234301,"datePerBonus":0},{"dateTotalBonus":253551,"month":201709,"dateTeamBonus":214311,"datePerBonus":39240},{"dateTotalBonus":7124921,"month":201708,"dateTeamBonus":6438653,"datePerBonus":686268},{"dateTotalBonus":2279291,"month":201707,"dateTeamBonus":1969007,"datePerBonus":310284},{"dateTotalBonus":4720284,"month":201706,"dateTeamBonus":4074084,"datePerBonus":646200},{"dateTotalBonus":627302,"month":201705,"dateTeamBonus":519230,"datePerBonus":108072}]
         * historyTotal : 26466170
         * totalBonus : 0
         * historyTeamBonus : 20969045
         * historyBraNum : 32181
         * chdMonth : 201807
         * perBonus : 0
         * monthAchievement : 0
         * historyPerBonus : 5497125
         * teamBonus : 0
         * chd : 201807分红
         * rate : 905
         */

        private int historyTotal;
        private int totalBonus;
        private int historyTeamBonus;
        private int historyBraNum;
        private String chdMonth;
        private int perBonus;
        private int monthAchievement;
        private int historyPerBonus;
        private int teamBonus;
        private String chd;
        private int rate;
        private List<ListBean> list;

        public int getHistoryTotal() {
            return historyTotal;
        }

        public void setHistoryTotal(int historyTotal) {
            this.historyTotal = historyTotal;
        }

        public int getTotalBonus() {
            return totalBonus;
        }

        public void setTotalBonus(int totalBonus) {
            this.totalBonus = totalBonus;
        }

        public int getHistoryTeamBonus() {
            return historyTeamBonus;
        }

        public void setHistoryTeamBonus(int historyTeamBonus) {
            this.historyTeamBonus = historyTeamBonus;
        }

        public int getHistoryBraNum() {
            return historyBraNum;
        }

        public void setHistoryBraNum(int historyBraNum) {
            this.historyBraNum = historyBraNum;
        }

        public String getChdMonth() {
            return chdMonth;
        }

        public void setChdMonth(String chdMonth) {
            this.chdMonth = chdMonth;
        }

        public int getPerBonus() {
            return perBonus;
        }

        public void setPerBonus(int perBonus) {
            this.perBonus = perBonus;
        }

        public int getMonthAchievement() {
            return monthAchievement;
        }

        public void setMonthAchievement(int monthAchievement) {
            this.monthAchievement = monthAchievement;
        }

        public int getHistoryPerBonus() {
            return historyPerBonus;
        }

        public void setHistoryPerBonus(int historyPerBonus) {
            this.historyPerBonus = historyPerBonus;
        }

        public int getTeamBonus() {
            return teamBonus;
        }

        public void setTeamBonus(int teamBonus) {
            this.teamBonus = teamBonus;
        }

        public String getChd() {
            return chd;
        }

        public void setChd(String chd) {
            this.chd = chd;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * dateTotalBonus : 2997079
             * month : 201806
             * dateTeamBonus : 2788032
             * datePerBonus : 209047
             */

            private int dateTotalBonus;
            private String month;
            private int dateTeamBonus;
            private int datePerBonus;

            public int getDateTotalBonus() {
                return dateTotalBonus;
            }

            public void setDateTotalBonus(int dateTotalBonus) {
                this.dateTotalBonus = dateTotalBonus;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public int getDateTeamBonus() {
                return dateTeamBonus;
            }

            public void setDateTeamBonus(int dateTeamBonus) {
                this.dateTeamBonus = dateTeamBonus;
            }

            public int getDatePerBonus() {
                return datePerBonus;
            }

            public void setDatePerBonus(int datePerBonus) {
                this.datePerBonus = datePerBonus;
            }
        }
    }
}
