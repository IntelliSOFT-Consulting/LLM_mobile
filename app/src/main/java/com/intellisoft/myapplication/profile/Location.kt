package com.intellisoft.myapplication.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.helper_class.FormatterClassHelper

class Location : AppCompatActivity() {

    private lateinit var tvLocation:TextView
    private lateinit var tvSpecificLocation:TextView

    private var formatterHelper = FormatterClassHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        tvLocation = findViewById(R.id.tvLocation)
        tvSpecificLocation = findViewById(R.id.tvSpecificLocation)

        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }

        getLocationDetails()
    }

    private fun getLocationDetails() {

        val heardAppFrom = formatterHelper.retrieveSharedPreference(this,
            "heardAppFrom")
        val specificLocation = formatterHelper.retrieveSharedPreference(this,
            "specificLocation")

        if (heardAppFrom != null){
            tvLocation.text = heardAppFrom
        }
        if (specificLocation != null){
            tvSpecificLocation.text = specificLocation
        }


    }
}