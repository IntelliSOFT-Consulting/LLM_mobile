package com.intellisoft.myapplication.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbProfile
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication

class ProfileEdit : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var heardAppFrom :String = ""
    private var contact :String = ""
    private var age :String = ""
    private var username :String = ""

    private lateinit var etFullName:EditText
    private lateinit var etPhoneNumber:EditText
    private lateinit var etAge:EditText
    private lateinit var etEmailAddress:EditText

    private var gender: String = ""
    private lateinit var spinnerGender: Spinner
    private var formatterHelper = FormatterClassHelper()
    private var retrofitCallsAuthentication = RetrofitCallsAuthentication()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        etFullName = findViewById(R.id.etFullName)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etAge = findViewById(R.id.etAge)
        etEmailAddress = findViewById(R.id.etEmailAddress)

        initSpinner()
        getUserData()

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            val fullName = etFullName.text.toString()
            val age = etAge.text.toString()

            if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(age)){

                val dbProfile = DbProfile(age.toInt(), gender, contact, heardAppFrom, contact)
                retrofitCallsAuthentication.updateUserData(this, dbProfile)

            }else{
                Toast.makeText(this, "Update was not possible. Try again", Toast.LENGTH_LONG).show()
            }



        }
    }

    private fun getUserData() {

        heardAppFrom = formatterHelper.retrieveSharedPreference(this,"heardAppFrom").toString()
        contact = formatterHelper.retrieveSharedPreference(this,"contact").toString()
        age = formatterHelper.retrieveSharedPreference(this,"age").toString()

        val fullName = formatterHelper.retrieveSharedPreference(this, "fullName")
        val userName = formatterHelper.retrieveSharedPreference(this, "username")
        if (fullName != null){
            username = fullName
        }else if (userName != null){
            username = userName
        }else{
            ""
        }

        val genderValue = formatterHelper.retrieveSharedPreference(this,"gender")

        etAge.setText(age)
        etPhoneNumber.setText(contact)
        etEmailAddress.setText(username)
//        spinnerGender.setSelection(1)


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