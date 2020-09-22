package com.fascode.gtmsample;

import android.app.Application;
import android.os.Bundle;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Date;
import java.util.Map;

class AFApp extends Application implements AppsFlyerConversionListener {
    static String AF_DEV_KEY = "SC6zv6Zb6N52vePBePs5Xo";
    @Override
    public void onCreate() {
        super.onCreate();
        initAppsFlyer();
    }

    private void initAppsFlyer() {
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, this, this);
        AppsFlyerLib.getInstance().setDebugLog(true);
        AppsFlyerLib.getInstance().startTracking(this);

    }
    public void sendInstallToFirebase(Map<String, Object> conversionData){
        Bundle bundle = new Bundle();
        bundle.putString("install_time", conversionData.get("install_time") == null ? String.valueOf(new Date().getTime()) : conversionData.get("install_time").toString());
        bundle.putString("click_time", conversionData.get("click_time") == null ? null : conversionData.get("click_time").toString());
        bundle.putString("media_source", conversionData.get("media_source") == null ? "organic": conversionData.get("media_source").toString());
        bundle.putString("campaign", conversionData.get("campaign") == null ? "organic": conversionData.get("campaign").toString());
        bundle.putString("install_type", conversionData.get("af_status") == null ? null: conversionData.get("af_status").toString());
        FirebaseAnalytics.getInstance(this).logEvent("install", bundle);
    }

    @Override
    public void onConversionDataSuccess(Map<String, Object> conversionData) {
        if(true == conversionData.get("is_first_launch")){
            sendInstallToFirebase(conversionData);
        }

    }

    @Override
    public void onConversionDataFail(String s) {

    }

    @Override
    public void onAppOpenAttribution(Map<String, String> map) {

    }

    @Override
    public void onAttributionFailure(String s) {

    }
}
