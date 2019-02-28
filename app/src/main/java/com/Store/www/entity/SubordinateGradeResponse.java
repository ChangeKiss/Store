package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 直属下级业绩响应体
 */
public class SubordinateGradeResponse {

    /**
     * returnValue : 1
     * dataList : [{"name":"马睿","audited":0,"unaudited":0},{"name":"麦敏芳","audited":0,"unaudited":0},{"name":"张蔡叶","audited":0,"unaudited":0},{"name":"杜丽萍","audited":0,"unaudited":0},{"name":"田瑞丽","audited":0,"unaudited":0},{"name":"殷雪芬","audited":0,"unaudited":0},{"name":"钱黄慧","audited":0,"unaudited":0},{"name":"朱乔平","audited":0,"unaudited":0},{"name":"王娴","audited":0,"unaudited":0},{"name":"崔雅宁","audited":0,"unaudited":0},{"name":"罗茜","audited":0,"unaudited":0},{"name":"徐爱侠","audited":0,"unaudited":0},{"name":"黄莉莉","audited":0,"unaudited":0},{"name":"廖飞","audited":0,"unaudited":0},{"name":"张世霞","audited":0,"unaudited":0},{"name":"周晶","audited":0,"unaudited":0},{"name":"吴年年","audited":0,"unaudited":0},{"name":"梁漫","audited":0,"unaudited":0},{"name":"KIVIE取消代理资格潘丽","audited":0,"unaudited":0},{"name":"王利平","audited":0,"unaudited":0},{"name":"朱芬芬","audited":0,"unaudited":0},{"name":"胡项青","audited":0,"unaudited":0},{"name":"王汉林","audited":0,"unaudited":0},{"name":"李路娟","audited":0,"unaudited":0},{"name":"徐丽","audited":0,"unaudited":0},{"name":"马冬梅","audited":0,"unaudited":0},{"name":"周保芹","audited":0,"unaudited":0},{"name":"李静乐","audited":0,"unaudited":0},{"name":"王家换","audited":0,"unaudited":0},{"name":"戴金玲","audited":0,"unaudited":0}]
     * errMsg : 成功
     */

    private int returnValue;
    private String errMsg;
    private List<DataListBean> dataList;

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

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * name : 马睿
         * audited : 0
         * unaudited : 0
         */

        private String name;
        private int audited;
        private int unaudited;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAudited() {
            return audited;
        }

        public void setAudited(int audited) {
            this.audited = audited;
        }

        public int getUnaudited() {
            return unaudited;
        }

        public void setUnaudited(int unaudited) {
            this.unaudited = unaudited;
        }
    }
}
