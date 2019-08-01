package com.kongqw.wechathelper.utils

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

object BitmapUtil {

    /**
     * Bitmap 转 ByteArray
     */
    fun bitmapToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray? {
        val output = ByteArrayOutputStream()
        return try {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
            if (needRecycle) {
                bmp.recycle()
            }
            output.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            output.close()
        }
    }
}