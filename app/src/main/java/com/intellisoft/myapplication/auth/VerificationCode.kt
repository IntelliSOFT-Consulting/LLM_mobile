package com.intellisoft.myapplication.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaos.view.PinView
import com.intellisoft.myapplication.R

class VerificationCode : AppCompatActivity() {

    private lateinit var pinView: PinView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        pinView = findViewById(R.id.pinview)


    }
}