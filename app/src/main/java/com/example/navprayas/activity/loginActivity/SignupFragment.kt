package com.example.navprayas.activity.loginActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.navprayas.FirebaseService.FirebaseLoginSignup
import com.example.navprayas.R
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {
    private val sharedViewModel: LoginViewModel by activityViewModels()
    private lateinit var fireBaseService : FirebaseLoginSignup
    lateinit var firebaseAuth: FirebaseAuth
    var passwordsame: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_f_ragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = view.findViewById<TextInputEditText>(R.id.et_email_address)
        val password = view.findViewById<TextInputEditText>(R.id.et_password)
        val confirmPassword = view.findViewById<TextInputEditText>(R.id.et_confirm_password)
        val signup = view.findViewById<MaterialButton>(R.id.btn_sign_up)
        val signInWithGoogle = view.findViewById<SignInButton>(R.id.btn_sign_in_with_google)
        val signIn = view.findViewById<TextView>(R.id.tv_login)
        fireBaseService= FirebaseLoginSignup(view.context)
        signIn.setOnClickListener {
            val action= SignupFragmentDirections.actionSignupFragmentToLoginHomeFragment()
            view.findNavController().navigate(action)
        }
        firebaseAuth = FirebaseAuth.getInstance()
        signup.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.error = "Email is required"
            }
            else if(password.text.toString().length<6){
                password.error="Password must be greater than 6 characters"
            }
            else if(password.text.toString()==confirmPassword.text.toString()) {
                fireBaseService.signup(email.text.toString(), password.text.toString())
            }
            else{
                confirmPassword.error="Password does not match"
            }
        }

        signInWithGoogle.setOnClickListener {
            Toast.makeText(context, "Logging In", Toast.LENGTH_SHORT).show()
            fireBaseService.signInWithGoogle(this)
        }

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                if (s!!.isEmpty()) {
                    confirmPassword.error = null
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != password.text.toString()) {
                    confirmPassword.error = "Password does not match"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == password.text.toString()) {
                    passwordsame = true
                    confirmPassword.error = null
                }
            }
        })
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FirebaseLoginSignup.RC_SIGN_IN) {
            fireBaseService.handleGoogleSignInResult(data)
        }
    }
}
