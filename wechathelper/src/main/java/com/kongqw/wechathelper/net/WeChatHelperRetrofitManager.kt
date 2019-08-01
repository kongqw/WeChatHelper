package com.kongqw.wechathelper.net

import com.kongqw.wechathelper.net.converter.CustomGsonConverterFactory
import com.kongqw.wechathelper.net.response.AccessTokenInfo
import com.kongqw.wechathelper.net.response.WeChatUserInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

object WeChatHelperRetrofitManager {

    @Volatile
    private var instance: Retrofit? = null

    @JvmStatic
    fun getRetrofitInstance() = instance ?: synchronized(this) {
        instance ?: createRetrofit().also { instance = it }
    }

    private fun createRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        // debug模式添加log信息拦截
        // val interceptor = HttpLoggingInterceptor()
        // interceptor.level = HttpLoggingInterceptor.Level.BODY
        // okHttpBuilder.addInterceptor(interceptor)
        // okHttpBuilder.addInterceptor(HeaderInterceptor(context))
        // okHttpBuilder.addInterceptor(ParamsInterceptor())
        //设置网络连接失败时自动重试
        okHttpBuilder.retryOnConnectionFailure(true)
        //设置连接超时
        okHttpBuilder.connectTimeout(WeChatHelperNetworkConfig.TIME_OUT_CONNECT, TimeUnit.SECONDS)
        //设置写超时
        okHttpBuilder.writeTimeout(WeChatHelperNetworkConfig.TIME_OUT_WRITE, TimeUnit.SECONDS)
        //设置读超时
        okHttpBuilder.readTimeout(WeChatHelperNetworkConfig.TIME_OUT_READ, TimeUnit.SECONDS)

        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(WeChatHelperNetworkConfig.WECHAT_API_HOST)
        retrofitBuilder.client(okHttpBuilder.build())
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        retrofitBuilder.addConverterFactory(CustomGsonConverterFactory.create())
        return retrofitBuilder.build()
    }

    /**
     * 通过code获取access_token
     */
    fun getAccessToken(appId: String?, secret: String?, code: String?): Observable<AccessTokenInfo> {
        val params = HashMap<String, String>()
        params["appid"] = appId ?: ""
        params["secret"] = secret ?: ""
        params["code"] = code ?: ""
        params["grant_type"] = "authorization_code"
        return Observable.just(params)
            .subscribeOn(Schedulers.io())
            .flatMap { getRetrofitInstance().create(WeChatRetrofitMethods::class.java).getAccessToken(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }


    /**
     * 通过access_token获取用户信息
     */
    fun getWeChatUserInfo(accessToken: String?, openId: String?): Observable<WeChatUserInfo> {
        val params = HashMap<String, String>()
        params["access_token"] = accessToken ?: ""
        params["openid"] = openId ?: ""
        return Observable.just(params)
            .subscribeOn(Schedulers.io())
            .flatMap { getRetrofitInstance().create(WeChatRetrofitMethods::class.java).getWeChatUserInfo(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }

}