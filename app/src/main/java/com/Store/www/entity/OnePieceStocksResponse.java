package com.Store.www.entity;

import java.util.List;

/**
 * 单件商品库存的响应体
 */

public class OnePieceStocksResponse extends BaseBean{

    /**
     * data : [{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0170A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":"","color":"黑色","colorCode":"H01","size":"70A","sizeCode":"70A","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0170B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"70B","sizeCode":"70B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0175A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"75A","sizeCode":"75A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0175B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"75B","sizeCode":"75B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0175C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"75C","sizeCode":"75C","repositoryCount":"2","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0180A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"80A","sizeCode":"80A","repositoryCount":"3","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0180B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"80B","sizeCode":"80B","repositoryCount":"2","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0180C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"80C","sizeCode":"80C","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0185A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"85A","sizeCode":"85A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601H0185B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"黑色","colorCode":"H01","size":"85B","sizeCode":"85B","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0170A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"70A","sizeCode":"70A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0170B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"70B","sizeCode":"70B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0175A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"75A","sizeCode":"75A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0175B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"75B","sizeCode":"75B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0175C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"75C","sizeCode":"75C","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0180A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"80A","sizeCode":"80A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0180B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"80B","sizeCode":"80B","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0180C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"80C","sizeCode":"80C","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0185A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"85A","sizeCode":"85A","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601Y0185B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"肤色","colorCode":"Y01","size":"85B","sizeCode":"85B","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0170A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"70A","sizeCode":"70A","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0170B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"70B","sizeCode":"70B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0175A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"75A","sizeCode":"75A","repositoryCount":"2","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0175B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"75B","sizeCode":"75B","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0175C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"75C","sizeCode":"75C","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0180A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"80A","sizeCode":"80A","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0180B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"80B","sizeCode":"80B","repositoryCount":"0","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0180C","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"80C","sizeCode":"80C","repositoryCount":"2","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0185A","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"85A","sizeCode":"85A","repositoryCount":"1","type":1,"possession":0,"agentnum":null},{"createTime":null,"updateTime":null,"creater":null,"updater":null,"isDelete":null,"id":null,"respositoryId":null,"sku":"JW-W1601X0185B","productName":"金薇呼吸款胸罩","productNo":"JW-W1601","typeName":null,"natureName":null,"color":"小豹纹","colorCode":"X01","size":"85B","sizeCode":"85B","repositoryCount":"0","type":1,"possession":0,"agentnum":null}]
     * errMsg : 成功
     */

    private String errMsg;
    private List<DataBean> data;

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
         * createTime : null
         * updateTime : null
         * creater : null
         * updater : null
         * isDelete : null
         * id : null
         * respositoryId : null
         * sku : JW-W1601H0170A
         * productName : 金薇呼吸款胸罩
         * productNo : JW-W1601
         * typeName : null
         * natureName :
         * color : 黑色
         * colorCode : H01
         * size : 70A
         * sizeCode : 70A
         * repositoryCount : 0
         * type : 1
         * possession : 0
         * agentnum : null
         *"isEnable": 1,
         */

        private Object createTime;
        private Object updateTime;
        private Object creater;
        private Object updater;
        private Object isDelete;
        private Object id;
        private Object respositoryId;
        private String sku;
        private String productName;
        private String productNo;
        private Object typeName;
        private String natureName;
        private String color;
        private String colorCode;
        private String size;
        private String sizeCode;
        private String repositoryCount;
        private int type;
        private int possession;
        private Object agentnum;
        private int isEnable;

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreater() {
            return creater;
        }

        public void setCreater(Object creater) {
            this.creater = creater;
        }

        public Object getUpdater() {
            return updater;
        }

        public void setUpdater(Object updater) {
            this.updater = updater;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getRespositoryId() {
            return respositoryId;
        }

        public void setRespositoryId(Object respositoryId) {
            this.respositoryId = respositoryId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public Object getTypeName() {
            return typeName;
        }

        public void setTypeName(Object typeName) {
            this.typeName = typeName;
        }

        public String getNatureName() {
            return natureName;
        }

        public void setNatureName(String natureName) {
            this.natureName = natureName;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSizeCode() {
            return sizeCode;
        }

        public void setSizeCode(String sizeCode) {
            this.sizeCode = sizeCode;
        }

        public String getRepositoryCount() {
            return repositoryCount;
        }

        public void setRepositoryCount(String repositoryCount) {
            this.repositoryCount = repositoryCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPossession() {
            return possession;
        }

        public void setPossession(int possession) {
            this.possession = possession;
        }

        public Object getAgentnum() {
            return agentnum;
        }

        public void setAgentnum(Object agentnum) {
            this.agentnum = agentnum;
        }

        public int getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(int isEnable) {
            this.isEnable = isEnable;
        }
    }
}
