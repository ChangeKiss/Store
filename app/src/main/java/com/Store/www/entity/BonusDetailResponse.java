package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/7/10.
 * 分红详情响应体 弹窗的
 */

public class BonusDetailResponse {

    /**
     * returnValue : 1
     * data : [{"monthAchievement":458000,"codeAndName":"罗吕飞(JWD0817144)","bonus":6183,"rate":15},{"monthAchievement":734500,"codeAndName":"林海清(JWB0730408)","bonus":9915,"rate":15},{"monthAchievement":605500,"codeAndName":"赖梅玉(JWD1605300582)","bonus":8174,"rate":15},{"monthAchievement":0,"codeAndName":"陈勤(JW20170720810082)","bonus":0,"rate":45},{"monthAchievement":1579800,"codeAndName":"刘红秀(JWE1225293)","bonus":21327,"rate":15},{"monthAchievement":2059500,"codeAndName":"张勋(JW040725)","bonus":9267,"rate":5},{"monthAchievement":253500,"codeAndName":"王风华(JWD1007178)","bonus":3422,"rate":15},{"monthAchievement":936000,"codeAndName":"金招萍(JWB0615105)","bonus":12636,"rate":15},{"monthAchievement":9450000,"codeAndName":"黄小梅(JWA05126)","bonus":47250,"rate":0},{"monthAchievement":244500,"codeAndName":"张细艳(JWB1221294)","bonus":3300,"rate":15},{"monthAchievement":260500,"codeAndName":"郭月利(JWE1610100396)","bonus":3516,"rate":15},{"monthAchievement":592500,"codeAndName":"夏琦(JWE1602180293)","bonus":7998,"rate":15},{"monthAchievement":228500,"codeAndName":"范晓清(JWE1609250250)","bonus":3084,"rate":15},{"monthAchievement":255000,"codeAndName":"张伟丽(JWC111516)","bonus":10327,"rate":45},{"monthAchievement":245500,"codeAndName":"肖慧(JWD0827117)","bonus":613,"rate":0},{"monthAchievement":1141500,"codeAndName":"黄碧玲(JWH1606200869)","bonus":15410,"rate":15},{"monthAchievement":244500,"codeAndName":"储洁敏(JWC1106126)","bonus":3300,"rate":15},{"monthAchievement":252000,"codeAndName":"张影霞(JWB112619)","bonus":3402,"rate":15},{"monthAchievement":256500,"codeAndName":"周莉芬(JWF1607050391)","bonus":3462,"rate":15},{"monthAchievement":253500,"codeAndName":"刘艳丹(JWD1601120024)","bonus":3422,"rate":15},{"monthAchievement":246500,"codeAndName":"陈清燕(JWC0805207)","bonus":3327,"rate":15},{"monthAchievement":239500,"codeAndName":"张妍(JWC0805125)","bonus":3233,"rate":15},{"monthAchievement":1192500,"codeAndName":"李元强(JW040126)","bonus":26831,"rate":25},{"monthAchievement":177000,"codeAndName":"罗丽平(JWE1221232)","bonus":2389,"rate":15},{"monthAchievement":229500,"codeAndName":"高玉莹(JWC1209104)","bonus":3098,"rate":15},{"monthAchievement":503000,"codeAndName":"刘秋花(JWD1609130027)","bonus":20371,"rate":45},{"monthAchievement":949000,"codeAndName":"谢金凤(JWE1223175)","bonus":12811,"rate":15},{"monthAchievement":1589500,"codeAndName":"刘泉秀(JWD0822180)","bonus":21458,"rate":15},{"monthAchievement":247500,"codeAndName":"郑海艳(JWD1702250041)","bonus":3341,"rate":15},{"monthAchievement":513000,"codeAndName":"汤艳红(JWC092576)","bonus":6925,"rate":15},{"monthAchievement":255500,"codeAndName":"吕凤珍(JWB112180)","bonus":1277,"rate":0},{"monthAchievement":531000,"codeAndName":"李静(JWD0813198)","bonus":7168,"rate":15},{"monthAchievement":354000,"codeAndName":"徐希梅(JWD1604280404)","bonus":1593,"rate":5},{"monthAchievement":354000,"codeAndName":"王君芬(JWC1609270007)","bonus":4779,"rate":15},{"monthAchievement":354000,"codeAndName":"段军霞(JWG1601080215)","bonus":4779,"rate":15},{"monthAchievement":354000,"codeAndName":"郞晓青(JWC1605040495)","bonus":14337,"rate":45},{"monthAchievement":354000,"codeAndName":"黄文香(JWC0930150)","bonus":4779,"rate":15},{"monthAchievement":531000,"codeAndName":"李艳秋(JWB062568)","bonus":21505,"rate":45},{"monthAchievement":354000,"codeAndName":"周欢(JWD1223279)","bonus":4779,"rate":15},{"monthAchievement":354000,"codeAndName":"王静(JWC1603020271)","bonus":7965,"rate":25},{"monthAchievement":0,"codeAndName":"张常碧(JWD1229102)","bonus":0,"rate":45},{"monthAchievement":0,"codeAndName":"徐亚丽(JW20170720808736)","bonus":0,"rate":5},{"monthAchievement":0,"codeAndName":"徐佩佩(JWC072346)","bonus":0,"rate":25},{"monthAchievement":0,"codeAndName":"陆燕君(JWF1605090691)","bonus":0,"rate":0},{"monthAchievement":0,"codeAndName":"徐新月(JWF0824296)","bonus":0,"rate":55},{"monthAchievement":0,"codeAndName":"卢清霞(JWE1602290761)","bonus":0,"rate":15},{"monthAchievement":0,"codeAndName":"郭俊囡(JWD122882)","bonus":0,"rate":15},{"monthAchievement":0,"codeAndName":"王潇伟(JWE1603170249)","bonus":0,"rate":15},{"monthAchievement":0,"codeAndName":"康舒琴(JWA041326)","bonus":0,"rate":15},{"monthAchievement":0,"codeAndName":"陈艳利(JWH1601080146)","bonus":0,"rate":15},{"monthAchievement":0,"codeAndName":"陈美玲(JWF1602270529)","bonus":0,"rate":15}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataBean> data;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

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
         * monthAchievement : 458000
         * codeAndName : 罗吕飞(JWD0817144)
         * bonus : 6183
         * rate : 15
         */

        private int monthAchievement;
        private String codeAndName;
        private int bonus;
        private int rate;

        public int getMonthAchievement() {
            return monthAchievement;
        }

        public void setMonthAchievement(int monthAchievement) {
            this.monthAchievement = monthAchievement;
        }

        public String getCodeAndName() {
            return codeAndName;
        }

        public void setCodeAndName(String codeAndName) {
            this.codeAndName = codeAndName;
        }

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }
    }
}
