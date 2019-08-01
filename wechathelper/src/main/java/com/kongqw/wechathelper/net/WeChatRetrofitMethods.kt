package com.kongqw.wechathelper.net

import com.kongqw.wechathelper.net.response.AccessTokenInfo
import com.kongqw.wechathelper.net.response.WeChatUserInfo
import io.reactivex.Observable
import retrofit2.http.*


interface WeChatRetrofitMethods {

    /**
     * 通过code获取access_token
     */
    @GET("sns/oauth2/access_token")
    fun getAccessToken(@QueryMap param: HashMap<String, String>): Observable<AccessTokenInfo>

    /**
     * 获取用户信息
     */
    @GET("sns/userinfo")
    fun getWeChatUserInfo(@QueryMap param: HashMap<String, String>): Observable<WeChatUserInfo>


}