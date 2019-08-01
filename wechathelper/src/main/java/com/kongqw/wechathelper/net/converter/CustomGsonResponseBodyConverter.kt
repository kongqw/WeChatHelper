package com.kongqw.wechathelper.net.converter

import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import com.google.gson.Gson
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

class CustomGsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string().trim()
        // response = response.replace("null", "\"\"", true)


        // Log.d(TAG, "response = $response")
//        gson.fromJson(response, BaseResponse::class.java)?.apply {
//            // 验证返回的结果是否正常
//            when {
//                isInvalidSession() -> EventBus.getDefault().post(this)
//            }
//        }


        //继续处理body数据反序列化，注意value.string() 不可重复使用
        val contentType = value.contentType()
        val charset = if (contentType != null) contentType.charset(Charsets.UTF_8) else Charsets.UTF_8
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset!!)
        val jsonReader = gson.newJsonReader(reader)

        try {
            return adapter.read(jsonReader)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            value.close()
        }

        return null
    }
}