package com.example.navprayas.activity.loginActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _user = MutableLiveData(auth.currentUser)
    val userLiveData: MutableLiveData<FirebaseUser?> = _user

    fun setUser(user: FirebaseUser?) {
        _user.value = user
    }
}
