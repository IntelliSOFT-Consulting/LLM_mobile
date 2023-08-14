package com.intellisoft.myapplication.helper_class

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.intellisoft.myapplication.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormatterClassHelper {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    fun getGreetingByTime(context: Context): String {
        val calendar = Calendar.getInstance()

        var name = ""
        val userName = retrieveSharedPreference(context, "username")
        if (userName != null){
            name = userName
        }

        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good morning $name"
            in 12..16 -> "Good afternoon $name"
            in 17..20 -> "Good evening $name"
            else -> "Good night $name"
        }
    }

    fun validateEmail(emailAddress: String):Boolean{
        return emailAddress.matches(emailPattern.toRegex())
    }
    fun getDate(): String {
        val formatter = SimpleDateFormat("E, d MMM yyyy")
        val date = Date()
        return formatter.format(date)
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