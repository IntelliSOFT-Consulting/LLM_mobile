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
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbRoles
import com.intellisoft.myapplication.data_class.DbSignUp
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication

class SignUp2 : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var etSpecificLocation: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var spinnerLocation: Spinner
    private lateinit var spinnerHearUs: Spinner

    private var location: String = ""
    private var aboutUs: String = ""

    private var formatterHelper = FormatterClassHelper()
    private var retrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)

        etSpecificLocation = findViewById(R.id.etSpecificLocation)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        findViewById<Button>(R.id.btnSignUp).setOnClickListener {

            val specificLocation = etSpecificLocation.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (!TextUtils.isEmpty(specificLocation) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(confirmPassword) &&
                location != "" && aboutUs != ""){

                if (password == confirmPassword){

                    val rolesList = ArrayList<DbRoles>()
                    rolesList.add(DbRoles("USER"))

                    val dbSignUpJson = formatterHelper.retrieveSharedPreference(this, "dbSignUp")
                    val dbSignUp = Gson().fromJson(dbSignUpJson, DbSignUp::class.java)
                    dbSignUp.heardAppFrom = aboutUs
                    dbSignUp.password = password
                    dbSignUp.roles = rolesList

                    dbSignUp.location = location
                    dbSignUp.specificLocation = specificLocation

                    val age = dbSignUp.dob
                    dbSignUp.dob = age

                    retrofitCallsAuthentication.registerUser(this, dbSignUp)

                }else{
                    Toast.makeText(this, "Passwords must match!!", Toast.LENGTH_LONG).show()
                }


            }else{
                if (TextUtils.isEmpty(specificLocation)) etSpecificLocation.error = "Enter your specific location"
                if (TextUtils.isEmpty(password)) etPassword.error = "Enter your password"
                if (TextUtils.isEmpty(confirmPassword)) etConfirmPassword.error = "Enter your password"
                if (location == "") Toast.makeText(this, "Please select your location", Toast.LENGTH_LONG).show()
                if (aboutUs == "") Toast.makeText(this, "Please select your where you heard us from", Toast.LENGTH_LONG).show()

            }

        }
        findViewById<TextView>(R.id.tvPrevious).setOnClickListener {
            val intent = Intent(this, SignUp1::class.java)
            startActivity(intent)
        }


        initSpinner()

        spinnerHearUs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                aboutUs = parent?.getItemAtPosition(position).toString()
                // Handle selected item from spinner2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where no item is selected
            }
        }





    }

    private fun initSpinner() {

        spinnerLocation = findViewById(R.id.spinnerLocation)
        spinnerLocation.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.array_location,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerLocation.adapter = adapter
        }


        spinnerHearUs = findViewById(R.id.spinnerHearUs)
        spinnerHearUs.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            this,
            R.array.array_hear_us,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerHearUs.adapter = adapter
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        location = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
    
}