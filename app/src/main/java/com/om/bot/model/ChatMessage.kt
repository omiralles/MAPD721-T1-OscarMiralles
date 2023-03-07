package com.om.bot.model

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

/**
 * Class created to store messages sent in chat
 */
class ChatMessage {
    private var id: String? = null
    private var userName: String? = null
    private var body: String? = null
    private var statusUpdate = false

    /**
     * Constructors
     */
    constructor(userName: String?, body: String?) : this(null, userName, body, false) {}
    constructor(userName: String?, body: String?, statusUpdate: Boolean) : this(
        null,
        userName,
        body,
        statusUpdate
    ) {
    }

    constructor(id: String?, userName: String?, body: String?, statusUpdate: Boolean) {
        this.id = id
        this.userName = userName
        this.body = body
        this.statusUpdate = statusUpdate
    }

    /**
     * Get and Set methods
     */
    fun getUserName(): String? {
        return userName
    }

    fun getBody(): String? {
        return body
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun setUserName(userName: String?) {
        this.userName = userName
    }

    fun setBody(body: String?) {
        this.body = body
    }

    fun isStatusUpdate(): Boolean {
        return statusUpdate
    }

    override fun toString(): String {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", body='" + body + '\'' +
                '}'
    }
}