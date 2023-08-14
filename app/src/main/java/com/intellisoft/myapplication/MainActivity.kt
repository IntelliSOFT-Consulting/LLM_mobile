package com.intellisoft.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    }
}