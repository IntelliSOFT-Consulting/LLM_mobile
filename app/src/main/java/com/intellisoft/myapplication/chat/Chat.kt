package com.intellisoft.myapplication.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbChat
import com.intellisoft.myapplication.data_class.DbLLM
import com.intellisoft.myapplication.data_class.DbMessages
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.landing_page.MainActivity
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Chat : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var etMessage: EditText
    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var imgBtnSend: ImageButton

    private val formatterHelper = FormatterClassHelper()
    private val retrofitCallsAuthentication = RetrofitCallsAuthentication()

    private lateinit var tvSelectionName:TextView
    private lateinit var tvSelectionDescription:TextView
    val dbChatList = ArrayList<DbChat>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerView)
        etMessage = findViewById(R.id.etMessage)
        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
        imgBtnSend = findViewById(R.id.imgBtnSend)

        tvSelectionDescription = findViewById(R.id.tvSelectionDescription)
        tvSelectionName = findViewById(R.id.tvSelectionName)

        recyclerView.layoutManager = LinearLayoutManager(this)

        setVisibility(true)
        getChatInfo()

        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }
        imgBtnSend.setOnClickListener {

            val phoneNumber = formatterHelper.retrieveSharedPreference(this, "contact")
            val username = formatterHelper.retrieveSharedPreference(this, "username")

            val message = etMessage.text.toString()
            if (!TextUtils.isEmpty(message) && phoneNumber != null && username != null){

                setVisibility(false)
                processChat(message, username, phoneNumber)

            }else{
                etMessage.error = "Message cannot be empty.."
            }
        }

        updateMetadata()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateMetadata() {
        val localDateTime = formatterHelper.getLocalTime()
        formatterHelper.saveSharedPreference(this, "observedTimeStartUse", localDateTime.toString())
    }

    private fun setVisibility(isSend: Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            if(isSend){
                progressBar.visibility = View.GONE
                imgBtnSend.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.VISIBLE
                imgBtnSend.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getChatInfo() {

        val imageResource = formatterHelper.retrieveSharedPreference(this, "searchSubjectImage")
        val searchSubject = formatterHelper.retrieveSharedPreference(this, "searchSubject")

        if (imageResource != null && searchSubject != null){

            tvSelectionName.text = searchSubject
            tvSelectionDescription.text = "Ask anything about $searchSubject"
            imageView.setImageResource(imageResource.toInt())


        }else{
            startActivity(Intent(this, MainActivity::class.java))
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

            addList(DbChat(username, message))


            CoroutineScope(Dispatchers.IO).launch {
                val messageResponse = retrofitCallsAuthentication.searchLLm(this@Chat, dbLLM)
                if (messageResponse != null){
                    setVisibility(true)
                    addList(DbChat("AI", messageResponse))

                }
            }
        }
    }

    private fun addList(dbChat:DbChat){
        val dbChat = DbChat(dbChat.username, dbChat.chat)
        dbChatList.add(dbChat)
        populateRecyclerView()

    }

    private fun populateRecyclerView() {

        CoroutineScope(Dispatchers.Main).launch {
            val chatAdapter = ChatAdapter(dbChatList)
            recyclerView.adapter = chatAdapter
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        formatterHelper.updateMetaData(this)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        formatterHelper.updateMetaData(this)
    }
}