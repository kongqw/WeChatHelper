package com.kongqw.wechathelper.listener

interface IPaymentParams {

    fun onAppId(): String

    fun onPartnerId(): String

    fun onPrepayId(): String

    fun onPackageValue(): String

    fun onNonceStr(): String

    fun onTimeStamp(): String

    fun onSign(): String
}