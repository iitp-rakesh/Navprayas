package com.example.navprayas.activity.mainactivity

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navprayas.FirebaseRepository
import com.example.navprayas.models.Student
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {
    private val auth= Firebase.auth
    private val firebaseRepository = FirebaseRepository()

    private val _student = MutableLiveData<Student>()
    val student: MutableLiveData<Student> = _student
    init {
        getUser()
    }
    fun setName(name: String) {
        _student.value?.name = name
    }

    fun setClass(clas: String) {
        _student.value?.clas = clas
    }

    fun setMobileNumber(mobNum: String) {
        _student.value?.mobileNumber = mobNum
    }

    fun setAdrress(address: String) {
        _student.value?.address = address
    }
    fun updateUser() {
        Log.d("repos","1")
        firebaseRepository.addUser(_student.value!!)
    }

    fun getUser() {
        firebaseRepository.getUser { _student.value = it }
        Log.d("Repos", "DocumentSnapshot data get: ${student.value}")
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            _student.value?.image = firebaseRepository.uploadImage(uri)
            updateUser()
            Log.d("repos","2")
        }
    }
}
