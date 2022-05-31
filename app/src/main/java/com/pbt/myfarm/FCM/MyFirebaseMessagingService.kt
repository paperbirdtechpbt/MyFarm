package com.pbt.myfarm.FCM

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_PAYLOAD
import org.json.JSONObject

class MyFirebaseMessagingService:FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val obj = JSONObject(message.data.toString())
        obj.let {
            if (obj.getJSONObject("data").has(CONST_PAYLOAD)) {
                  showNotification()
            }
        }


    }

    private fun showNotification() {

    }

    override fun onNewToken(token: String) {

    }
}