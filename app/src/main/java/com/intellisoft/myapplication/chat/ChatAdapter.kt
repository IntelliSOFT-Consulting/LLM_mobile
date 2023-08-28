package com.intellisoft.myapplication.chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat

class ChatAdapter(private val dbChatList: ArrayList<DbChat>):
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvUsername: TextView

        val tvChatUser: TextView
        val linearLayoutUser: LinearLayout
        val cardViewUser: CardView

        val tvChatAi: TextView
        val linearLayoutAi: LinearLayout
        val cardViewAi: CardView

        init {
            tvUsername = view.findViewById(R.id.tvUsername)

            tvChatUser = view.findViewById(R.id.tvChatUser)
            linearLayoutUser = view.findViewById(R.id.linearLayoutUser)
            cardViewUser = view.findViewById(R.id.cardViewUser)

            tvChatAi = view.findViewById(R.id.tvChatAi)
            linearLayoutAi = view.findViewById(R.id.linearLayoutAi)
            cardViewAi = view.findViewById(R.id.cardViewAi)
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

        if (username == "AI"){

            holder.cardViewAi.visibility = View.VISIBLE
            holder.cardViewUser.visibility = View.GONE

            holder.tvChatAi.text = chat

        }else{

            holder.cardViewAi.visibility = View.GONE
            holder.cardViewUser.visibility = View.VISIBLE

            holder.tvChatUser.text = chat

        }




    }


}