package com.Store.www.entity;

import java.util.List;

/**
 * @author: haifeng
 * @description: 下级扫码列表响应体
 */
public class SubordinateScanResponse extends BaseBenTwo{

    /**
     * returnValue : 1
     * data : [{"agent_name":"胡巍-测试用","creat_time":1495865469000,"order_number":"DD20170527187355","agent_code":"JW1607260168","order_id":187355,"order_type":1,"status":2,"products":[{"product_count":60,"scanning_number":1,"product_name":"呼吸款","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/呼吸款_01.jpg"}]},{"agent_name":"胡巍-测试用","creat_time":1495865772000,"order_number":"DD20170527187357","agent_code":"JW1607260168","order_id":187357,"order_type":1,"status":2,"products":[{"product_count":30,"scanning_number":1,"product_name":"KK能量裤","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20170411.png"}]},{"agent_name":"胡巍-测试用","creat_time":1495865808000,"order_number":"DD20170527187360","agent_code":"JW1607260168","order_id":187360,"order_type":1,"status":2,"products":[{"product_count":30,"scanning_number":1,"product_name":"KK能量裤","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/20170411.png"}]},{"agent_name":"胡巍-测试用","creat_time":1498029521000,"order_number":"DD20170621192801","agent_code":"18857738393","order_id":192801,"order_type":1,"status":2,"products":[{"product_count":50,"scanning_number":1,"product_name":"呼吸款","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/呼吸款_01.jpg"}]},{"agent_name":"胡巍-测试用","creat_time":1498226615000,"order_number":"DD20170623192897","agent_code":"18857738393","order_id":192897,"order_type":1,"status":2,"products":[{"product_count":83,"scanning_number":1,"product_name":"玲珑款","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/玲珑款0.jpg"}]},{"agent_name":"胡巍-测试用","creat_time":1509157665000,"order_number":"DD20171028209533","agent_code":"JW1607260168","order_id":209533,"order_type":1,"status":2,"products":[{"product_count":30,"scanning_number":1,"product_name":"订二送一福利赠品（玲珑款）","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/玲珑款6.png"}]},{"agent_name":"胡巍-测试用","creat_time":1509157744000,"order_number":"DD20171028209534","agent_code":"JW1607260168","order_id":209534,"order_type":1,"status":2,"products":[{"product_count":20,"scanning_number":1,"product_name":"订二送一福利赠品（kk先生裤）","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/KK裤1.jpg"}]},{"agent_name":"胡巍-测试用","creat_time":1524966184000,"order_number":"DD20180429231199","agent_code":"18857738393","order_id":231199,"order_type":1,"status":1,"products":[{"product_count":100,"scanning_number":1,"product_name":"金薇玲珑款胸罩（台湾）","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/玲珑款01101.jpg"},{"product_count":100,"scanning_number":1,"product_name":"金薇呼吸内裤（台湾）","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/呼吸款内裤01101.JPG"}]},{"agent_name":"胡巍-测试用","creat_time":1544424802000,"order_number":"DD20181210248439","agent_code":"18857738393","order_id":248439,"order_type":0,"status":2,"products":[{"product_count":500,"scanning_number":1,"product_name":"爱瘦","product_img":null}]}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * agent_name : 胡巍-测试用
         * creat_time : 1495865469000
         * order_number : DD20170527187355
         * agent_code : JW1607260168
         * order_id : 187355
         * order_type : 1
         * status : 2
         * products : [{"product_count":60,"scanning_number":1,"product_name":"呼吸款","product_img":"http://jwbucket.oss-cn-shanghai.aliyuncs.com/呼吸款_01.jpg"}]
         */

        private String agent_name;
        private long creat_time;
        private String order_number;
        private String agent_code;
        private int order_id;
        private int order_type;
        private int status;
        private List<ProductsBean> products;

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public long getCreat_time() {
            return creat_time;
        }

        public void setCreat_time(long creat_time) {
            this.creat_time = creat_time;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getAgent_code() {
            return agent_code;
        }

        public void setAgent_code(String agent_code) {
            this.agent_code = agent_code;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_type() {
            return order_type;
        }

        public void setOrder_type(int order_type) {
            this.order_type = order_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * product_count : 60
             * scanning_number : 1
             * product_name : 呼吸款
             * product_img : http://jwbucket.oss-cn-shanghai.aliyuncs.com/呼吸款_01.jpg
             */

            private int product_count;
            private int scanning_number;
            private String product_name;
            private String product_img;

            public int getProduct_count() {
                return product_count;
            }

            public void setProduct_count(int product_count) {
                this.product_count = product_count;
            }

            public int getScanning_number() {
                return scanning_number;
            }

            public void setScanning_number(int scanning_number) {
                this.scanning_number = scanning_number;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public String getProduct_img() {
                return product_img;
            }

            public void setProduct_img(String product_img) {
                this.product_img = product_img;
            }
        }
    }
}
