package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.intellisoft.myapplication.R

class ForgotPassword : AppCompatActivity() {

    private lateinit var etEmailAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etEmailAddress = findViewById(R.id.etEmailAddress)

        findViewById<Button>(R.id.btnSendCode).setOnClickListener {

            val emailAddress = etEmailAddress.text.toString()
            if (!TextUtils.isEmpty(emailAddress)){



            }else{
                etEmailAddress.error = "Enter an email address.."
            }

        }

        findViewById<TextView>(R.id.tvBackLogin).setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}