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
    private lateinit var etDob:EditText
    private lateinit var etEmailAddress:EditText
    private lateinit var etUniqueId:EditText

    private lateinit var spinnerGender:Spinner

    private var gender: String? = ""

    private var formatterHelper = FormatterClassHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etDob = findViewById(R.id.etDob)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etUniqueId = findViewById(R.id.etUniqueId)


        initSpinner()

        findViewById<TextView>(R.id.tvSignIn).setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnNext).setOnClickListener {

            val fullName = etFullName.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val dob = etDob.text.toString()
            val emailAddress = etEmailAddress.text.toString()
            var uniqueId = etUniqueId.text.toString()

            if (!TextUtils.isEmpty(fullName) &&
                !TextUtils.isEmpty(phoneNumber) &&
                !TextUtils.isEmpty(dob) &&
                !TextUtils.isEmpty(emailAddress) &&
                gender != ""){

                if (formatterHelper.validateEmail(emailAddress) &&
                    formatterHelper.isValidDateFormat(dob)){

                    if (TextUtils.isEmpty(uniqueId)) uniqueId = formatterHelper.getUniqueId(dob, fullName)

                    val todayDate = formatterHelper.getTodayDate()

                    val dbSignUp = DbSignUp(
                        uniqueId,
                        dob,
                        gender.toString(),
                        phoneNumber,
                        todayDate,
                        "",
                        "",
                        fullName,
                        "",
                        emailAddress.trimEnd(),
                        "",
                        Collections.emptyList())

                    val json = Gson().toJson(dbSignUp)
                    formatterHelper.saveSharedPreference(this, "dbSignUp", json)

                    val intent = Intent(this, SignUp2::class.java)
                    startActivity(intent)

                }else{
                    if (!formatterHelper.isValidDateFormat(dob)) etDob.error = "Date should be in format: yyyy-MM-dd"
                    if (!formatterHelper.validateEmail(emailAddress)) etEmailAddress.error = "Enter a valid email address"
                }



            }else{

                if (TextUtils.isEmpty(fullName)) etFullName.error = "Enter your name"
                if (TextUtils.isEmpty(phoneNumber)) etPhoneNumber.error = "Enter your phone number"
                if (TextUtils.isEmpty(dob)) etDob.error = "Enter your dob"
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