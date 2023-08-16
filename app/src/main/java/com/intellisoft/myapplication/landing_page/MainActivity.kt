package com.intellisoft.myapplication.landing_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.chat.Chat
import com.intellisoft.myapplication.chat.ChatAdapter
import com.intellisoft.myapplication.data_class.DbChat
import com.intellisoft.myapplication.data_class.DbNCDs
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.profile.ProfileDetails
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var tvGreetings: TextView
    private lateinit var tvDate: TextView

    private val formatterHelper = FormatterClassHelper()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        tvGreetings = findViewById(R.id.tvGreetings)
        tvDate = findViewById(R.id.tvDate)

        processImages()

        val greeting = formatterHelper.getGreetingByTime(this)
        val dateNow = formatterHelper.getDate()

        tvGreetings.text = greeting
        tvDate.text = dateNow

        findViewById<ImageView>(R.id.imageView).setOnClickListener {
            val intent = Intent(this, ProfileDetails::class.java)
            startActivity(intent)
        }
//        findViewById<LinearLayout>(R.id.linearLayoutRunning).setOnClickListener {
//            formatterHelper.saveSharedPreference(this, "searchSubject","RUNNING")
//            val intent = Intent(this, Chat::class.java)
//            startActivity(intent)
//        }
    }

    private fun processImages() {

        val dbNCDsList = getNcds("en")

        val chatAdapter = MainActivityAdapter(dbNCDsList, this)
        recyclerView.adapter = chatAdapter

    }

    private fun getNcds(type:String):ArrayList<DbNCDs> {
        val dbNCDsList = ArrayList<DbNCDs>()
        val dbNCDStringList = listOf<String>(
            "Being Physically Active",
            "Choosing Healthy Diets",
            "Saying No to Tobacco",
            "Reducing Use of Alcohol",
            "Beating Tobacco and Unhealthy foods",
            "Promoting Physical Activity",
            "Universal Health Coverage",
            "Promoting Cleaner cities",
            "Educating Children",
            "Promoting Regulation",
        )

        for (i in 1..10) {

            val drawableResName = "en$i"
            val drawableResId = resources.getIdentifier(drawableResName, "drawable", packageName)
            val dbNCD = DbNCDs(drawableResId, dbNCDStringList[i - 1])
            dbNCDsList.add(dbNCD)

        }

        return dbNCDsList
    }
}