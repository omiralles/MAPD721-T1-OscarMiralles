package com.om.bot.notification

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import com.om.bot.ChatActivity

/**
 * This class create the channel needed to send notifications.
 */
class NotificationDecorator(context: Context?, notificationManager: NotificationManager) {
    private val TAG = "NotificationDecorator"
    val CHANNEL_ID = "chatBotChannel_1"
    private lateinit var context: Context
    private var notificationMgr: NotificationManager
    //Notification index
    private var notificationId: Int = 0

    init {
        if (context != null) {
            this.context = context
        }
        this.notificationMgr = notificationManager
        createChannel(notificationManager)
    }

    /**
     * Create notification channel
     */
    private fun createChannel(notificationManager: NotificationManager) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID, "ChatBot Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    /**
     * Function to display notifications. Permissions added to manifest.
     */
    fun displaySimpleNotification(title: String?, contentText: String?) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            //Immutable flag necessary for new versions
            intent, PendingIntent.FLAG_IMMUTABLE
        )

        // notification message
        try {
            val noti = Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(contentText)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID)
                .build()
            noti.flags = noti.flags or Notification.FLAG_AUTO_CANCEL
            notificationMgr!!.notify(notificationId, noti)

            //Allow more than one notification
            notificationId += 1
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message!!)
        }
    }
}