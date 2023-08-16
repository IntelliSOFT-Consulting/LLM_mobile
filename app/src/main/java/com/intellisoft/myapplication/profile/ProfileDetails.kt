package com.intellisoft.myapplication.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            completeLogOut()

        }

        findViewById<LinearLayout>(R.id.linearPersonalInfo).setOnClickListener {
            val intent = Intent(this, ProfileEdit::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.linearLocation).setOnClickListener {
            val intent = Intent(this, Location::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.linearSecurity).setOnClickListener {
            val intent = Intent(this, Security::class.java)
            startActivity(intent)
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

        val etDeleteReason = dialog.findViewById<EditText>(R.id.etDeleteReason)
        val btnDeleteContinue = dialog.findViewById<Button>(R.id.btnDeleteContinue)

        val imgBtnCancel = dialog.findViewById<ImageButton>(R.id.imgBtnCancel)
        imgBtnCancel.setOnClickListener { dialog.dismiss() }

        btnDeleteContinue.setOnClickListener {

            val deleteReason = etDeleteReason.text.toString()
            if (!TextUtils.isEmpty(deleteReason)){
                dialog.dismiss()
                showBottomDialogConfirm()
            }else{
                etDeleteReason.error = "Fill this field"
            }

        }


        showDialogDetails(dialog)

    }

    private fun showBottomDialogConfirm() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet_confirm_delete)

        val imgBtnCancel = dialog.findViewById<ImageButton>(R.id.imgBtnCancel)
        imgBtnCancel.setOnClickListener { dialog.dismiss() }

        val etPassword = dialog.findViewById<EditText>(R.id.etPassword)
        val btnConfirmDelete = dialog.findViewById<Button>(R.id.btnConfirmDelete)

        btnConfirmDelete.setOnClickListener {

            val password = etPassword.text.toString()
            if (!TextUtils.isEmpty(password)){
                dialog.dismiss()

                completeLogOut()

            }else{
                etPassword.error = "Fill this field"
            }

        }


        showDialogDetails(dialog)


    }

    private fun showDialogDetails(dialog: Dialog){
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }

    private fun completeLogOut(){
        formatterHelper.logOut(this)

        Toast.makeText(this, "You have been logged out.", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

}