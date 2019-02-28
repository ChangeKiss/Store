package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/6/7.
 * 代理信息资料响应体
 */

public class AgentDataResponse {

    /**
     * returnValue : 1
     * data : {"code":"K20180410826512","IDCard":"dd","ssyCount":0,"levelName":" 一级代理","promoteSize":0,"ssyYear":2018,"dueTime":11,"headPicture":"http://oss.com","corsetLevelName":"钻石","phone":"18968686868","name":"阿里巴巴","ssyMonth":null,"weChat":"222","prompt":"打款","email":"123.qq.com","ssyLevelList":[{"ssyLevelName":"特约","ssyLevelCondition":"20"},{"ssyLevelName":"二级","ssyLevelCondition":"100"},{"ssyLevelName":"一级","ssyLevelCondition":"240"},{"ssyLevelName":"钻石","ssyLevelCondition":"500"},{"ssyLevelName":"合伙人","ssyLevelCondition":"1000"}]}
     */

    private int returnValue;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : K20180410826512
         * IDCard : dd
         * ssyCount : 0
         * levelName :  一级代理
         * promoteSize : 0
         * ssyYear : 2018
         * dueTime : 11
         * headPicture : http://oss.com
         * corsetLevelName : 钻石
         * phone : 18968686868
         * name : 阿里巴巴
         * ssyMonth : null
         * weChat : 222
         * prompt : 打款
         * email : 123.qq.com
         * ssyLevelList : [{"ssyLevelName":"特约","ssyLevelCondition":"20"},{"ssyLevelName":"二级","ssyLevelCondition":"100"},{"ssyLevelName":"一级","ssyLevelCondition":"240"},{"ssyLevelName":"钻石","ssyLevelCondition":"500"},{"ssyLevelName":"合伙人","ssyLevelCondition":"1000"}]
         */

        private String code;
        private String IDCard;
        private int ssyCount;
        private String levelName;
        private int promoteSize;
        private int ssyYear;
        private int dueTime;
        private String headPicture;
        private String corsetLevelName;
        private String phone;
        private String name;
        private int ssyMonth;
        private String weChat;
        private String prompt;
        private String email;
        private List<SsyLevelListBean> ssyLevelList;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIDCard() {
            return IDCard;
        }

        public void setIDCard(String IDCard) {
            this.IDCard = IDCard;
        }

        public int getSsyCount() {
            return ssyCount;
        }

        public void setSsyCount(int ssyCount) {
            this.ssyCount = ssyCount;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public int getPromoteSize() {
            return promoteSize;
        }

        public void setPromoteSize(int promoteSize) {
            this.promoteSize = promoteSize;
        }

        public int getSsyYear() {
            return ssyYear;
        }

        public void setSsyYear(int ssyYear) {
            this.ssyYear = ssyYear;
        }

        public int getDueTime() {
            return dueTime;
        }

        public void setDueTime(int dueTime) {
            this.dueTime = dueTime;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getCorsetLevelName() {
            return corsetLevelName;
        }

        public void setCorsetLevelName(String corsetLevelName) {
            this.corsetLevelName = corsetLevelName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSsyMonth() {
            return ssyMonth;
        }

        public void setSsyMonth(int ssyMonth) {
            this.ssyMonth = ssyMonth;
        }

        public String getWeChat() {
            return weChat;
        }

        public void setWeChat(String weChat) {
            this.weChat = weChat;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<SsyLevelListBean> getSsyLevelList() {
            return ssyLevelList;
        }

        public void setSsyLevelList(List<SsyLevelListBean> ssyLevelList) {
            this.ssyLevelList = ssyLevelList;
        }

        public static class SsyLevelListBean {
            /**
             * ssyLevelName : 特约
             * disposableGoods : 20
             * ssyLevelCondition : 20
             */

            private String ssyLevelName;
            private String ssyLevelCondition;
            private String disposableGoods;

            public String getSsyLevelName() {
                return ssyLevelName;
            }

            public void setSsyLevelName(String ssyLevelName) {
                this.ssyLevelName = ssyLevelName;
            }

            public String getDisposableGoods() {
                return disposableGoods;
            }

            public void setDisposableGoods(String disposableGoods) {
                this.disposableGoods = disposableGoods;
            }

            public String getSsyLevelCondition() {
                return ssyLevelCondition;
            }

            public void setSsyLevelCondition(String ssyLevelCondition) {
                this.ssyLevelCondition = ssyLevelCondition;
            }
        }
    }
}
