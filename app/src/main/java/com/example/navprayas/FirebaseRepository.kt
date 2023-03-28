package com.example.navprayas

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.example.navprayas.models.Student
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = Firebase.auth
    fun addUser(student: Student) {
        val user = hashMapOf(
            "name" to student.name,
            "Class" to student.clas,
            "email" to auth.currentUser?.email,
            "mobileNumber" to student.mobileNumber,
            "address" to student.address,
            "imageUri" to student.image
        )

        db.collection("users").document(auth.currentUser!!.uid).set(user)
            .addOnSuccessListener {
                Log.d(TAG, "User added")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user", e)
            }
    }

    fun getUser(callback: (Student?) -> Unit) {
        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Repos", "DocumentSnapshot data1: ${document.data}")
                    val student = Student(
                        document.data?.get("name").toString(),
                        document.data?.get("email").toString(),
                        document.data?.get("Class").toString(),
                        document.data?.get("mobileNumber").toString(),
                        document.data?.get("address").toString(),
                        Uri.parse(document.data?.get("imageUri").toString())
                    )
                    if (student.name == "null")
                        student.name = auth.currentUser?.displayName.toString()
                    if (student.name == "null")
                        student.name =
                            FirebaseAuth.getInstance().currentUser?.displayName.toString()
                    if (student.email == "null") {
                        student.email = auth.currentUser?.email.toString()
                        student.clas = ""
                        student.address = ""
                        student.mobileNumber= ""
                    }
                    Log.d("Repos", "Student: $student")
                    callback(student)
                } else {
                    Log.d("Repos", "No such document")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Repos", "get failed with ", exception)
                callback(null)
            }
    }

    private val storageRef = Firebase.storage.reference

    suspend fun uploadImage(uri: Uri): Uri {
        val imageName = auth.currentUser!!.uid
        val imageRef = storageRef.child("images/$imageName")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.await()
        Log.d("Repos", "Image uploaded: ${imageRef.downloadUrl}")
        return imageRef.downloadUrl.await()
    }

    private val storage = FirebaseStorage.getInstance().reference

    suspend fun downloadImages(child: String): MutableLiveData<List<String>> {
        val images: MutableLiveData<List<String>> = MutableLiveData(emptyList())
        try {
            val result = storage.child(child).listAll().await()
            result.items.forEach { imageRef ->
                val uri = imageRef.downloadUrl.await()
                images.value = images.value?.plus(uri.toString())
            }
        } catch (exception: Exception) {
            // Handle any errors that occur while downloading the images
        }
        Log.d("Repos", "Images: ${images.value?.size}")
        return images
    }

    fun getEventList(eventsList: MutableList<String>) {
        db.collection("events").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    eventsList.add(document["name"].toString())
                    Log.d("Repos", "doucment: ${document["name"]}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Repos", "Error getting documents.", exception)
            }
        Log.d("Repos", "Events: $eventsList")
    }
    fun getUserDetails():MutableLiveData<Student>{
        val student=MutableLiveData<Student>()
        db.collection("users").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("Repos", "DocumentSnapshot data1: ${document.data}")
                    student.value?.name = document.data?.get("name").toString()
                    student.value?.email = document.data?.get("email").toString()
                    student.value?.clas = document.data?.get("Class").toString()
                    student.value?.mobileNumber = document.data?.get("mobileNumber").toString()
                    student.value?.address = document.data?.get("address").toString()
                    student.value?.image = Uri.parse(document.data?.get("imageUri").toString())
                    if (student.value?.name == "null")
                        student.value?.name = auth.currentUser?.displayName.toString()
                    if (student.value?.name == "null")
                        student.value?.name =
                            FirebaseAuth.getInstance().currentUser?.displayName.toString()
                    if (student.value?.email == "null") {
                        student.value?.email = auth.currentUser?.email.toString()
                        student.value?.clas = ""
                        student.value?.address = ""
                        student.value?.mobileNumber = auth.currentUser?.phoneNumber.toString()
                    }
                    Log.d("Repos", "Student: $student")
                } else {
                    Log.d("Repos", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Repos", "get failed with ", exception)
            }
        Log.d("Repos", "Student: $student")
        return student
    }
}
