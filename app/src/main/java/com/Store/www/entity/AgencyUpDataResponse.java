package com.Store.www.entity;

import java.util.List;

/**
 * Created by www on 2018/8/31.
 * 代理升级响应体
 */

public class AgencyUpDataResponse {


    /**
     * returnValue : 1
     * data : {"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_1532584616485_headImage_0.jpg","isApply":1,"allList":[{"condition":"累计下单10件","name":"VIP客户","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/505fc848-deb2-41cf-9ff0-84ddbd6a6047.png"},{"condition":"累计下单100件","name":"经销商","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/30c7616e-f07c-4304-9ff3-5086aa3526a1.png"},{"condition":"累计下单1000件","name":"代理商","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/74988856-f94c-4767-8adc-078619d3bd0e.png"},{"condition":"累计下单10000件","name":"一级代理","list":[{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/39a20f81-1c73-41c8-b8e0-b20df03c631d.png"},{"condition":"累计下单100000件","name":"钻石总代","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"授权证书","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/8d540060-e9e8-4ff3-bb13-4d828ea749db.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"},{"powerName":"11111","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/8d241d7a-3c06-416c-aa32-16231979caa0.jpg"},{"powerName":"22222","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/0c5aa181-0045-444b-9015-ad7d4eb5e4de.jpg"},{"powerName":"333333","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/94acd806-6517-4534-b4b5-b5cf13e6749d.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/deb3bd2c-d1ab-4585-8d51-81bb6d123e00.png"}],"refusal":"null","agentName":"郑盛测试","levelName":"钻石总代","fictitiousName":"钻石总代"}
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
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822125_1532584616485_headImage_0.jpg
         * isApply : 1
         * allList : [{"condition":"累计下单10件","name":"VIP客户","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/505fc848-deb2-41cf-9ff0-84ddbd6a6047.png"},{"condition":"累计下单100件","name":"经销商","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/30c7616e-f07c-4304-9ff3-5086aa3526a1.png"},{"condition":"累计下单1000件","name":"代理商","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/74988856-f94c-4767-8adc-078619d3bd0e.png"},{"condition":"累计下单10000件","name":"一级代理","list":[{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/39a20f81-1c73-41c8-b8e0-b20df03c631d.png"},{"condition":"累计下单100000件","name":"钻石总代","list":[{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"授权证书","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/8d540060-e9e8-4ff3-bb13-4d828ea749db.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"},{"powerName":"11111","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/8d241d7a-3c06-416c-aa32-16231979caa0.jpg"},{"powerName":"22222","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/0c5aa181-0045-444b-9015-ad7d4eb5e4de.jpg"},{"powerName":"333333","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/94acd806-6517-4534-b4b5-b5cf13e6749d.jpg"}],"chdPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/deb3bd2c-d1ab-4585-8d51-81bb6d123e00.png"}]
         * refusal : null
         * agentName : 郑盛测试
         * levelName : 钻石总代
         * fictitiousName : 钻石总代
         */

        private String headPicture;
        private int isApply;
        private int isDiamonds;
        private String refusal;
        private String agentName;
        private String levelName;
        private String fictitiousName;
        private List<AllListBean> allList;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public int getIsApply() {
            return isApply;
        }

        public void setIsApply(int isApply) {
            this.isApply = isApply;
        }

        public int getIsDiamonds() {
            return isDiamonds;
        }

        public void setIsDiamonds(int isDiamonds) {
            this.isDiamonds = isDiamonds;
        }

        public String getRefusal() {
            return refusal;
        }

        public void setRefusal(String refusal) {
            this.refusal = refusal;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getFictitiousName() {
            return fictitiousName;
        }

        public void setFictitiousName(String fictitiousName) {
            this.fictitiousName = fictitiousName;
        }

        public List<AllListBean> getAllList() {
            return allList;
        }

        public void setAllList(List<AllListBean> allList) {
            this.allList = allList;
        }

        public static class AllListBean {
            /**
             * condition : 累计下单10件
             * name : VIP客户
             * list : [{"powerName":"招收代理","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg"},{"powerName":"购物打折","picture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/635c1323-ab14-437d-982a-5c6692488ebc.jpg"}]
             * chdPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/505fc848-deb2-41cf-9ff0-84ddbd6a6047.png
             */

            private String condition;
            private String name;
            private String chdPicture;
            private List<ListBean> list;

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getChdPicture() {
                return chdPicture;
            }

            public void setChdPicture(String chdPicture) {
                this.chdPicture = chdPicture;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * powerName : 招收代理
                 * picture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/cb377ba0-e4d6-4845-a6f3-329348ce1afc.jpg
                 */

                private String powerName;
                private String picture;
                private String describe;

                public String getPowerName() {
                    return powerName;
                }

                public void setPowerName(String powerName) {
                    this.powerName = powerName;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }

                public String getDescribe() {
                    return describe;
                }

                public void setDescribe(String describe) {
                    this.describe = describe;
                }
            }
        }
    }
}
