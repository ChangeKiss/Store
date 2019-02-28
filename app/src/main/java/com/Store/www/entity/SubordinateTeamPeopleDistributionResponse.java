package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 直属下级各团队等级人员分布
 */
public class SubordinateTeamPeopleDistributionResponse {

    /**
     * returnValue : 1
     * dataList : [{"small":[{"count":2,"levelName":" 一级代理"},{"count":42,"levelName":"代理商"},{"count":419,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":23,"levelName":"钻石总代"}],"agentName":"马睿"},{"small":[{"count":8,"levelName":" 一级代理"},{"count":55,"levelName":"代理商"},{"count":997,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":90,"levelName":"钻石总代"}],"agentName":"麦敏芳"},{"small":[{"count":4,"levelName":" 一级代理"},{"count":120,"levelName":"代理商"},{"count":1240,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":103,"levelName":"钻石总代"}],"agentName":"张蔡叶"},{"small":[{"count":6,"levelName":" 一级代理"},{"count":30,"levelName":"代理商"},{"count":1747,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":150,"levelName":"钻石总代"}],"agentName":"杜丽萍"},{"small":[{"count":3,"levelName":" 一级代理"},{"count":3,"levelName":"代理商"},{"count":283,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":25,"levelName":"钻石总代"}],"agentName":"田瑞丽"},{"small":[{"count":26,"levelName":" 一级代理"},{"count":187,"levelName":"代理商"},{"count":2328,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":187,"levelName":"钻石总代"}],"agentName":"殷雪芬"},{"small":[{"count":1,"levelName":" 一级代理"},{"count":5,"levelName":"代理商"},{"count":176,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":13,"levelName":"钻石总代"}],"agentName":"钱黄慧"},{"small":[{"count":1,"levelName":" 一级代理"},{"count":14,"levelName":"代理商"},{"count":311,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":26,"levelName":"钻石总代"}],"agentName":"朱乔平"}]
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
         * small : [{"count":2,"levelName":" 一级代理"},{"count":42,"levelName":"代理商"},{"count":419,"levelName":"经销商"},{"count":0,"levelName":"VIP客户"},{"count":23,"levelName":"钻石总代"}]
         * agentName : 马睿
         */

        private String agentName;
        private List<SmallBean> small;

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public List<SmallBean> getSmall() {
            return small;
        }

        public void setSmall(List<SmallBean> small) {
            this.small = small;
        }

        public static class SmallBean {
            /**
             * count : 2
             * levelName :  一级代理
             */

            private int count;
            private String levelName;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }
        }
    }
}
