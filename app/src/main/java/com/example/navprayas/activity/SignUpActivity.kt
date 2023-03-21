package com.example.navprayas.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.navprayas.FirebaseService.FirebaseLoginSignup
import com.example.navprayas.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val Req_Code: Int = 123
    lateinit var firebaseAuth: FirebaseAuth
    var auth = Firebase.auth
    var passwordsame: Boolean = false
    private val fireBaseService = FirebaseLoginSignup(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}
