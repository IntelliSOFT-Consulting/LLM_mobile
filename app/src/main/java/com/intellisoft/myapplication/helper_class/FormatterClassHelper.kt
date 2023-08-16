package com.intellisoft.myapplication.helper_class

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.intellisoft.myapplication.R
import com.intellisoft.myapplication.data_class.DbUpdateMetadata
import com.intellisoft.myapplication.network_request.requests.RetrofitCallsAuthentication
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormatterClassHelper {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMetaData(context: Context) {

        val retrofitCallsAuthentication = RetrofitCallsAuthentication()

        val observedTimeLastUseString = getLocalTime()
        val observedTimeStartUseString = retrieveSharedPreference(context, "observedTimeStartUse")

        val searchSubject = retrieveSharedPreference(context, "searchSubject")

        if (observedTimeStartUseString != null && searchSubject != null){

            val duration =
                getDuration(observedTimeStartUseString, observedTimeLastUseString)

            val dbUpdateMetadata = DbUpdateMetadata(
                searchSubject,
                observedTimeStartUseString,
                observedTimeLastUseString,
                duration)
            retrofitCallsAuthentication.updateMetadata(context, dbUpdateMetadata)

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDuration(startUseTime: String, lastUseTime:String):String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timestamp1 = LocalDateTime.parse(lastUseTime, formatter)
        val timestamp2 = LocalDateTime.parse(startUseTime, formatter)

        val duration = Duration.between(timestamp2, timestamp1)

        val days = duration.toDays()
        val hours = duration.toHours()
        val minutes = duration.toMinutes()

        return "$hours hour $minutes minutes"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalTime(): String {
        val zoneId = ZoneId.of("Africa/Nairobi")
        val now = ZonedDateTime.now(zoneId)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return now.format(formatter)
    }

    fun getGreetingByTime(context: Context): String {
        val calendar = Calendar.getInstance()

        var name = ""
        val fullName = retrieveSharedPreference(context, "fullName")
        val userName = retrieveSharedPreference(context, "username")
        if (fullName != null && fullName != ""){
            name = fullName
        }else if (userName != null && userName != ""){
            name = userName
        }else{
            ""
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

        val appName = context.getString(R.string.app_name)
        val sharedPreferences = context.getSharedPreferences(appName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()


    }

}