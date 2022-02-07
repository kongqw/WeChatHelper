package com.kongqw.wechathelper

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.Nullable
import com.kongqw.wechathelper.enums.Scene
import com.kongqw.wechathelper.listener.IPaymentParams
import com.kongqw.wechathelper.listener.OnWeChatAuthLoginListener
import com.kongqw.wechathelper.listener.OnWeChatPaymentListener
import com.kongqw.wechathelper.listener.OnWeChatShareListener
import com.kongqw.wechathelper.utils.AppUtils

object WeChatClient {

    lateinit var applicationContext: Context

    var isLoggable = false

    var isRegistered = false

    fun init(context: Context, isLoggable: Boolean): Boolean {
        this.applicationContext = context
        this.isLoggable = isLoggable
        return true
    }

    /**
     * 分享文字内容
     */
    fun shareText(content: String, scene: Scene, listener: OnWeChatShareListener): Boolean {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return false
        }
        return WeChatBaseHelper(applicationContext).shareText(content, scene, listener)
    }

    /**
     * 分享图片
     */
    fun shareImage(
        bmp: Bitmap,
        scene: Scene,
        listener: OnWeChatShareListener,
        @Nullable thumbWidth: Int = WeChatBaseHelper.THUMB_SIZE,
        @Nullable thumbHeight: Int = WeChatBaseHelper.THUMB_SIZE
    ): Boolean {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return false
        }
        return WeChatBaseHelper(applicationContext).shareImage(bmp, scene, listener, thumbWidth, thumbHeight)
    }

    /**
     * 分享音乐
     */
    fun shareMusic(
        bitmap: Bitmap,
        scene: Scene,
        musicUrl: String,
        title: String,
        description: String,
        listener: OnWeChatShareListener,
        @Nullable thumbWidth: Int = WeChatBaseHelper.THUMB_SIZE,
        @Nullable thumbHeight: Int = WeChatBaseHelper.THUMB_SIZE
    ): Boolean {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return false
        }
        return WeChatBaseHelper(applicationContext).shareMusic(bitmap, scene, musicUrl, title, description, listener, thumbWidth, thumbHeight)
    }

    /**
     * 分享视频
     */
    fun shareVideo(
        bitmap: Bitmap,
        scene: Scene,
        videoUrl: String,
        title: String,
        description: String,
        listener: OnWeChatShareListener,
        @Nullable thumbWidth: Int = WeChatBaseHelper.THUMB_SIZE,
        @Nullable thumbHeight: Int = WeChatBaseHelper.THUMB_SIZE
    ): Boolean {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return false
        }
        return WeChatBaseHelper(applicationContext).shareVideo(bitmap, scene, videoUrl, title, description, listener, thumbWidth, thumbHeight)
    }

    /**
     * 网页分享
     */
    fun shareWebPage(
        bitmap: Bitmap,
        scene: Scene,
        webPageUrl: String,
        title: String,
        description: String,
        listener: OnWeChatShareListener,
        @Nullable thumbWidth: Int = WeChatBaseHelper.THUMB_SIZE,
        @Nullable thumbHeight: Int = WeChatBaseHelper.THUMB_SIZE
    ): Boolean {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return false
        }
        return WeChatBaseHelper(applicationContext).shareWebPage(bitmap, scene, webPageUrl, title, description, listener, thumbWidth, thumbHeight)
    }


    /**
     * 授权登录
     */
    fun authLogin(listener: OnWeChatAuthLoginListener) {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return
        }
        WeChatBaseHelper(applicationContext).authLogin(listener)
    }


    /**
     * 微信支付
     */
    fun payment(params: IPaymentParams, listener: OnWeChatPaymentListener) {
        if(!AppUtils.isWeChatInstalled(applicationContext)){
            listener.onNotInstall()
            return
        }
        WeChatBaseHelper(applicationContext).payment(params, listener)
    }
}