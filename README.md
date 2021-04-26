# 微信开放平台封装

[![](https://jitpack.io/v/kongqw/WeChatHelper.svg)](https://jitpack.io/#kongqw/WeChatHelper)

本库在授权登录部分使用了`Retrofit`和`RxKotlin`

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

``` gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

``` gradle
dependencies {
        implementation 'com.github.kongqw:WeChatHelper:1.1.2'
}
```

Step 3. Add `WECHAT_APP_ID` in app build.gradle

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
WeChatHelper.getInstance(applicationContext).shareText(`分享的文字`, `Scene`, `OnWeChatShareListener`)
```

### 分享图片

``` kotlin
WeChatHelper.getInstance(applicationContext).shareImage(`Bitmap`, `Scene`, `OnWeChatShareListener`)
```

### 分享音乐

``` kotlin
WeChatHelper.getInstance(applicationContext).shareMusic(`Bitmap`, `Scene`, `音乐链接`, `分享音乐名称`, `分享音乐描述`, `OnWeChatShareListener`)
```

### 分享视频

``` kotlin
WeChatHelper.getInstance(applicationContext).shareVideo(`Bitmap`, `Scene`, `视频链接`, `分享视频名称`, `分享视频描述`, `OnWeChatShareListener`)
```

### 分享网址

``` kotlin
WeChatHelper.getInstance(applicationContext).shareWebPage(`Bitmap`, `Scene`, `网址链接`, `分享网址名称`, `分享网址描述`, `OnWeChatShareListener`)
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
WeChatHelper.getInstance(applicationContext).authLogin(`OnWeChatAuthLoginListener`)
```


## 支付

``` kotlin
WeChatHelper.getInstance(applicationContext).payment(`IPaymentParams` ,`OnWeChatPaymentListener`)
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