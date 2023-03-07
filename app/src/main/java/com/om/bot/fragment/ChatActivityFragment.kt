package com.om.bot.fragment

/**
* MAPD721 MidTerm Test
* @Authors
* Student Name: Oscar Miralles Fernandez
* Student ID: 301250756
**/

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.om.bot.Constants
import com.om.bot.R
import com.om.bot.adapter.ChatAdapter
import com.om.bot.model.ChatMessage
import com.om.bot.service.ChatBotService

class ChatActivityFragment: Fragment() {
    private lateinit var btnGenerateMsg: Button
    private lateinit var btnStopService: Button
    private lateinit var messageListView: ListView
    private lateinit var userName: String
    private lateinit var adapter: ChatAdapter

    companion object {
        private const val LOG_TAG = "ChatActivityFragment"
    }

    /**
     * Create fragment view using adapter to show the messages and the other components
     */
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_chat, container, false)

        loadUserFromPreferences()

        btnGenerateMsg = v.findViewById(R.id.btnGenerateMsg)
        btnGenerateMsg.setOnClickListener {
            simulateBotMessage(Constants.MESSAGE1_BOT + userName)
            simulateBotMessage(Constants.MESSAGE2_BOT)
            simulateBotMessage(Constants.MESSAGE3_BOT + userName)
        }

        btnStopService = v.findViewById(R.id.btnStopService)
        btnStopService.setOnClickListener {
            stopService(Constants.MESSAGE_STOP)
        }
        messageListView = v.findViewById(R.id.messageList)
        adapter = ChatAdapter(requireActivity(), ArrayList<ChatMessage>())
        messageListView.adapter = adapter

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerServiceStateChangeReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(mServiceStateChangeReceiver)
    }

    /**
     * Send to the service new message command
     */
    private fun simulateBotMessage(messageText: String) {
        val data: Bundle = Bundle()
        data.putInt(Constants.CMD,Constants.CMD_SIMULATE_BOT)
        data.putString(Constants.KEY_MESSAGE_TEXT, messageText)

        val intent = Intent(context, ChatBotService::class.java)
        intent.putExtras(data)
        activity?.startService(intent)
    }

    /**
     * Send to the service stop command
     */
    private fun stopService(messageText: String) {
        val data = Bundle()
        data.putInt(Constants.CMD,Constants.CMD_STOP_SERVICE)
        data.putString(Constants.KEY_STOP_SERVICE, messageText)

        val intent = Intent(context, ChatBotService::class.java)
        intent.putExtras(data)
        activity?.startService(intent)
    }

    /**
     * Display messages on Recycled View
     */
    fun displayMessage(message: ChatMessage?) {
        if (message != null) {
            adapter.add(message)
        }
        adapter.notifyDataSetChanged()
        scroll()
    }

    private fun scroll() {
        messageListView.setSelection(messageListView.count - 1)
    }

    private fun loadUserFromPreferences() {
        val pref = this.activity?.getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        if (pref != null) {
            userName = pref.getString(Constants.KEY_USER_NAME, "User").toString( )
        }
    }

    /**
     * Create the broadcast receiver to show messages in main screen
     */
    private val mServiceStateChangeReceiver = object : BroadcastReceiver() {
        private val TAG = "BroadcastReceiver"

        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.getAction();
            val data: Bundle? = intent?.getExtras();
            Log.d(TAG, "received broadcast message from service: " + action);

            if (Constants.BROADCAST_NEW_MESSAGE == action) {
                val userName: String? = data?.getString(Constants.CHAT_USER_NAME)
                val message: String? = data?.getString(Constants.CHAT_MESSAGE)
                val chatMessage = ChatMessage(userName,message)
                displayMessage(chatMessage)
            } else if (Constants.BROADCAST_STOP_SERVICE == action) {
                val chatMessage = ChatMessage("Status ", "Stopped: 56", true);
                displayMessage(chatMessage);
            } else {
                Log.v(TAG, "do nothing for action: " + action);
            }
        }

    }

    /**
     * Register the server states
     */
    private fun registerServiceStateChangeReceiver() {
        Log.d(LOG_TAG, "registering service state change receiver...")
        val intentFilter = IntentFilter()

        intentFilter.addAction(Constants.BROADCAST_NEW_MESSAGE)
        intentFilter.addAction(Constants.BROADCAST_STOP_SERVICE)
        activity?.registerReceiver(mServiceStateChangeReceiver, intentFilter)
    }
}