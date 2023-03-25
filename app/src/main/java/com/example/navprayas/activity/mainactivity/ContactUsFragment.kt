package com.example.navprayas.activity.mainactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.navprayas.R
import com.example.navprayas.databinding.FragmentContactUsBinding

class ContactUsFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentContactUsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactUsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val facebookUrl = "https://www.facebook.com/navprayas.np/"
        val linkedInUrl = "https://www.linkedin.com/company/navprayas/"
        val instagramUrl = "https://www.instagram.com/navprayas.np/"

// Set click listeners for each button
        binding.lottieFacebook.setOnClickListener {
            openLink(facebookUrl, "com.facebook.katana")
        }
        binding.lottieLinkedin.setOnClickListener {
            openLink(linkedInUrl, "com.linkedin.android")
        }

        binding.lottieInstagram.setOnClickListener {
            openLink(instagramUrl, "com.instagram.android")
        }
    }

    private fun openLink(url: String, pack: String) {
        val packageManager = context?.packageManager
        try {
            // First try to launch the Instagram app
            val intent = packageManager?.getLaunchIntentForPackage(pack)
            startActivity(intent)
        } catch (e: Exception) {
            // If the Instagram app is not installed, launch the website
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}
