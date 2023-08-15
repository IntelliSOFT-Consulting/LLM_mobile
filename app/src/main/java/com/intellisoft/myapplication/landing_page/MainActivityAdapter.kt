package com.intellisoft.myapplication.landing_page

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat
import com.intellisoft.myapplication.data_class.DbNcd

//class MainActivityAdapter(private val dbChatList: ArrayList<DbNcd>):
//    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
//
//        val tvUsername: ImageView
//        val tvChat: TextView
//
//        init {
//            tvUsername = view.findViewById(R.id.tvUsername)
//            tvChat = view.findViewById(R.id.tvChat)
//            linearLayout = view.findViewById(R.id.linearLayout)
//        }
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.chat_layout, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount() = dbChatList.size
//
//    @SuppressLint("ResourceAsColor")
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val imageName = dbChatList[position].imageName
//        val imageSource = dbChatList[position].imageSource
//
//        holder.tvUsername.text = username
//        holder.tvChat.text = chat
//
//        if (username.contains("AI")){
//            holder.linearLayout.setBackgroundColor(R.color.darker_gray)
//            holder.tvChat.setTextColor(R.color.black)
//        }
//
//
//
//    }
//
//
//}