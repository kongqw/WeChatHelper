package com.kongqw.wechathelper.listener

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX

interface OnWeChatShareListener {

    /**
     * 开始微信分享
     */
    fun onWeChatShareStart()

    /**
     * 微信分享成功
     * Note：测试，如果分享成功后选择了留在微信，则不会有成功回调
     */
    fun onWeChatShareSuccess(resp: SendMessageToWX.Resp?)

    /**
     * 用户取消分享
     * Note：这里微信可能存在一个问题，即便用户取消的分享，也会返回 BaseResp.ErrCode.ERR_OK
     */
    fun onWeChatShareCancel(resp: SendMessageToWX.Resp?)

    /**
     * 分享被拒绝
     * 检查包名或签名与注册信息是否相符
     */
    fun onWeChatShareAuthDenied(resp: SendMessageToWX.Resp?)

    /**
     * 发送分享失败
     */
    fun onWeChatShareSentFailed(resp: SendMessageToWX.Resp?)

    /**
     * 其他分享错误
     */
    fun onWeChatShareError(resp: SendMessageToWX.Resp?)
}