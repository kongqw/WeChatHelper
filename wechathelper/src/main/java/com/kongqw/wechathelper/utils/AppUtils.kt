package com.kongqw.wechathelper.utils

import android.annotation.SuppressLint
import android.content.Context

internal object AppUtils {

    /**
     * 是否安装了微信APP
     */
    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun isWeChatInstalled(context: Context): Boolean {
        try {
            context.applicationContext.packageManager.getInstalledPackages(0).forEach { packageInfo ->
                if ("com.tencent.mm" == packageInfo.packageName) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}

