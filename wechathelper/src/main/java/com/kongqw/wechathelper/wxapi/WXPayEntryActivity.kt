package com.kongqw.wechathelper.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.kongqw.wechathelper.WeChatBaseHelper
import com.kongqw.wechathelper.utils.Logger
import com.kongqw.wechathelper.utils.MetaUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    companion object {
        private val TAG = WXPayEntryActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WXAPIFactory.createWXAPI(this, MetaUtil.getWeChatAppId(applicationContext), true).handleIntent(intent, this)
        Logger.d("$TAG WeChatAppId = ${MetaUtil.getWeChatAppId(applicationContext)}")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("$TAG onResume")
        finish()
    }

    override fun onResp(baseResp: BaseResp?) {
        Logger.d("$TAG onResp baseResp = $baseResp")
        when (baseResp?.type) {
            // 支付
            ConstantsAPI.COMMAND_PAY_BY_WX -> {
                when (baseResp.errCode) {
                    BaseResp.ErrCode.ERR_OK -> WeChatBaseHelper.mOnWeChatPaymentListener?.onWeChatPaymentSuccess()
                    BaseResp.ErrCode.ERR_USER_CANCEL -> WeChatBaseHelper.mOnWeChatPaymentListener?.onWeChatPaymentCancel()
                    BaseResp.ErrCode.ERR_AUTH_DENIED -> WeChatBaseHelper.mOnWeChatPaymentListener?.onWeChatPaymentAuthDenied()
                    else -> {
                        // intent.action = Config.ACTION_WX_PAY_ERROR
                        // QCToast.show(applicationContext, "微信未知错误  resp?.errCode = ${resp.errCode}   resp.errStr = ${resp.errStr}")
                        val errCode = baseResp.errCode
                        val errStr = baseResp.errStr
                        Logger.d("$TAG errCode = $errCode  errStr = $errStr")

                        WeChatBaseHelper.mOnWeChatPaymentListener?.onWeChatPaymentError(errCode, errStr)
                    }
                }
                WeChatBaseHelper.mOnWeChatPaymentListener = null
            }
        }
    }

    override fun onReq(baseReq: BaseReq?) {
        Logger.d("$TAG onReq  baseReq = $baseReq")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.d("$TAG requestCode = $requestCode resultCode = $resultCode")
        super.onActivityResult(requestCode, resultCode, data)
    }
}