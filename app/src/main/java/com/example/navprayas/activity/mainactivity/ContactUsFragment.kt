package com.example.navprayas.activity.mainactivity

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

class ContactUsFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get references to the TextView and Button views
        val textView = view.findViewById<EditText>(R.id.textview)
        val editButton = view.findViewById<Button>(R.id.button)

// Disable editing initially
        textView.isEnabled = false

// Set click listener on the edit button
        editButton.setOnClickListener {
            // Enable editing when button is clicked
            textView.isEnabled = true
        }

// Optional: Set a focus change listener to disable editing when focus is lost
        textView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                textView.isEnabled = false
            }
        }
    }
}
