package com.intellisoft.myapplication.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbSignUp
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import java.util.Collections

class SignUp1 : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var etFullName:EditText
    private lateinit var etPhoneNumber:EditText
    private lateinit var etAge:EditText
    private lateinit var etEmailAddress:EditText

    private lateinit var spinnerGender:Spinner

    private var gender: String? = ""

    private var formatterHelper = FormatterClassHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etAge = findViewById(R.id.etAge)
        etEmailAddress = findViewById(R.id.etEmailAddress)


        initSpinner()

        findViewById<TextView>(R.id.tvSignIn).setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener {

            val fullName = etFullName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val age = etAge.text.toString()
            val emailAddress = etEmailAddress.text.toString()

            if (!TextUtils.isEmpty(fullName) &&
                !TextUtils.isEmpty(phoneNumber) &&
                !TextUtils.isEmpty(age) &&
                !TextUtils.isEmpty(emailAddress) &&
                gender != ""){

                if (formatterHelper.validateEmail(emailAddress)){

                    val todayDate = formatterHelper.getTodayDate()

                    val dbSignUp = DbSignUp(
                        phoneNumber,
                        age.toInt(),
                        gender.toString(),
                        phoneNumber,
                        todayDate,
                        "",
                        "",
                        fullName,
                        "",
                        emailAddress,
                        "",
                        Collections.emptyList())

                    val json = Gson().toJson(dbSignUp)
                    formatterHelper.saveSharedPreference(this, "dbSignUp", json)

                    val intent = Intent(this, SignUp2::class.java)
                    startActivity(intent)

                }else{
                    etEmailAddress.error = "Enter a valid email address"
                }



            }else{

                if (TextUtils.isEmpty(fullName)) etFullName.error = "Enter your name"
                if (TextUtils.isEmpty(phoneNumber)) etPhoneNumber.error = "Enter your phone number"
                if (TextUtils.isEmpty(age)) etAge.error = "Enter your age"
                if (TextUtils.isEmpty(emailAddress)) etEmailAddress.error = "Enter your email address"
                if (gender == "") Toast.makeText(this, "Please select your gender", Toast.LENGTH_LONG).show()

            }



        }
    }


    private fun initSpinner() {

        spinnerGender = findViewById(R.id.spinnerGender)
        spinnerGender.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.array_gender,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerGender.adapter = adapter
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        gender = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}