package com.intellisoft.myapplication.network_request.interfaces

import com.intellisoft.myapplication.data_class.DbSignUp
import com.intellisoft.myapplication.data_class.DbSignUpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {


    @POST("auth/sign-up/")
    suspend fun signUpUser(@Body dbSignUp: DbSignUp): Response<DbSignUpResponse>




}