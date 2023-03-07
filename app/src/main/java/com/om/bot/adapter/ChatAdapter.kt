package com.om.bot.adapter

/**
 * MAPD721 MidTerm Test
 * @Authors
 * Student Name: Oscar Miralles Fernandez
 * Student ID: 301250756
 **/

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.om.bot.Constants
import com.om.bot.R
import com.om.bot.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter to create the message View List.
 */
class ChatAdapter(private val context: Context, private val chatMessages: ArrayList<ChatMessage>): BaseAdapter() {
    override fun getCount(): Int {
        return chatMessages.size
    }

    override fun getItem(position: Int): ChatMessage {
        return chatMessages.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val chatMessage: ChatMessage = getItem(position)
        val retView: View

        val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null) {
            retView = vi.inflate(R.layout.chat_message, null)
            holder = createViewHolder(retView)
            retView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }

        if (chatMessage.isStatusUpdate()) {
            holder.bubbleMessageContainer?.setVisibility(View.GONE)
            holder.txtUser?.setText(Constants.CHATBOT_USER + " " + chatMessage.getBody())

            val messageDateTime =
                SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().time)
            holder.txtInfo?.setText(messageDateTime)
        } else {
            holder.bubbleMessageContainer?.visibility = View.VISIBLE
            holder.txtMessage?.setText(chatMessage.getBody())
            holder.txtUser?.setText(Constants.CHATBOT_USER)

            val messageDateTime =
                SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().time)
            holder.txtInfo?.setText(messageDateTime)
        }

        return retView
    }

    fun add(message: ChatMessage) {
        chatMessages.add(message)
    }

    private fun createViewHolder(v: View): ViewHolder {
        val holder = ViewHolder()
        holder.bubbleMessageContainer =
            v.findViewById<View>(R.id.messageContainer) as LinearLayout
        holder.txtMessage = v.findViewById<View>(R.id.txtMessage) as TextView
        holder.txtUser = v.findViewById<View>(R.id.txtUser) as TextView
        holder.txtInfo = v.findViewById<View>(R.id.txtInfo) as TextView
        return holder
    }


    private class ViewHolder {
        var bubbleMessageContainer: LinearLayout? = null
        var txtMessage: TextView? = null
        var txtUser: TextView? = null
        var txtInfo: TextView? = null
    }
}