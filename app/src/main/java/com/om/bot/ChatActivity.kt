package com.om.bot

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ChatActivity: AppCompatActivity() {
    private lateinit var userName: String

    companion object {
        private const val LOG_TAG = "ChatActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        loadUserFromPreferences()
    }

    /**
     * Load user stored by key from Shared preferences
     */
    private fun loadUserFromPreferences() {
        val pref = getSharedPreferences(Constants.USER_PREF, Context.MODE_PRIVATE)
        userName = pref.getString(Constants.KEY_USER_NAME, "User").toString()
    }
}