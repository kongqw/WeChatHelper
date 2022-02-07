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
        Logger.i(TAG, "onCreate WeChatAppId = ${MetaUtil.getWeChatAppId(applicationContext)}")
    }

    override fun onResume() {
        super.onResume()
        Logger.i(TAG, "onResume")
        finish()
    }

    override fun onResp(baseResp: BaseResp?) {
        Logger.i(TAG, "onResp($baseResp)")
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
                        Logger.i(TAG, "errCode = $errCode  errStr = $errStr")

                        WeChatBaseHelper.mOnWeChatPaymentListener?.onWeChatPaymentError(errCode, errStr)
                    }
                }
                WeChatBaseHelper.mOnWeChatPaymentListener = null
            }
        }
    }

    override fun onReq(baseReq: BaseReq?) {
        Logger.i(TAG, "onReq($baseReq)")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.i(TAG, "onActivityResult($requestCode, $resultCode, $data)")
        super.onActivityResult(requestCode, resultCode, data)
    }
}