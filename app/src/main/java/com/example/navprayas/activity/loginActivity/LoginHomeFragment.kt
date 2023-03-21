package com.example.navprayas.activity.loginActivity

import android.content.Intent
import android.os.Bundle
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

class LoginHomeFragment : Fragment() {
    private val sharedViewModel: LoginViewModel by activityViewModels()
    private lateinit var fireBaseService: FirebaseLoginSignup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = view.findViewById<TextInputEditText>(R.id.et_email_address)
        val password = view.findViewById<TextInputEditText>(R.id.et_password)
        val signIn = view.findViewById<MaterialButton>(R.id.btn_sign_in)
        val googleSignIn = view.findViewById<SignInButton>(R.id.btn_sign_in_with_google)
        val signUp = view.findViewById<TextView>(R.id.tv_sign_up)
        fireBaseService = FirebaseLoginSignup(view.context)
        signUp.setOnClickListener {
            val action = LoginHomeFragmentDirections.actionLoginHomeFragmentToSignupFragment()
            view.findNavController().navigate(action)
        }
        signIn.setOnClickListener {
            if (editTextError(email) && editTextError(password))
                fireBaseService.login(email.text.toString(), password.text.toString())
        }
        googleSignIn.setOnClickListener {
            Toast.makeText(context, "Logging In", Toast.LENGTH_SHORT).show()
            fireBaseService.signInWithGoogle(this)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FirebaseLoginSignup.RC_SIGN_IN) {
            fireBaseService.handleGoogleSignInResult(data)
        }
    }

    fun editTextError(itemView: TextInputEditText): Boolean {
        if (itemView.text.isNullOrEmpty()) {
            itemView.error = "Required Field"
            return false
        }
        return true
    }
}
