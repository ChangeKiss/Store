package com.Store.www.entity;

/**
 * 验证推荐人响应体
 */

public class VerifyPeopleResponse extends BaseBenTwo{

    /**
     * errMsg : 成功
     * jsrAgent : {"createTime":1469462400000,"updateTime":1486656000000,"creater":"admin","updater":"admin","isDelete":0,"id":252699,"parentId":252699,"idNumber":"330324198506181111","team":null,"headPicture":"打底裤APP主图.jpg","certificateUrl":"","examineTime":null,"code":"18857738393","experienceReport":null,"typeName":null,"typeCode":null,"name":"胡巍-测试用","wb":"","email":"hlw@kivie.com","qq":"","mobilephone":"18857738393","account":"","type":null,"nature":9,"natureName":"大陆代理","password":"e10adc3949ba59abbe56e057f20f883e","sonId":null,"status":0,"chain":",18857738393,","approver":null,"applyTime":"2017-02-10","teamCode":null,"billNumber":"","level":7,"auditTime":1486656000000,"auditName":"系统管理员","levelTime":null,"leveler":null,"levelStatus":null,"teamName":null,"agentNum":"18857738393","weChat":"18857738393","token":"38e1bb60da6a867443ebfe617cb3d720","levelName":"台湾钻石代理","parentCode":"18857738393","failCause":null,"directCode":"18857738393","age":null,"parentName":"胡巍-测试用","directName":"胡巍-测试用","depth":10,"statusCode":"ACTV","agentAdress":""}
     */


    private JsrAgentBean jsrAgent;



    public JsrAgentBean getJsrAgent() {
        return jsrAgent;
    }

    public void setJsrAgent(JsrAgentBean jsrAgent) {
        this.jsrAgent = jsrAgent;
    }

    public static class JsrAgentBean {
        /**
         * createTime : 1469462400000
         * updateTime : 1486656000000
         * creater : admin
         * updater : admin
         * isDelete : 0
         * id : 252699
         * parentId : 252699
         * idNumber : 330324198506181111
         * team : null
         * headPicture : 打底裤APP主图.jpg
         * certificateUrl :
         * examineTime : null
         * code : 18857738393
         * experienceReport : null
         * typeName : null
         * typeCode : null
         * name : 胡巍-测试用
         * wb :
         * email : hlw@kivie.com
         * qq :
         * mobilephone : 18857738393
         * account :
         * type : null
         * nature : 9
         * natureName : 大陆代理
         * password : e10adc3949ba59abbe56e057f20f883e
         * sonId : null
         * status : 0
         * chain : ,18857738393,
         * approver : null
         * applyTime : 2017-02-10
         * teamCode : null
         * billNumber :
         * level : 7
         * auditTime : 1486656000000
         * auditName : 系统管理员
         * levelTime : null
         * leveler : null
         * levelStatus : null
         * teamName : null
         * agentNum : 18857738393
         * weChat : 18857738393
         * token : 38e1bb60da6a867443ebfe617cb3d720
         * levelName : 台湾钻石代理
         * parentCode : 18857738393
         * failCause : null
         * directCode : 18857738393
         * age : null
         * parentName : 胡巍-测试用
         * directName : 胡巍-测试用
         * depth : 10
         * statusCode : ACTV
         * agentAdress :
         */

        private long createTime;
        private long updateTime;
        private String creater;
        private String updater;
        private int isDelete;
        private int id;
        private int parentId;
        private String idNumber;
        private Object team;
        private String headPicture;
        private String certificateUrl;
        private Object examineTime;
        private String code;
        private Object experienceReport;
        private Object typeName;
        private Object typeCode;
        private String name;
        private String wb;
        private String email;
        private String qq;
        private String mobilephone;
        private String account;
        private Object type;
        private int nature;
        private String natureName;
        private String password;
        private Object sonId;
        private int status;
        private String chain;
        private Object approver;
        private String applyTime;
        private Object teamCode;
        private String billNumber;
        private int level;
        private long auditTime;
        private String auditName;
        private Object levelTime;
        private Object leveler;
        private Object levelStatus;
        private Object teamName;
        private String agentNum;
        private String weChat;
        private String token;
        private String levelName;
        private String parentCode;
        private Object failCause;
        private String directCode;
        private Object age;
        private String parentName;
        private String directName;
        private int depth;
        private String statusCode;
        private String agentAdress;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getUpdater() {
            return updater;
        }

        public void setUpdater(String updater) {
            this.updater = updater;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public Object getTeam() {
            return team;
        }

        public void setTeam(Object team) {
            this.team = team;
        }

        public String getHeadPicture() {
            return headPicture;
        }

        public void setHeadPicture(String headPicture) {
            this.headPicture = headPicture;
        }

        public String getCertificateUrl() {
            return certificateUrl;
        }

        public void setCertificateUrl(String certificateUrl) {
            this.certificateUrl = certificateUrl;
        }

        public Object getExamineTime() {
            return examineTime;
        }

        public void setExamineTime(Object examineTime) {
            this.examineTime = examineTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getExperienceReport() {
            return experienceReport;
        }

        public void setExperienceReport(Object experienceReport) {
            this.experienceReport = experienceReport;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public Object getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(Object typeCode) {
            this.typeCode = typeCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWb() {
            return wb;
        }

        public void setWb(String wb) {
            this.wb = wb;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getNature() {
            return nature;
        }

        public void setNature(int nature) {
            this.nature = nature;
        }

        public String getNatureName() {
            return natureName;
        }

        public void setNatureName(String natureName) {
            this.natureName = natureName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getSonId() {
            return sonId;
        }

        public void setSonId(Object sonId) {
            this.sonId = sonId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getChain() {
            return chain;
        }

        public void setChain(String chain) {
            this.chain = chain;
        }

        public Object getApprover() {
            return approver;
        }

        public void setApprover(Object approver) {
            this.approver = approver;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public Object getTeamCode() {
            return teamCode;
        }

        public void setTeamCode(Object teamCode) {
            this.teamCode = teamCode;
        }

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public long getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(long auditTime) {
            this.auditTime = auditTime;
        }

        public String getAuditName() {
            return auditName;
        }

        public void setAuditName(String auditName) {
            this.auditName = auditName;
        }

        public Object getLevelTime() {
            return levelTime;
        }

        public void setLevelTime(Object levelTime) {
            this.levelTime = levelTime;
        }

        public Object getLeveler() {
            return leveler;
        }

        public void setLeveler(Object leveler) {
            this.leveler = leveler;
        }

        public Object getLevelStatus() {
            return levelStatus;
        }

        public void setLevelStatus(Object levelStatus) {
            this.levelStatus = levelStatus;
        }

        public Object getTeamName() {
            return teamName;
        }

        public void setTeamName(Object teamName) {
            this.teamName = teamName;
        }

        public String getAgentNum() {
            return agentNum;
        }

        public void setAgentNum(String agentNum) {
            this.agentNum = agentNum;
        }

        public String getWeChat() {
            return weChat;
        }

        public void setWeChat(String weChat) {
            this.weChat = weChat;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public Object getFailCause() {
            return failCause;
        }

        public void setFailCause(Object failCause) {
            this.failCause = failCause;
        }

        public String getDirectCode() {
            return directCode;
        }

        public void setDirectCode(String directCode) {
            this.directCode = directCode;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getDirectName() {
            return directName;
        }

        public void setDirectName(String directName) {
            this.directName = directName;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getAgentAdress() {
            return agentAdress;
        }

        public void setAgentAdress(String agentAdress) {
            this.agentAdress = agentAdress;
        }
    }
}
