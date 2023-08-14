package com.intellisoft.myapplication.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.intellisoft.myapplication.MainActivity
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.auth.SignIn
import com.intellisoft.myapplication.helper_class.FormatterClassHelper


class ProfileDetails : AppCompatActivity() {

    private var formatterHelper = FormatterClassHelper()
    private lateinit var tvUsername: TextView
    private lateinit var tvPhoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)

        tvUsername = findViewById(R.id.tvUsername)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)

        findViewById<TextView> (R.id.tvDeleteAccount).setOnClickListener {
            showBottomDialog()
        }
        findViewById<ImageButton>(R.id.imgBtnBack).setOnClickListener {
            onBackPressed() // Navigate back when the button is clicked
        }
        findViewById<TextView>(R.id.tvLogOut).setOnClickListener {
            formatterHelper.logOut(this)

            Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SignIn::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()

        }

        getUserDetails()

    }

    private fun getUserDetails() {

        val username = formatterHelper.retrieveSharedPreference(this, "username")
        val contact = formatterHelper.retrieveSharedPreference(this, "contact")

        tvUsername.text = username
        tvPhoneNumber.text = contact
    }


    private fun showBottomDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_delete)

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

    }

}