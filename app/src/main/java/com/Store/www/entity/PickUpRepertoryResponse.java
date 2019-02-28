package com.Store.www.entity;

import java.util.List;

/**
 * 提货商品库存的响应体
 */

public class PickUpRepertoryResponse extends BaseBean{

    /**
     * data : {"totalCount":50,"list":[{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"11462","size":"75A","createTime":null,"currentCount":10,"creater":null,"colorCode":"H01","sizeCode":"75A","id":null,"sku":"JW-W1601H0175A","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"11378","size":"75B","createTime":null,"currentCount":0,"creater":null,"colorCode":"H01","sizeCode":"75B","id":null,"sku":"JW-W1601H0175B","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"15096","size":"75C","createTime":null,"currentCount":10,"creater":null,"colorCode":"H01","sizeCode":"75C","id":null,"sku":"JW-W1601H0175C","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"23972","size":"80A","createTime":null,"currentCount":3,"creater":null,"colorCode":"H01","sizeCode":"80A","id":null,"sku":"JW-W1601H0180A","productNo":"JW-W1601"}]}
     * errMsg : 成功
     */

    private DataBean data;
    private String errMsg;

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
         * totalCount : 50
         * list : [{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"11462","size":"75A","createTime":null,"currentCount":10,"creater":null,"colorCode":"H01","sizeCode":"75A","id":null,"sku":"JW-W1601H0175A","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"11378","size":"75B","createTime":null,"currentCount":0,"creater":null,"colorCode":"H01","sizeCode":"75B","id":null,"sku":"JW-W1601H0175B","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"15096","size":"75C","createTime":null,"currentCount":10,"creater":null,"colorCode":"H01","sizeCode":"75C","id":null,"sku":"JW-W1601H0175C","productNo":"JW-W1601"},{"respositoryId":null,"color":"黑色","isDelete":null,"possession":0,"natureName":null,"typeName":null,"updateTime":null,"type":1,"productName":"金薇呼吸款胸罩","updater":null,"isEnable":1,"agentnum":null,"repositoryCount":"23972","size":"80A","createTime":null,"currentCount":3,"creater":null,"colorCode":"H01","sizeCode":"80A","id":null,"sku":"JW-W1601H0180A","productNo":"JW-W1601"}]
         */

        private int totalCount;
        private List<ListBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * respositoryId : null
             * color : 黑色
             * isDelete : null
             * possession : 0
             * natureName : null
             * typeName : null
             * updateTime : null
             * type : 1
             * productName : 金薇呼吸款胸罩
             * updater : null
             * isEnable : 1
             * agentnum : null
             * repositoryCount : 11462
             * size : 75A
             * createTime : null
             * currentCount : 10
             * creater : null
             * colorCode : H01
             * sizeCode : 75A
             * id : null
             * sku : JW-W1601H0175A
             * productNo : JW-W1601
             */

            private Object respositoryId;
            private String color;
            private Object isDelete;
            private int possession;
            private Object natureName;
            private Object typeName;
            private Object updateTime;
            private int type;
            private String productName;
            private Object updater;
            private int isEnable;
            private Object agentnum;
            private String repositoryCount;
            private String size;
            private Object createTime;
            private int currentCount;
            private Object creater;
            private String colorCode;
            private String sizeCode;
            private Object id;
            private String sku;
            private String productNo;

            public Object getRespositoryId() {
                return respositoryId;
            }

            public void setRespositoryId(Object respositoryId) {
                this.respositoryId = respositoryId;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public Object getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(Object isDelete) {
                this.isDelete = isDelete;
            }

            public int getPossession() {
                return possession;
            }

            public void setPossession(int possession) {
                this.possession = possession;
            }

            public Object getNatureName() {
                return natureName;
            }

            public void setNatureName(Object natureName) {
                this.natureName = natureName;
            }

            public Object getTypeName() {
                return typeName;
            }

            public void setTypeName(Object typeName) {
                this.typeName = typeName;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public Object getUpdater() {
                return updater;
            }

            public void setUpdater(Object updater) {
                this.updater = updater;
            }

            public int getIsEnable() {
                return isEnable;
            }

            public void setIsEnable(int isEnable) {
                this.isEnable = isEnable;
            }

            public Object getAgentnum() {
                return agentnum;
            }

            public void setAgentnum(Object agentnum) {
                this.agentnum = agentnum;
            }

            public String getRepositoryCount() {
                return repositoryCount;
            }

            public void setRepositoryCount(String repositoryCount) {
                this.repositoryCount = repositoryCount;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public int getCurrentCount() {
                return currentCount;
            }

            public void setCurrentCount(int currentCount) {
                this.currentCount = currentCount;
            }

            public Object getCreater() {
                return creater;
            }

            public void setCreater(Object creater) {
                this.creater = creater;
            }

            public String getColorCode() {
                return colorCode;
            }

            public void setColorCode(String colorCode) {
                this.colorCode = colorCode;
            }

            public String getSizeCode() {
                return sizeCode;
            }

            public void setSizeCode(String sizeCode) {
                this.sizeCode = sizeCode;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getProductNo() {
                return productNo;
            }

            public void setProductNo(String productNo) {
                this.productNo = productNo;
            }
        }
    }
}
