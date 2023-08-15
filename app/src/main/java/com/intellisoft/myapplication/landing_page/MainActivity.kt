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

        val dbNCDsList = ArrayList<DbNCDs>()

        val dbNCDsRun = DbNCDs(R.drawable.running, "Running")
        val dbNCDsDiet = DbNCDs(R.drawable.food, "Diet")
        val dbNCDsSmoking = DbNCDs(R.drawable.smoking, "Smoking and Drugs")
        val dbNCDsHeart = DbNCDs(R.drawable.heart, "Heart")
        val dbNCDsStress = DbNCDs(R.drawable.stress, "Stress")

        dbNCDsList.addAll(mutableListOf(
            dbNCDsRun, dbNCDsDiet, dbNCDsSmoking, dbNCDsHeart, dbNCDsStress))


        val chatAdapter = MainActivityAdapter(dbNCDsList, this)
        recyclerView.adapter = chatAdapter

    }
}