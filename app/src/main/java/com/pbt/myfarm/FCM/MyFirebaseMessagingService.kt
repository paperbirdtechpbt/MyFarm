package com.pbt.myfarm.FCM


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.pbt.myfarm.R
import com.pbt.myfarm.Util.AppConstant.Companion.CONST_TOKEN
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.Util.MySharedPreference

private const val CHANNEL_ID = "my_channel"


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {

        message.let {
            AppUtils.logDebug("##MyFirebaseMessagingService", Gson().toJson(it.data))

            try {
                val title = it.data.get("title")
                val notificationmsg = it.data.get("message")
                val target_user = it.data.get("target_user")
                if (target_user == MySharedPreference.getUser(this)?.id.toString()) {
                    popUpNotificaiton(title, notificationmsg)
                }

            } catch (e: Exception) {
                AppUtils.logError("##MyFirebaseMessagingService", e.message.toString())
            }
        }
    }

    override fun onNewToken(token: String) {
        MySharedPreference.setStringTokenValue(this, CONST_TOKEN, token)
    }

    private fun popUpNotificaiton(title: String?, message: String?) {

        val notificationLayout = RemoteViews(packageName, R.layout.item_incoming_notification)
        notificationLayout.setTextViewText(R.id.label_title, title)
        notificationLayout.setTextViewText(R.id.label_message, message)

        val sound: Uri =
            Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.alertringtone)

        val ringtone = RingtoneManager.getRingtone(applicationContext, sound)
        ringtone?.play()

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.tranparentlogo)
            .setSound(sound)
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

//         Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelMyFarm"
            val description = "Farm Notification "
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            channel.setShowBadge(true)
            channel.setVibrationPattern(longArrayOf(300, 500, 1500))
            channel.enableVibration(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val buildNotification = notificationBuilder.build()
        buildNotification.visibility = (Notification.VISIBILITY_PUBLIC)

        val mNotifyMgr = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(1, buildNotification)

    }

}