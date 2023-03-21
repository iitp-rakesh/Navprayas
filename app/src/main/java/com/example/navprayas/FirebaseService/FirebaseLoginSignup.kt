package com.example.navprayas.FirebaseService

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.navprayas.R
import com.example.navprayas.activity.loginActivity.LoginViewModel
import com.example.navprayas.activity.MainActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseLoginSignup(private val context: Context) {
    var auth = Firebase.auth
    private var TAG: String = "Firebase"

    // Firebase Authentication instance
    private val mAuth = FirebaseAuth.getInstance()
    private var mGoogleApiClient: GoogleApiClient? = null
    private var loginViewModel: LoginViewModel? = null
    // Google Sign-In client
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    if (!auth.currentUser!!.isEmailVerified) {
                        Toast.makeText(
                            context, "Email not verified",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnCompleteListener
                    }
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(context, MainActivity::class.java)
                    loginViewModel?.setUser(user)
                    startActivity(context, intent, null)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Incorrect Credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signup(email: String, password: String) {
        Log.w(TAG, email + " " + password)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    auth.currentUser?.sendEmailVerification()
                    Toast.makeText(
                        context, "Verification Email Sent",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }

    fun signInWithGoogle(fragment: Fragment) {
        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Create a GoogleApiClient instance
        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        // Start the Google Sign-In intent
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient!!)
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Handle the Google Sign-In result in the onActivityResult() method of the calling activity
    fun handleGoogleSignInResult(data: Intent?) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
        if (result!!.isSuccess) {
            // Google Sign-In was successful, authenticate with Firebase
            val account = result.signInAccount
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Firebase authentication was successful
                        val user = mAuth.currentUser
                        loginViewModel?.setUser(user)
                        startActivity(context, Intent(context, MainActivity::class.java), null)
                    } else {
                        // Firebase authentication failed
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
        } else {
            // Google Sign-In failed
            Log.w(TAG, "Google sign in failed")
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
        private const val TAG = "FirebaseService"
    }
}
