package com.example.navprayas.activity.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel: ViewModel() {
    private val _name = MutableLiveData("")
    val name : MutableLiveData<String> = _name

    private val _email = MutableLiveData("")
    val email : MutableLiveData<String> = _email

    fun setName(name: String) {
        _name.value = name
    }
}
