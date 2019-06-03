package com.Store.www.entity;

import java.util.List;

/**
 * 商品库存请求体
 */

public class CommodityStocksResponse extends BaseBean{


    /**
     * data : [{"sum":26,"no":"JW-W1601","productId":1,"count":29,"name":"金薇呼吸款胸罩"},{"sum":83,"no":"JW-W1660","productId":61,"count":17,"name":"金薇玲珑款胸罩"}]
     * repositoryId : 451
     * errMsg : 成功
     * type : 1
     */

    private int repositoryId;
    private String errMsg;
    private int type;
    private List<DataBean> data;

    public int getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sum : 26
         * no : JW-W1601
         * productId : 1
         * count : 29
         * name : 金薇呼吸款胸罩
         * image
         */

        private int sum;
        private String no;
        private int productId;
        private int count;
        private int mCount;
        private String name;
        private String image;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getmCount() {
            return mCount;
        }

        public void setmCount(int mCount) {
            this.mCount = mCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
