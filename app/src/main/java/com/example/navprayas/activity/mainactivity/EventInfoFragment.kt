package com.example.navprayas.activity.mainactivity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navprayas.R

class EventInfoFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_info, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.notification, menu)
        val faqMenuItem= menu.findItem(R.id.faq)
        faqMenuItem.isVisible=true
        faqMenuItem.setOnMenuItemClickListener {
            findNavController().navigate(R.id.FAQsFragment)
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
