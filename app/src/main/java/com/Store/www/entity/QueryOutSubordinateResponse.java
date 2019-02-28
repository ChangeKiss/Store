package com.Store.www.entity;

/**
 * @author: haifeng
 * @description: 查询出库下级响应体
 */
public class QueryOutSubordinateResponse extends BaseBenTwo{

    /**
     * returnValue : 1
     * data : {"headPicture":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/822134_1517554019531_headImage.jpg","phone":"13500000000","parentCode":"K20180201822125","agentCode":"K20180202822134","name":"kivie","idNumber":"3302"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * headPicture : http://jwbucket.oss-cn-shanghai.aliyuncs.com/822134_1517554019531_headImage.jpg
         * phone : 13500000000
         * parentCode : K20180201822125
         * agentCode : K20180202822134
         * name : kivie
         * idNumber : 3302
         */

        private String headPicture;
        private String phone;
        private String parentCode;
        private String agentCode;
        private String name;
        private String idNumber;

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getAgentCode() {
            return agentCode;
        }

        public void setAgentCode(String agentCode) {
            this.agentCode = agentCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
    }
}
