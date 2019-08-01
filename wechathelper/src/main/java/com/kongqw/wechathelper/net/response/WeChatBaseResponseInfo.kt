package com.kongqw.wechathelper.net.response

import java.io.Serializable

open class WeChatBaseResponseInfo : Serializable {
    /*
     * 调用错误返回
     ***************************************************************************/
    // 错误码
    val errcode: Int? = null
    // 错误信息
    val errmsg: String? = null
}
