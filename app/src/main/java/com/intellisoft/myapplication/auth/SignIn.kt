package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.intellisoft.myapplication.R

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViewById<TextView>(R.id.tvSignUp).setOnClickListener {
            val intent = Intent(this, SignUp1::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnSendCode).setOnClickListener {
            val intent = Intent(this, VerificationCode::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.tvForgotPassword).setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }
}