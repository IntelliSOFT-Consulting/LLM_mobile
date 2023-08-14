package com.intellisoft.myapplication.network_request.interfaces

import com.intellisoft.myapplication.data_class.DbSignIn
import com.intellisoft.myapplication.data_class.DbSignUp
import com.intellisoft.myapplication.data_class.DbSignUpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {


    @POST("api/authentication/register")
    suspend fun signUpUser(@Body dbSignUp: DbSignUp): Response<DbSignUpResponse>


    @POST("api/authentication/login")
    suspend fun signInUser(
        @Header("Authorization") token: String, // Add this line to pass the Bearer Token
        @Body dbSignIn: DbSignIn):
            Response<DbSignUpResponse>




}