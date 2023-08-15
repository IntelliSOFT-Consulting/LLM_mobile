package com.intellisoft.myapplication.landing_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.chat.Chat
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.profile.ProfileDetails

class MainActivity : AppCompatActivity() {

    private lateinit var tvGreetings: TextView
    private lateinit var tvDate: TextView

    private val formatterHelper = FormatterClassHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvGreetings = findViewById(R.id.tvGreetings)
        tvDate = findViewById(R.id.tvDate)

        val greeting = formatterHelper.getGreetingByTime(this)
        val dateNow = formatterHelper.getDate()

        tvGreetings.text = greeting
        tvDate.text = dateNow

        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            val intent = Intent(this, ProfileDetails::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.linearLayoutRunning).setOnClickListener {
            formatterHelper.saveSharedPreference(this, "searchSubject","RUNNING")
            val intent = Intent(this, Chat::class.java)
            startActivity(intent)
        }
    }
}