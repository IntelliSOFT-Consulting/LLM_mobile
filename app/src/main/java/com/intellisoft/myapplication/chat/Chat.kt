package com.intellisoft.myapplication.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat
import com.intellisoft.myapplication.data_class.DbLLM
import com.intellisoft.myapplication.data_class.DbMessages
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Chat : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var etMessage: EditText
    private val formatterHelper = FormatterClassHelper()
    private val retrofitCallsAuthentication = RetrofitCallsAuthentication()

    private val queue = ArrayDeque<Pair<DbChat, DbChat>>()
    val dbChatList = ArrayList<DbChat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        etMessage = findViewById(R.id.etMessage)

        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }
        findViewById<ImageButton>(R.id.imgBtnSend).setOnClickListener {

            val phoneNumber = formatterHelper.retrieveSharedPreference(this, "contact")
            val username = formatterHelper.retrieveSharedPreference(this, "username")

            val message = etMessage.text.toString()
            if (!TextUtils.isEmpty(message) && phoneNumber != null && username != null){

                processChat(message, username, phoneNumber)

            }else{
                etMessage.error = "Message cannot be empty.."
            }
        }
    }

    private fun processChat(message: String, username: String, phoneNumber: String) {

        CoroutineScope(Dispatchers.Main).launch {

            etMessage.setText("")

            var searchSubject = ""
            val search = formatterHelper.retrieveSharedPreference(this@Chat, "searchSubject")
            if (search != null){
                searchSubject = search
            }


            val dbMessagesList = ArrayList<DbMessages>()
            val dbMessages = DbMessages(
                "user",
                message)
            dbMessagesList.add(dbMessages)

            val dbLLM = DbLLM(
//                    phoneNumber,
//                    searchSubject,
                dbMessagesList)

            CoroutineScope(Dispatchers.IO).launch {
                val messageResponse = retrofitCallsAuthentication.searchLLm(this@Chat, dbLLM)
                if (messageResponse != null){

                    val dbChatRequest = DbChat(username, message)
                    val dbChatResponse = DbChat("AI", messageResponse)

                    queue.addLast(dbChatRequest to dbChatResponse)

                    populateRecyclerView(queue)
                }

            }


        }

    }

    private fun populateRecyclerView(queue: ArrayDeque<Pair<DbChat, DbChat>>) {

        while (queue.isNotEmpty()){
            val (request, response) = queue.removeFirst()

            val dbChatRequest = DbChat(request.username, request.chat)
            val dbChatResponse = DbChat(response.username, response.chat)
            dbChatList.add(dbChatRequest)
            dbChatList.add(dbChatResponse)

        }

        CoroutineScope(Dispatchers.Main).launch {
            val chatAdapter = ChatAdapter(dbChatList)
            recyclerView.adapter = chatAdapter
        }

    }
}