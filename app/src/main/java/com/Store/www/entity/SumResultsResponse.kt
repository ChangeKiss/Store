package com.Store.www.entity

/**
 * 总业绩响应体
 */

class SumResultsResponse {


    /**
     * returnValue : 1
     * data : [{"allMoney":999,"unauditedMoney":999,"auditedMoney":999,"allCount":999,"unauditedCount":999,"auditedCount":999,"month":9}]
     * errMsg : 错误信息, 字符串
     */

    var returnValue: Int = 0
    var errMsg: String? = null
    var data: List<DataBean>? = null

    class DataBean {
        /**
         * allMoney : 999
         * unauditedMoney : 999
         * auditedMoney : 999
         * allCount : 999
         * unauditedCount : 999
         * auditedCount : 999
         * year:2018
         * month : 9
         * "time":"2018年3月份",
         */

        var allMoney: Int = 0
        var unauditedMoney: Int = 0
        var auditedMoney: Int = 0
        var allCount: Int = 0
        var unauditedCount: Int = 0
        var auditedCount: Int = 0
        var year: Int = 0
        var month: String? = null
        var time: String? = null
    }
}
