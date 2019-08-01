package com.kongqw.wechat

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kongqw.wechathelper.WeChatHelper
import com.kongqw.wechathelper.enums.Scene
import com.kongqw.wechathelper.listener.OnWeChatAuthLoginListener
import com.kongqw.wechathelper.listener.OnWeChatShareListener
import com.kongqw.wechathelper.net.response.WeChatUserInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX

class MainActivity : AppCompatActivity(), OnWeChatShareListener, OnWeChatAuthLoginListener {
    override fun onWeChatAuthLoginStart() {
        Toast.makeText(applicationContext, "开始申请授权登录", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatAuthLoginSuccess(weChatUserInfo: WeChatUserInfo?) {
        Toast.makeText(applicationContext, "授权登录成功 : ${weChatUserInfo?.nickname}", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatAuthLoginCancel() {
        Toast.makeText(applicationContext, "取消授权登录", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatAuthLoginAuthDenied() {
        Toast.makeText(applicationContext, "拒绝授权登录", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatAuthLoginError(errorCode: Int?, errorMessage: String?) {
        Toast.makeText(applicationContext, "授权登录异常 错误码:$errorCode,错误信息:$errorMessage", Toast.LENGTH_SHORT).show()
    }


    override fun onWeChatShareStart() {
        Toast.makeText(applicationContext, "开始分享", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatShareSuccess(resp: SendMessageToWX.Resp?) {
        Toast.makeText(applicationContext, "分享成功", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatShareCancel(resp: SendMessageToWX.Resp?) {
        Toast.makeText(applicationContext, "取消分享", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatShareAuthDenied(resp: SendMessageToWX.Resp?) {
        Toast.makeText(applicationContext, "授权拒绝", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatShareSentFailed(resp: SendMessageToWX.Resp?) {
        Toast.makeText(applicationContext, "分享失败", Toast.LENGTH_SHORT).show()
    }

    override fun onWeChatShareError(resp: SendMessageToWX.Resp?) {
        Toast.makeText(applicationContext, "分享异常", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 分享文字到朋友圈
     */
    fun onShareTextToTimeLine(view: View) {
        WeChatHelper.getInstance(applicationContext).shareText("我分享了文字到朋友圈", Scene.Timeline, this)
    }

    /**
     * 分享文字到收藏夹
     */
    fun onShareTextToFavorite(view: View) {
        WeChatHelper.getInstance(applicationContext).shareText("我分享了文字到收藏夹", Scene.Favorite, this)
    }

    /**
     * 分享文字给联系人
     */
    fun onShareTextToSession(view: View) {
        WeChatHelper.getInstance(applicationContext).shareText("我分享了文字给好友", Scene.Session, this)
    }

    /**
     * 分享图片到朋友圈
     */
    fun onShareImageToTimeLine(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareImage(bmp, Scene.Timeline, this)
    }

    /**
     * 分享图片到收藏夹
     */
    fun onShareImageToFavorite(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareImage(bmp, Scene.Favorite, this)
    }

    /**
     * 分享图片给联系人
     */
    fun onShareImageToSession(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareImage(bmp, Scene.Session, this)
    }

    /**
     * 分享音乐到朋友圈
     */
    fun onShareMusicToTimeLine(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareMusic(
            bmp,
            Scene.Timeline,
            "http://m10.music.126.net/20190402151400/39f1d995f4b2d48efa312d1ecb71550f/ymusic/363b/72ef/7661/0b373b6cdfc54e3022ef436c3ad58ec3.mp3",
            "音乐名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享音乐到收藏夹
     */
    fun onShareMusicToFavorite(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareMusic(
            bmp,
            Scene.Favorite,
            "http://m10.music.126.net/20190402151400/39f1d995f4b2d48efa312d1ecb71550f/ymusic/363b/72ef/7661/0b373b6cdfc54e3022ef436c3ad58ec3.mp3",
            "音乐名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享音乐给联系人
     */
    fun onShareMusicToSession(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareMusic(
            bmp,
            Scene.Session,
            "http://m10.music.126.net/20190402151400/39f1d995f4b2d48efa312d1ecb71550f/ymusic/363b/72ef/7661/0b373b6cdfc54e3022ef436c3ad58ec3.mp3",
            "音乐名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享视频到朋友圈
     */
    fun onShareVideoToTimeLine(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareVideo(
            bmp,
            Scene.Timeline,
            "https://github.com/kongqw/OpenCVForAndroid/blob/opencv3.2.0/mp4/ObjectDetecting.mp4",
            "视频名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享视频到收藏夹
     */
    fun onShareVideoToFavorite(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareVideo(
            bmp,
            Scene.Favorite,
            "https://github.com/kongqw/OpenCVForAndroid/blob/opencv3.2.0/mp4/ObjectDetecting.mp4",
            "视频名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享视频给联系人
     */
    fun onShareVideoToSession(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareVideo(
            bmp,
            Scene.Session,
            "https://github.com/kongqw/OpenCVForAndroid/blob/opencv3.2.0/mp4/ObjectDetecting.mp4",
            "视频名称",
            "分享到朋友圈",
            this
        )
    }

    /**
     * 分享网页到朋友圈
     */
    fun onShareWebPageToTimeLine(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareWebPage(
            bmp,
            Scene.Timeline,
            "https://github.com/kongqw",
            "kongqw",
            "kongqw的GitHub",
            this
        )
    }

    /**
     * 分享网页到收藏夹
     */
    fun onShareWebPageToFavorite(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareWebPage(
            bmp,
            Scene.Favorite,
            "https://github.com/kongqw",
            "kongqw",
            "kongqw的GitHub",
            this
        )
    }

    /**
     * 分享网页给联系人
     */
    fun onShareWebPageToSession(view: View) {
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.share)
        WeChatHelper.getInstance(applicationContext).shareWebPage(
            bmp,
            Scene.Session,
            "https://github.com/kongqw",
            "kongqw",
            "kongqw的GitHub",
            this
        )
    }

    /**
     * 授权登录
     */
    fun onAuthLogin(view: View) {
        WeChatHelper.getInstance(applicationContext).authLogin(this)
    }
}
