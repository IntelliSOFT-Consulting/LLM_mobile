package com.intellisoft.myapplication.chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat

class ChatAdapter(private val dbChatList: ArrayList<DbChat>):
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvUsername: TextView
        val tvChat: TextView
        val linearLayout: LinearLayout

        init {
            tvUsername = view.findViewById(R.id.tvUsername)
            tvChat = view.findViewById(R.id.tvChat)
            linearLayout = view.findViewById(R.id.linearLayout)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dbChatList.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val username = dbChatList[position].username
        val chat = dbChatList[position].chat

        holder.tvUsername.text = username
        holder.tvChat.text = chat

        if (username.contains("AI")){
            holder.linearLayout.setBackgroundColor(Color.rgb(237,240,247))
            holder.tvChat.setTextColor(Color.BLACK)
        }



    }


}