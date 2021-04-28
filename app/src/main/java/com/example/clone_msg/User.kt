package com.example.clone_msg

data class User(val uid:String, val username:String, val email:String, val numero:String, val fotoUrl:String){
    constructor(): this("","","","","")
}