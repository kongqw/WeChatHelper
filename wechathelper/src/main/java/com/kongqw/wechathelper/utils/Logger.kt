package com.kongqw.wechathelper.utils

import android.util.Log
import com.kongqw.wechathelper.WeChatClient

internal object Logger {

    fun i(tag: String, log: String?) {
        if (WeChatClient.isLoggable) {
            Log.i(tag, log)
        }
    }
}