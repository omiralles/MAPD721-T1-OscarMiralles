package com.om.bot

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

/**
 * Class to store constants used during execution
 */
class Constants {
    companion object {
        //Bot messages
        const val MESSAGE1_BOT = "Hello "
        const val MESSAGE2_BOT = "How are you?"
        const val MESSAGE3_BOT = "Good Bye "
        const val MESSAGE_STOP = "ChatBot Stopped: 56"

        //preferences
        const val KEY_USER_NAME = "user_name"
        const val USER_PREF = "PREFERENCE_NAME"

        //broadcast
        const val BASE = "com.om.bot."
        const val KEY_MESSAGE_TEXT = "message_text"
        const val KEY_STOP_SERVICE = "stop_service"
        const val BROADCAST_NEW_MESSAGE = BASE + "NEW_MESSAGE"
        const val BROADCAST_STOP_SERVICE = BASE + "STOP_SERVICE"

        const val CHAT_MESSAGE = "CHAT_MESSAGE"
        const val STOP_SERVICE = "STOP_SERVICE"
        const val CHAT_USER_NAME = "CHAT_USER_NAME"

        const val CHATBOT_USER = "ChatBot"

        //Commands
        const val CMD = "msg_cmd"
        const val CMD_SIMULATE_BOT = 10
        const val CMD_STOP_SERVICE = 20
    }
}