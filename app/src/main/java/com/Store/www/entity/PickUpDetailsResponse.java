package com.Store.www.entity;

import java.util.List;

/**
 * 提货单详情响应体
 */

public class PickUpDetailsResponse extends BaseBenTwo{

    /**
     * data : {"no":"TH201712314145","agentId":252699,"address":"北京市 北京市市辖区 东城区 测试一下得到的却上班就是看看究竟是你","orderNumber":"TH201712314145","receiver":"测试","typeName":"提货","count":48,"type":1,"userId":252699,"receiveName":"测试","mobilephone":"13500000000","id":4145,"status":0,"pickBillProducts":[{"id":103801,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470A","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103802,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470B","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103803,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470C","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70C","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103804,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0475A","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"75A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103805,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0475B","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"75B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103806,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0470A","colorCode":null,"color":"冰绿","sizeCode":null,"size":"70A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103807,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0470B","colorCode":null,"color":"冰绿","sizeCode":null,"size":"70B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":12,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103808,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0475A","colorCode":null,"color":"冰绿","sizeCode":null,"size":"75A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":4,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103809,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0475B","colorCode":null,"color":"冰绿","sizeCode":null,"size":"75B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":2,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null}]}
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
         * no : TH201712314145
         * agentId : 252699
         * address : 北京市 北京市市辖区 东城区 测试一下得到的却上班就是看看究竟是你
         * orderNumber : TH201712314145
         * receiver : 测试
         * typeName : 提货
         * count : 48
         * type : 1
         * userId : 252699
         * receiveName : 测试
         * mobilephone : 13500000000
         * id : 4145
         * status : 0
         * pickBillProducts : [{"id":103801,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470A","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103802,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470B","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103803,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0470C","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"70C","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103804,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0475A","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"75A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103805,"pickBillId":4145,"productId":null,"sku":"JW-W1660P0475B","colorCode":null,"color":"蜜粉","sizeCode":null,"size":"75B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":3,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103806,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0470A","colorCode":null,"color":"冰绿","sizeCode":null,"size":"70A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":7,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103807,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0470B","colorCode":null,"color":"冰绿","sizeCode":null,"size":"70B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":12,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103808,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0475A","colorCode":null,"color":"冰绿","sizeCode":null,"size":"75A","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":4,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null},{"id":103809,"pickBillId":4145,"productId":null,"sku":"JW-W1660G0475B","colorCode":null,"color":"冰绿","sizeCode":null,"size":"75B","productName":"金薇玲珑款胸罩","productNo":"JW-W1660","count":2,"billNum":null,"removalnum":null,"price":null,"removalsum":null,"billsum":null}]
         */

        private String no;
        private int agentId;
        private String address;
        private String orderNumber;
        private String receiver;
        private String typeName;
        private int count;
        private int type;
        private int userId;
        private String receiveName;
        private String mobilephone;
        private int id;
        private int status;
        private long createTime;
        private List<PickBillProductsBean> pickBillProducts;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getMobilephone() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone = mobilephone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public List<PickBillProductsBean> getPickBillProducts() {
            return pickBillProducts;
        }

        public void setPickBillProducts(List<PickBillProductsBean> pickBillProducts) {
            this.pickBillProducts = pickBillProducts;
        }

        public static class PickBillProductsBean {
            /**
             * id : 103801
             * pickBillId : 4145
             * productId : null
             * sku : JW-W1660P0470A
             * colorCode : null
             * color : 蜜粉
             * sizeCode : null
             * size : 70A
             * productName : 金薇玲珑款胸罩
             * productNo : JW-W1660
             * count : 7
             * billNum : null
             * removalnum : null
             * price : null
             * removalsum : null
             * billsum : null
             */

            private int id;
            private int pickBillId;
            private Object productId;
            private String sku;
            private Object colorCode;
            private String color;
            private Object sizeCode;
            private String size;
            private String productName;
            private String productNo;
            private int count;
            private Object billNum;
            private Object removalnum;
            private Object price;
            private Object removalsum;
            private Object billsum;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPickBillId() {
                return pickBillId;
            }

            public void setPickBillId(int pickBillId) {
                this.pickBillId = pickBillId;
            }

            public Object getProductId() {
                return productId;
            }

            public void setProductId(Object productId) {
                this.productId = productId;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public Object getColorCode() {
                return colorCode;
            }

            public void setColorCode(Object colorCode) {
                this.colorCode = colorCode;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public Object getSizeCode() {
                return sizeCode;
            }

            public void setSizeCode(Object sizeCode) {
                this.sizeCode = sizeCode;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
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

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public Object getBillNum() {
                return billNum;
            }

            public void setBillNum(Object billNum) {
                this.billNum = billNum;
            }

            public Object getRemovalnum() {
                return removalnum;
            }

            public void setRemovalnum(Object removalnum) {
                this.removalnum = removalnum;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public Object getRemovalsum() {
                return removalsum;
            }

            public void setRemovalsum(Object removalsum) {
                this.removalsum = removalsum;
            }

            public Object getBillsum() {
                return billsum;
            }

            public void setBillsum(Object billsum) {
                this.billsum = billsum;
            }
        }
    }
}
