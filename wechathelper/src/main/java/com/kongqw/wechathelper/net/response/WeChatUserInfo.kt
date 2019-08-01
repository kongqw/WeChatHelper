package com.kongqw.wechathelper.net.response

import java.io.Serializable

class WeChatUserInfo : WeChatBaseResponseInfo(), Serializable {
    // {"openid":"ovLL954dzynO6JVas2JWedco3gks",
    // "nickname":"明天再见",
    // "sex":0,
    // "language":"zh_CN",
    // "city":"",
    // "province":"",
    // "country":"",
    // "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/bWoSTkVovOpAdK4CB3x99SzbXU9AJ0IqZtUlTc7xjBAX9SevDBiaDM9e16fOJfgfpr2s1rUibwQMT647H8HcGzqg\/132",
    // "privilege":[],
    // "unionid":"oFYlK1Be__U2HqaLQ47LjxpE9I2k"}

    val openid: String? = null
    val nickname: String? = null
    val sex: String? = null
    val language: String? = null
    val city: String? = null
    val province: String? = null
    val country: String? = null
    val headimgurl: String? = null
    val privilege: Any? = null
    val unionid: String? = null

    /**
     * 接口是否反正正确
     */
    fun isSuccess(): Boolean {

        // 错误码或错误信息不为空
        if (null != errcode || !errmsg.isNullOrEmpty()) {
            return false
        }
        return true
    }

    override fun toString(): String {
        return "WeChatUserInfo(openid=$openid, nickname=$nickname, sex=$sex, language=$language, city=$city, province=$province, country=$country, headimgurl=$headimgurl, privilege=$privilege, unionid=$unionid)"
    }

}
