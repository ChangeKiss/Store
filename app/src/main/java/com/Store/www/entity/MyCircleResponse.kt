package com.Store.www.entity

/**
 *@author: haifeng
 *@description: 我的圈子响应体
 */
 class MyCircleResponse{
    val `data`: List<Data>? = null
    val isLastPage: Int = 0
    val returnValue: Int = 0

    class Data {
        val circleId: Int = 0
        val commentCount: Int = 0
        val content: String? = null
        val images: List<Image>? = null
        var isLike: Int = 0
        val isOwn: Int = 0
        var likeCount: Int = 0
        val readNum: Int = 0
        val time: Long = 0
        val type: Int = 0
        val userInfo: UserInfo? = null

        class UserInfo {
            val headPicture: Any? = null
            val nickName: String ? = null
            val userId: Int = 0
        }

        class Image{
            val url: String ? = null
        }
    }
}







