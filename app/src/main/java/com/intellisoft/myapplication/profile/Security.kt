package com.intellisoft.myapplication.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication

class Security : AppCompatActivity() {

    private lateinit var etCurrentPassword:EditText
    private lateinit var etPassword:EditText
    private lateinit var etConfirmPassword:EditText
    private var retrofitCallsAuthentication = RetrofitCallsAuthentication()
    private var formatterHelper = FormatterClassHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

        etCurrentPassword = findViewById(R.id.etCurrentPassword)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnUpdatePassword).setOnClickListener {

            val currentPassword = etCurrentPassword.text.toString()
            val newPassword = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val username = formatterHelper.retrieveSharedPreference(this,"username")

            if (!TextUtils.isEmpty(currentPassword) &&
                !TextUtils.isEmpty(newPassword) &&
                !TextUtils.isEmpty(confirmPassword) &&
                        username != null){

                if (newPassword == confirmPassword){

                    retrofitCallsAuthentication.passwordChange(
                        this,
                        newPassword,
                        username
                    )

                }else{
                    Toast.makeText(this, "New password and confirm password must match.", Toast.LENGTH_SHORT).show()
                }


            }else{
                Toast.makeText(this, "Fill all fields..", Toast.LENGTH_SHORT).show()
            }



        }


        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }
    }
}