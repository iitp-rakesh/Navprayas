package com.example.navprayas.activity.mainactivity

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navprayas.FirebaseRepository
import com.example.navprayas.R
import com.example.navprayas.models.Notification
import com.example.navprayas.models.Student
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val firebaseRepository = FirebaseRepository()

    private val _announcementsImage: MutableLiveData<List<String>> = MutableLiveData()
    val announcementImages: LiveData<List<String>> = _announcementsImage

    private val _eventImage: MutableLiveData<List<String>> = MutableLiveData()
    val eventImages: LiveData<List<String>> = _eventImage

    private var _eventNameList: MutableList<String> = mutableListOf()
    val eventNameList: MutableList<String> = _eventNameList

    private var _student: MutableLiveData<Student> = MutableLiveData()
    var student: LiveData<Student> = _student

    private val _notificationList: MutableLiveData<List<Notification>> = MutableLiveData()
    val notificationList: LiveData<List<Notification>> = _notificationList

    init {
        Log.d("Repos", "_student.value: ${_student.value}")
//        _student.value = firebaseRepository.getUserDetails().value
//        firebaseRepository.getUserDetails(_student)
        firebaseRepository.getUser { student ->
            _student.value = student
            Log.d("Repos", "_student.value: ${_student.value}")
            firebaseRepository.getEventList(_eventNameList)
            _notificationList.value =
                listOf(Notification("Announcement This is a test notification", "x"))
            viewModelScope.launch {
                _announcementsImage.value = firebaseRepository.downloadImages("announcements").value
                _eventImage.value = firebaseRepository.downloadImages("events").value
            }

        }
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
        Log.d("Repos", "address:$address ${_student.value?.address}")
    }

    fun updateUser() {
        Log.d("repos", "1")
        firebaseRepository.addUser(_student.value!!)
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            _student.value?.image = firebaseRepository.uploadImage(uri)
            updateUser()
            Log.d("repos", "2")
        }
    }
}
