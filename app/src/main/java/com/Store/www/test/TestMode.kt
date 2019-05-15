package com.Store.www.test

/**
 *@author: haifeng
 *@description:
 */
class TestMode {
    private var Bean: List<DateBean>? = null


    fun getBean(): List<DateBean>? {
        return Bean
    }

    fun setBean(bean: List<DateBean>) {
        Bean = bean
    }

    class DateBean {
        var money: String? = null
        var height: Int = 0
        var url: String? = null
        var name: String? = null
        var time: String? = null
    }
}