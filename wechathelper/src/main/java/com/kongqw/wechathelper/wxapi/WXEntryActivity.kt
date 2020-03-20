package com.kongqw.wechathelper.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kongqw.wechathelper.WeChatBaseHelper
import com.kongqw.wechathelper.net.WeChatHelperRetrofitManager
import com.kongqw.wechathelper.net.response.AccessTokenInfo
import com.kongqw.wechathelper.utils.Logger
import com.kongqw.wechathelper.utils.MetaUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.rxkotlin.subscribeBy

class WXEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WXAPIFactory.createWXAPI(this, MetaUtil.getWeChatAppId(applicationContext), true).handleIntent(intent, this)
        Logger.d("WXEntryActivity onCreate  WeChatAppId = ${MetaUtil.getWeChatAppId(applicationContext)}")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("WXEntryActivity onCreate  onResume")
        finish()
    }

    override fun onResp(baseResp: BaseResp?) {
        Logger.d("WXEntryActivity onResp  baseResp = $baseResp")

        when (baseResp?.type) {
            // 授权登录
            ConstantsAPI.COMMAND_SENDAUTH -> {
                when (baseResp.errCode) {
                    // 用户同意了授权
                    BaseResp.ErrCode.ERR_OK -> {
                        val code = (baseResp as? SendAuth.Resp)?.code
                        var accessToken: AccessTokenInfo? = null
                        // 请求 Access Token
                        WeChatHelperRetrofitManager.getAccessToken(MetaUtil.getWeChatAppId(applicationContext), MetaUtil.getWeChatSecret(applicationContext), code)
                            .filter { accessTokenInfo ->
                                accessToken = accessTokenInfo
                                val isSuccess = accessTokenInfo.isSuccess()
                                if (!isSuccess) {
                                    // 请求AccessToken失败
                                    WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginError(accessTokenInfo.errcode, accessTokenInfo.errmsg)
                                    WeChatBaseHelper.mOnWeChatAuthLoginListener = null
                                }
                                return@filter isSuccess
                            }
                            // 请求用户信息
                            .flatMap { accessTokenInfo -> WeChatHelperRetrofitManager.getWeChatUserInfo(accessTokenInfo.access_token, accessTokenInfo.openid) }
                            .subscribeBy(
                                onNext = { weChatUserInfo ->
                                    if (weChatUserInfo.isSuccess()) {
                                        WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginSuccess(accessToken, weChatUserInfo)
                                    } else {
                                        WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginError(weChatUserInfo.errcode, weChatUserInfo.errmsg)
                                    }
                                },
                                onComplete = { WeChatBaseHelper.mOnWeChatAuthLoginListener = null },
                                onError = {
                                    WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginError(null, it.message)
                                    WeChatBaseHelper.mOnWeChatAuthLoginListener = null
                                    it.printStackTrace()
                                }
                            )
                    }
                    // 用户取消的授权登录
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginCancel()
                        WeChatBaseHelper.mOnWeChatAuthLoginListener = null
                    }
                    // 用户拒绝授权
                    BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                        WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginAuthDenied()
                        WeChatBaseHelper.mOnWeChatAuthLoginListener = null
                    }
                    else -> {
                        WeChatBaseHelper.mOnWeChatAuthLoginListener?.onWeChatAuthLoginError(null, null)
                        WeChatBaseHelper.mOnWeChatAuthLoginListener = null
                    }
                }
            }
            // 分享
            ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                val resp: SendMessageToWX.Resp? = baseResp as? SendMessageToWX.Resp?
                when (resp?.errCode) {
                    // 用户同意
                    BaseResp.ErrCode.ERR_OK -> WeChatBaseHelper.mOnWeChatShareListener?.onWeChatShareSuccess(resp)
                    // 用户拒绝授权
                    BaseResp.ErrCode.ERR_AUTH_DENIED -> WeChatBaseHelper.mOnWeChatShareListener?.onWeChatShareAuthDenied(resp)
                    // 用户取消
                    BaseResp.ErrCode.ERR_USER_CANCEL -> WeChatBaseHelper.mOnWeChatShareListener?.onWeChatShareCancel(resp)
                    // 分享发送失败
                    BaseResp.ErrCode.ERR_SENT_FAILED -> WeChatBaseHelper.mOnWeChatShareListener?.onWeChatShareSentFailed(resp)
                    // 其他错误
                    else -> WeChatBaseHelper.mOnWeChatShareListener?.onWeChatShareError(resp)
                }
                WeChatBaseHelper.mOnWeChatShareListener = null
            }
        }

//        val type1 = resp?.type
//        val code = resp?.code
//        val state = resp?.state
//        val lang = resp?.lang
//        val errCode1 = resp?.errCode
//        val errStr1 = resp?.errStr
//
//        Log.i("WXEntryActivity", "onResp ------------------------------------")
//        Log.i("WXEntryActivity", "onResp | type1 = $type1")
//        Log.i("WXEntryActivity", "onResp | errCode1 = $errCode1")
//        Log.i("WXEntryActivity", "onResp | errStr1 = $errStr1")
//        Log.i("WXEntryActivity", "onResp | code = $code")
//        Log.i("WXEntryActivity", "onResp | state = $state")
//        Log.i("WXEntryActivity", "onResp | lang = $lang")
//        Log.i("WXEntryActivity", "onResp ------------------------------------")
    }

    override fun onReq(baseReq: BaseReq?) {
        Logger.d("WXEntryActivity onReq  baseReq = $baseReq")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.d("WXEntryActivity requestCode = $requestCode  resultCode = $resultCode")
        super.onActivityResult(requestCode, resultCode, data)
    }
}