package com.kongqw.wechathelper.utils

import android.content.Context
import android.content.pm.PackageManager

object MetaUtil {

    /**
     * 读取AppMetaData信息
     */
    private fun getAppMetaData(context: Context, key: String): String? {
        if (key.isEmpty()) {
            return null
        }
        return try {
            context.packageManager?.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                ?.metaData?.get(key)?.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取 WeChat APP ID
     */
    fun getWeChatAppId(context: Context): String? {
        return getAppMetaData(context, "wechat_app_id")
    }

    /**
     * 获取 WeChat Secret
     */
    fun getWeChatSecret(context: Context): String? {
        return getAppMetaData(context, "wechat_app_secret")
    }
}