package com.fascode.gtmsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var appsFlyerID: String? = null
    var devKey: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appsFlyerID = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        devKey = AFApplication.getAfDevKey()
        btnPurchase.setOnClickListener {
            sendPurchaseEvent()
        }
    }

    private fun sendPurchaseEvent() {
        val bundle = Bundle()
        // notice "af_id", this is the name of the event parameter
        // for AppsFlyer ID that we created in the previous step
        bundle.putString("af_id", appsFlyerID)
        // notice "dev_key", this is the name of the event parameter
        // for AppsFlyer Dev Key that we created in the previous step
        bundle.putString("dev_key", devKey)
        bundle.putString("af_revenue", "200")
        bundle.putString("af_price", "250")
        FirebaseAnalytics.getInstance(this).logEvent("af_purchase", bundle)
    }
}