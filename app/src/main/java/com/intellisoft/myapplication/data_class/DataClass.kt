package com.intellisoft.myapplication.data_class

import com.intellisoft.myapplication.R

enum class UrlData(var message: Int) {
    BASE_URL(R.string.base_url),
}

data class DbSignIn(
    val username:String,
    val password:String,
)

data class DbSignUp(
    var uniqueID:String,
    var age:Int,
    var gender:String,
    var phoneNumber:String,
    var signUpDate:String,
    var heardAppFrom:String,
    var username:String, //this is the email address
    var password:String,
    var roles:List<DbRoles>,
)
data class DbRoles(
    val name:String
)

data class DbSignUpResponse(
    val id: Int,
    val uniqueId: String,
    val age: Int,
    val gender: String,
    val contact: String,
    val heardAppFrom: String,
    val username: String,
    val password: String,
    val signUpDate: String,
    val token: String,
    val roles: List<Role>,
    val enabled: Boolean,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val authorities: List<Authority>
)

data class Role(
    val id: Int,
    val name: String
)

data class Authority(
    val authority: String
)