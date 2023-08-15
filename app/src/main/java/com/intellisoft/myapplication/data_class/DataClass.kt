package com.intellisoft.myapplication.data_class

import com.intellisoft.myapplication.R

enum class UrlData(var message: Int) {
    BASE_URL(R.string.base_url),
}

data class DbLLMResponse(
    val choices:List<DbChoices>
)
data class DbChoices(
    val message: DbMessage
)
data class DbMessage(
    val role: String,
    val content: String
)

data class DbLLM(
//    val phoneNumber:String,
//    val searchSubject:String,
    val messages : List<DbMessages>
)
data class DbMessages(
    val role:String,
    val content:String,
)
data class DbNcd(
    val imageSource: Int,
    val imageName:String
)
data class DbChat(
    val username: String,
    val chat: String
)

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