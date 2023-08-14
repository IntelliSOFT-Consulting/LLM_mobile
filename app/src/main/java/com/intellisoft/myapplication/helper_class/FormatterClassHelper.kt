package com.intellisoft.myapplication.helper_class

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.intellisoft.myapplication.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormatterClassHelper {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"



    fun validateEmail(emailAddress: String):Boolean{
        return emailAddress.matches(emailPattern.toRegex())
    }
    fun convertDdMMyyyy(date: String): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return formatter.parse(date)
    }
    fun getTodayDate(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val date = Date()
        return formatter.format(date)
    }

    fun saveSharedPreference(
        context: Context,
        sharedKey: String,
        sharedValue: String){

        val appName = context.getString(R.string.app_name)
        val sharedPreferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(sharedKey, sharedValue)
        editor.apply()
    }

    fun retrieveSharedPreference(
        context: Context,
        sharedKey: String): String? {

        val appName = context.getString(R.string.app_name)

        val sharedPreferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(sharedKey, null)

    }

    fun deleteSharedPreference(
        context: Context,
        sharedKey: String
    ){
        val appName = context.getString(R.string.app_name)
        val sharedPreferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove(sharedKey)
        editor.apply()

    }
    fun getHeaders(context: Context):HashMap<String, String>{

        val stringStringMap = HashMap<String, String>()

        val accessToken = retrieveSharedPreference(context, "token")

        stringStringMap["Authorization"] = " Bearer $accessToken"

        return stringStringMap
    }

    fun logOut(context: Context) {

        deleteSharedPreference(context, "token")
        deleteSharedPreference(context, "age")
        deleteSharedPreference(context, "gender")
        deleteSharedPreference(context, "username")
        deleteSharedPreference(context, "signUpDate")
        deleteSharedPreference(context, "contact")
        deleteSharedPreference(context, "isLoggedIn")

    }

}