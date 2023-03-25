package com.example.navprayas.models

import android.net.Uri

data class Student(
    var name: String,
    var email: String,
    var clas: String,
    var mobileNumber:String,
    var address:String,
    var image: Uri?
)
