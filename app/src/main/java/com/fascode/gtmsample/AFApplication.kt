package com.fascode.gtmsample

import android.app.Application
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*


val AF_DEV_KEY = "SC6zv6Zb6N52vePBePs5Xo"
class AFApplication: Application(), AppsFlyerConversionListener {
    companion object {
        fun getAfDevKey(): String? {
            return AF_DEV_KEY
        }
    }
    private val tag = "AFApplication"
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate() {
        super.onCreate()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        initAppsFlyer()
    }
    override fun onConversionDataSuccess(conversionData: MutableMap<String, Any>?) {
        Log.i(tag, "[onConversionDataSuccess] \n$conversionData")
        if(conversionData?.get("is_first_launch") == true){
            sendInstallToFirebase(conversionData);
        }

    }

    override fun onConversionDataFail(p0: String?) {
        Log.e(tag, "[onConversionDataFail] $p0")
    }

    override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
        Log.i(tag, "[onAppOpenAttribution] \n$p0")
    }

    override fun onAttributionFailure(p0: String?) {
        Log.e(tag, "[onAttributionFailure] $p0")
    }

    private fun initAppsFlyer() {
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, this, this)
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().startTracking(this)
    }

    fun sendInstallToFirebase(conversionData: Map<String, Any>) {
        val bundle = Bundle()
        bundle.putString("install_time", conversionData["install_time"]?.toString() ?: Date().time.toString())
        bundle.putString("click_time", conversionData["click_time"]?.toString())
        bundle.putString("media_source", conversionData["media_source"]?.toString() ?: "organic")
        bundle.putString("campaign", conversionData["campaign"]?.toString() ?: "organic")
        bundle.putString("install_type", conversionData["af_status"]?.toString())
        FirebaseAnalytics.getInstance(this).logEvent("install", bundle)
    }

}