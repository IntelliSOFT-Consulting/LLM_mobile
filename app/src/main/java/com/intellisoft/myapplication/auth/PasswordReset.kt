package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.intellisoft.myapplication.R

class PasswordReset : AppCompatActivity() {

    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnResetPassword).setOnClickListener {

            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)){



            }else{
                if (TextUtils.isEmpty(password)) etPassword.error = "Enter password.."
                if (TextUtils.isEmpty(confirmPassword)) etConfirmPassword.error = "Enter confirm password.."
            }

        }

        findViewById<TextView>(R.id.tvBackLogin).setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

    }
}