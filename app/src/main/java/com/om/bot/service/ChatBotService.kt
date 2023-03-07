package com.om.bot.service

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.om.bot.Constants
import com.om.bot.notification.NotificationDecorator

/**
 * Create chatservice to send broadcast messages based on commands executed
 */
class ChatBotService : Service() {
    private val TAG = "ChatService"
    private lateinit var userName: String
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationDecorator: NotificationDecorator
    private lateinit var data: Bundle

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationDecorator = NotificationDecorator(this,notificationManager)

        loadUserFromPreferences()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            data = intent.extras!!
            handleData(data)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        notificationManager.cancelAll()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Load user stored by key from Shared preferences
     */
    private fun loadUserFromPreferences() {
        val pref = getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        userName = pref.getString(Constants.KEY_USER_NAME, "User").toString()
    }

    /**
     * Handle commands executed, send broadcast and notifications
     */
    private fun handleData(data: Bundle) {
        val command = data.getInt(Constants.CMD)

        if (command == Constants.CMD_SIMULATE_BOT) {
            val messageText = data.getString(Constants.KEY_MESSAGE_TEXT)

            if (messageText != null) {
                sendBroadcastNewMessage(userName, messageText)
                notificationDecorator.displaySimpleNotification("ChatBot message...", messageText)
            }
        }
        else if (command == Constants.CMD_STOP_SERVICE) {
            val messageText = data.getString(Constants.KEY_STOP_SERVICE)

            if (messageText != null) {
                sendBroadcastStopService(messageText)
                notificationDecorator.displaySimpleNotification("ChatBot Stop", Constants.MESSAGE_STOP)
            }
        }
        else {
            Log.w(TAG,"Ignoring Unknown Command! id=$command")
        }
    }

    /**
     * Send broadcast messages
     */
    private fun sendBroadcastNewMessage(userName: String, message: String) {
        val intent = Intent()
        intent.action = Constants.BROADCAST_NEW_MESSAGE

        val data = Bundle()
        data.putString(Constants.CHAT_MESSAGE, message)
        data.putString(Constants.CHAT_USER_NAME, userName)
        intent.putExtras(data)

        sendBroadcast(intent)
    }

    /**
     * Send broadcast stop service
     */
    private fun sendBroadcastStopService (message: String) {
        val intent = Intent()
        intent.action = Constants.BROADCAST_STOP_SERVICE

        val data = Bundle()
        data.putString(Constants.STOP_SERVICE, message)
        intent.putExtras(data)

        sendBroadcast(intent)
    }
}