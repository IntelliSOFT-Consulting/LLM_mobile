package com.intellisoft.myapplication.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat

class Chat : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbChatList = ArrayList<DbChat>()

        for (i in 1..10){

            var username = "Jane"
            if (i % 2 == 0){
                username = "AI"
            }

            val dbChat= DbChat(username, "Lorem Ipsum is simply dummy text of the printing and" +
                    " typesetting industry. Lorem Ipsum has been the industry's standard dummy" +
                    " text ever since the 1500s.")

            dbChatList.add(dbChat)
        }

        val chatAdapter = ChatAdapter(dbChatList)
        recyclerView.adapter = chatAdapter


        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }
    }
}