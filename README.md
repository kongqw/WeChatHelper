# 微信开放平台封装

本库在授权登录部分使用了 Retrofit + RxKotlin

## Gradle 配置

``` gradle
android {
    ……
    defaultConfig {
        ……
        manifestPlaceholders = [
                WECHAT_APP_ID: "申请的微信appid"
        ]
        ……
    }
    ……
}
```

## 初始化

``` kotlin
WeChatHelper.getInstance(applicationContext).init(BuildConfig.DEBUG)
```

## 分享

| Scene | |
| --- | --- |
| Scene.Timeline | 朋友圈 |
| Scene.Favorite | 收藏夹 |
| Scene.Session | 联系人 |

### 分享文字

``` kotlin
WeChatHelper.getInstance(applicationContext).shareText(`分享的文字`, Scene, `监听接口`)
```

### 分享图片

``` kotlin
WeChatHelper.getInstance(applicationContext).shareImage(`Bitmap`, Scene, `监听接口`)
```

### 分享音乐

``` kotlin
WeChatHelper.getInstance(applicationContext).shareMusic(`Bitmap`, Scene, `音乐链接`, `分享音乐名称`, `分享音乐描述`, `监听接口`)
```

### 分享视频

``` kotlin
WeChatHelper.getInstance(applicationContext).shareVideo(`Bitmap`, Scene, `视频链接`, `分享视频名称`, `分享视频描述`, `监听接口`)
```

### 分享网址

``` kotlin
WeChatHelper.getInstance(applicationContext).shareWebPage(`Bitmap`, Scene, `网址链接`, `分享网址名称`, `分享网址描述`, `监听接口`)
```

### 监听接口

``` kotlin
interface OnWeChatShareListener {
    fun onWeChatShareStart()
    fun onWeChatShareSuccess(resp: SendMessageToWX.Resp?)
    fun onWeChatShareCancel(resp: SendMessageToWX.Resp?)
    fun onWeChatShareAuthDenied(resp: SendMessageToWX.Resp?)
    fun onWeChatShareSentFailed(resp: SendMessageToWX.Resp?)
    fun onWeChatShareError(resp: SendMessageToWX.Resp?)
}
```

## 授权登录

再清单文件配置 app secret

``` xml
<meta-data
    android:name="wechat_app_secret"
    android:value="申请的app_secret" />
```

### 申请授权登录

``` kotlin
WeChatHelper.getInstance(applicationContext).authLogin(`监听接口`)
```

### 监听接口

``` kotlin
interface OnWeChatAuthLoginListener {
    fun onWeChatAuthLoginStart()
    fun onWeChatAuthLoginSuccess(weChatUserInfo: WeChatUserInfo?)
    fun onWeChatAuthLoginCancel()
    fun onWeChatAuthLoginAuthDenied()
    fun onWeChatAuthLoginError(errorCode: Int?, errorMessage: String?)
}
```

## 混淆

```
-keep class com.tencent.mm.opensdk.** {
*;
}
-keep class com.tencent.wxop.** {
*;
}
-keep class com.tencent.mm.sdk.** {
*;
}
```