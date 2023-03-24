package com.example.navprayas

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.example.navprayas.models.Student
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
                    Log.d("Repos", "DocumentSnapshot data: ${document.data}")
                    val student = Student(
                        document.data?.get("name").toString(),
                        document.data?.get("email").toString(),
                        document.data?.get("Class").toString(),
                        document.data?.get("mobileNumber").toString(),
                        document.data?.get("address").toString(),
                        Uri.parse(document.data?.get("imageUri").toString())
                    )
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
}
