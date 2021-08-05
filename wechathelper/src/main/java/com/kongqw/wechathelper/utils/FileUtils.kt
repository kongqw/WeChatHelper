package com.kongqw.wechathelper.utils

import android.content.Context
import android.graphics.Bitmap
import com.kongqw.wechathelper.net.WeChatHelperRetrofitManager
import com.kongqw.wechathelper.net.WeChatRetrofitMethods
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider


object FileUtils {

    /**
     * 将图片保存到沙盒
     */
    fun saveBitmap(context: Context, bitmap: Bitmap, callback: ((isSuccess: Boolean, file: File?) -> Unit)) {

        Observable.just("")
            .subscribeOn(Schedulers.io())
            .map {
                val fileName = String.format("img_%s.jpg", System.currentTimeMillis())
                val screenshotsDir = File(context.getExternalFilesDir(null), "shareData")
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdirs()
                } else {
                    // 删除所有文件
                    screenshotsDir.listFiles().forEach { file ->
                        if (file.isFile) {
                            file.delete()
                        }
                    }
                }
                val screenshotFile = File(screenshotsDir, fileName)
                val fileOutputStream = FileOutputStream(screenshotFile)
                val outputStream = BufferedOutputStream(fileOutputStream)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.flush()
                outputStream.close()
                fileOutputStream.close()
                return@map screenshotFile
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    callback(true, it)
                },
                onComplete = {},
                onError = {
                    it.printStackTrace()
                    callback(true, null)
                }
            )
    }

    fun getFileUri(context: Context, file: File?): String? {
        if (file == null || !file.exists()) {
            return null
        }
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".wechatShare",  // 要与`AndroidManifest.xml`里配置的`authorities`一致，假设你的应用包名为com.example.app
            file
        )

        // 授权给微信访问路径
        context.grantUriPermission(
            "com.tencent.mm",  // 这里填微信包名
            contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        return contentUri.toString() // contentUri.toString() 即是以"content://"开头的用于共享的路径
    }
}