package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbSignIn
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication

class SignIn : AppCompatActivity() {

    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText

    private var formatterHelper = FormatterClassHelper()
    private var retrofitCallsAuthentication = RetrofitCallsAuthentication()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)

        findViewById<Button>(R.id.btnSendCode).setOnClickListener {

            val emailAddress = etEmailAddress.text.toString()
            val password = etPassword.text.toString()

            if (!TextUtils.isEmpty(emailAddress) && !TextUtils.isEmpty(password)){

                val dbSignIn = DbSignIn(emailAddress, password)
                retrofitCallsAuthentication.loginUser(this, dbSignIn)

            }else{
                if (TextUtils.isEmpty(emailAddress)) etEmailAddress.error = "Email address cannot be null.."
                if (TextUtils.isEmpty(password)) etPassword.error = "Password cannot be null.."
            }

        }

        findViewById<TextView>(R.id.tvSignUp).setOnClickListener {
            val intent = Intent(this, SignUp1::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }
}