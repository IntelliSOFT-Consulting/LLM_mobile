package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.intellisoft.myapplication.R

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Start the main activity after a delay
        Handler().postDelayed({
            startActivity(Intent(this, SignUp1::class.java))
            finish()
        }, 2000)

    }
}