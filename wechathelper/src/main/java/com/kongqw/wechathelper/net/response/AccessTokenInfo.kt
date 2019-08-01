package com.kongqw.wechathelper.net.response

import java.io.Serializable

class AccessTokenInfo : WeChatBaseResponseInfo(),Serializable {

    /*
     * 调用正确返回
     ***************************************************************************/
    // 接口调用凭证
    val access_token: String? = null
    // access_token接口调用凭证超时时间，单位（秒）
    val expires_in: Long = 0
    // 用户刷新access_token
    val refresh_token: String? = null
    // 授权用户唯一标识
    val openid: String? = null
    // 用户授权的作用域，使用逗号（,）分隔
    val scope: String? = null
    // 当且仅当该移动应用已获得该用户的userinfo授权时，才会出现该字段
    val unionid: String? = null


    /**
     * 接口是否反正正确
     */
    fun isSuccess(): Boolean {
        if (!access_token.isNullOrEmpty()) {
            return true
        }
        // 错误码或错误信息不为空
        if (null != errcode || !errmsg.isNullOrEmpty()) {
            return false
        }
        return false
    }

    override fun toString(): String {
        return "AccessTokenInfo(access_token=$access_token, expires_in=$expires_in, refresh_token=$refresh_token, openid=$openid, scope=$scope, unionid=$unionid, errcode=$errcode, errmsg=$errmsg)"
    }

}
