package com.intellisoft.myapplication.network_request.requests

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.intellisoft.myapplication.data_class.DbSignIn
import com.intellisoft.myapplication.data_class.DbSignUp
import com.intellisoft.myapplication.data_class.UrlData
import com.intellisoft.myapplication.helper_class.FormatterClassHelper
import com.intellisoft.myapplication.network_request.builder.RetrofitBuilder
import com.intellisoft.myapplication.network_request.interfaces.Interface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class RetrofitCallsAuthentication {

    fun registerUser(context: Context, dbSignUp: DbSignUp){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {
                startRegister(context, dbSignUp)
            }.join()
        }

    }
    private suspend fun startRegister(context: Context, dbSignUp: DbSignUp) {


        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Authentication in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val formatter = FormatterClassHelper()
                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                try {

                    val apiInterface = apiService.signUpUser(dbSignUp)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){

                            if (body != null){

                                val token = body.token

                                formatter.saveSharedPreference(context, "token", token)
//                                formatter.saveSharedPreference(context, "expires", expires)

                                formatter.deleteSharedPreference(context, "dbSignUp")
                            }else{
                                messageToast = "Error: Body is null"
                            }
                        }else{
                            messageToast = "Error: The request was not successful"
                        }
                    }else{
                        apiInterface.errorBody()?.let {
                            val errorBody = JSONObject(it.string())
                            messageToast = errorBody.getString("message")
                        }
                    }


                }catch (e: Exception){

                    messageToast = "There was an issue with the server"
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

    }

    fun loginUser(context: Context, dbSignIn: DbSignIn){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {
                starLogin(context, dbSignIn)
            }.join()
        }

    }
    private suspend fun starLogin(context: Context, dbSignIn: DbSignIn) {


        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Authentication in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val formatter = FormatterClassHelper()
                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                try {

                    val apiInterface = apiService.signInUser(dbSignIn)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){

                            if (body != null){

                                val token = body.token

                                formatter.saveSharedPreference(context, "token", token)
//                                formatter.saveSharedPreference(context, "expires", expires)

                            }else{
                                messageToast = "Error: Body is null"
                            }
                        }else{
                            messageToast = "Error: The request was not successful"
                        }
                    }else{
                        apiInterface.errorBody()?.let {
                            val errorBody = JSONObject(it.string())
                            messageToast = errorBody.getString("message")
                        }
                    }


                }catch (e: Exception){

                    messageToast = "There was an issue with the server"
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

    }

//    private suspend fun getUserData(context: Context) {
//
//        var messageToast = ""
//        val formatter = FormatterClass()
//        val stringStringMap = formatter.getHeaders(context)
//
//        val baseUrl = context.getString(UrlData.BASE_URL.message)
//
//        val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
//        try {
//
//            val apiInterface = apiService.getUserData(stringStringMap)
//
//            if (apiInterface.isSuccessful){
//                val statusCode = apiInterface.code()
//                val body = apiInterface.body()
//
//                if (statusCode == 200 || statusCode == 201){
//
//                    if (body != null){
//
//                        messageToast = "Login successful"
//
//                        val data = body.data
//                        val id = data.id
//                        val names = data.names
//                        val email = data.email
//                        val role = data.role
//
//                        val kmhflCode = data.kmhflCode
//                        val facilityName = data.facilityName
//
//                        formatter.saveSharedPreference(context, "id", id)
//                        formatter.saveSharedPreference(context, "USERID", id)
//                        formatter.saveSharedPreference(context, "names", names)
//                        formatter.saveSharedPreference(context, "email", email)
//                        formatter.saveSharedPreference(context, "role", role)
//
//                        if (kmhflCode != null) formatter.saveSharedPreference(context, "kmhflCode", kmhflCode)
//                        if (facilityName != null) formatter.saveSharedPreference(context, "facilityName", facilityName)
//
//                        FhirApplication.setLoggedIn(context, true)
//
//                        when (role) {
//                            "CHW" -> {
//                                val intent = Intent(context, PatientList::class.java)
//                                context.startActivity(intent)
//                            }
//                            "CLINICIAN" -> {
//                                val intent = Intent(context, NewMainActivity::class.java)
//                                context.startActivity(intent)
//                            }
//                            "NURSE" -> {
//                                val intent = Intent(context, NewMainActivity::class.java)
//                                context.startActivity(intent)
//                            }
//                            else -> {
//                                FhirApplication.setLoggedIn(context, false)
//                                messageToast = "Error: Role not recognized"
//                            }
//                        }
//
//                    }else{
//                        messageToast = "Error: Body is null"
//                    }
//
//                }else{
//
//                    messageToast = if (statusCode == 401){
//                        "Error: Invalid credentials"
//                    }else{
//                        "Error: Status code is $statusCode"
//                    }
//                }
//            }else{
//                apiInterface.errorBody()?.let {
//                    val errorBody = JSONObject(it.string())
//                    messageToast = errorBody.getString("message")
//                }
//
//            }
//
//        }catch (e: Exception){
//
//            messageToast = "There was an issue with the server"
//        }
//
//        CoroutineScope(Dispatchers.Main).launch{
//
//                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()
//
//        }
//
//
//    }


}

