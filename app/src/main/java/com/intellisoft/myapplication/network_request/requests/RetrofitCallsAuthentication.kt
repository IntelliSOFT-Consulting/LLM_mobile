package com.intellisoft.myapplication.network_request.requests

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.intellisoft.myapplication.landing_page.MainActivity
import com.intellisoft.myapplication.auth.SignIn
import com.intellisoft.myapplication.data_class.DbLLM
import com.intellisoft.myapplication.data_class.DbProfile
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
import kotlinx.coroutines.runBlocking
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

                                messageToast = "Registration was successful.."

                                formatter.saveSharedPreference(context, "registrationToken", token)
                                val intent = Intent(context, SignIn::class.java)
                                context.startActivity(intent)

                                formatter.deleteSharedPreference(context, "dbSignUp")
                            }else{
                                messageToast = "Error: Body is null"
                            }
                        }else{
                            messageToast = "Error: The request was not successful"
                        }
                    }else{
                        val statusCode = apiInterface.code()
                        if (statusCode == 401){
                            messageToast = "Authentication failed. Try again."
                        }

                    }


                }catch (e: Exception){

                    e.printStackTrace()



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

                    val registrationToken = formatter.retrieveSharedPreference(context, "registrationToken")
                    var token = ""
                    if (registrationToken != null){
                        token = registrationToken
                    }

                    val apiInterface = apiService.signInUser(token, dbSignIn)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){

                            if (body != null){

                                val tokenLogin = body.token
                                val age = body.age
                                val gender = body.gender
                                val username = body.username
                                val signUpDate = body.signUpDate
                                val contact = body.contact
                                val heardAppFrom = body.heardAppFrom

                                formatter.saveSharedPreference(context, "token", tokenLogin)
                                formatter.saveSharedPreference(context, "age", age.toString())
                                formatter.saveSharedPreference(context, "gender", gender)
                                formatter.saveSharedPreference(context, "username", username)
                                formatter.saveSharedPreference(context, "signUpDate", signUpDate)
                                formatter.saveSharedPreference(context, "contact", contact)
                                formatter.saveSharedPreference(context, "heardAppFrom", heardAppFrom)
                                formatter.saveSharedPreference(context, "isLoggedIn", "true")

                                messageToast = "Login successful.."

                                val intent = Intent(context, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                context.startActivity(intent)
                                if (context is Activity) {
                                    context.finish()
                                }

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

                    Log.e("******","")
                    Log.e("******",e.toString())
                    Log.e("******","")


                    messageToast = "Cannot login user.."
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

    }


    fun searchLLm(context: Context, dbLLM: DbLLM) = runBlocking{
        getLLM(context, dbLLM)
    }
    private suspend fun getLLM(context: Context, dbLLM: DbLLM): String?{
        val formatter = FormatterClassHelper()
        val baseUrl = context.getString(UrlData.BASE_URL.message)
        val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
        try {

            val registrationToken = formatter.retrieveSharedPreference(context, "token")
            var token = ""
            if (registrationToken != null){
                token = registrationToken
            }

            val apiInterface = apiService.requestLLMChat(token, dbLLM)
            if (apiInterface.isSuccessful){
                val statusCode = apiInterface.code()
                val body = apiInterface.body()
                if (statusCode == 200 && body !=null){
                    val choices = body.choices
                    for (choice in choices) {
                        return choice.message.content
                    }
                }
            }

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        return null
    }

    fun updateUserData(context: Context, dbProfile: DbProfile){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {
                starUserUpdate(context, dbProfile)
            }.join()
        }

    }
    private suspend fun starUserUpdate(context: Context, dbProfile: DbProfile) {


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

                    val registrationToken = formatter.retrieveSharedPreference(context, "token")
                    var token = ""
                    if (registrationToken != null){
                        token = registrationToken
                    }

                    val apiInterface = apiService.updateUserData(token, dbProfile)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){
                            messageToast = "Update is successful.."
                        }else{
                            messageToast = "Error: The request was not successful"
                        }
                    }else{
                        val statusCode = apiInterface.code()
                        if (statusCode == 401){
                            messageToast = "Authentication failed. Try again."
                        }

                    }


                }catch (e: Exception){

                    e.printStackTrace()
                    messageToast = "There was an issue with the server"
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

    }


    fun passwordChange(context: Context, password: String, username: String){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {
                startPasswordChange(context, password, username)
            }.join()
        }

    }
    private suspend fun startPasswordChange(context: Context, password: String, username: String) {


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

                    val registrationToken = formatter.retrieveSharedPreference(context, "token")
                    var token = ""
                    if (registrationToken != null){
                        token = registrationToken
                    }

                    val apiInterface = apiService.resetPassword(token, username, password)
                    if (apiInterface.isSuccessful){

                        val statusCode = apiInterface.code()
                        val body = apiInterface.body()

                        if (statusCode == 200 || statusCode == 201){
                            messageToast = "Update is successful.."
                        }else{
                            messageToast = "Error: The request was not successful"
                        }
                    }else{
                        val statusCode = apiInterface.code()
                        if (statusCode == 401){
                            messageToast = "Authentication failed. Try again."
                        }

                    }


                }catch (e: Exception){

                    e.printStackTrace()
                    messageToast = "There was an issue with the server"
                }


            }.join()
            CoroutineScope(Dispatchers.Main).launch{

                progressDialog.dismiss()
                Toast.makeText(context, messageToast, Toast.LENGTH_LONG).show()

            }

        }

    }




}

