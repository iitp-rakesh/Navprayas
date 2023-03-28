package com.example.navprayas.activity.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navprayas.R
import com.example.navprayas.adapter.EventInfoAdapter
import com.example.navprayas.databinding.EventInfoBinding
import com.example.navprayas.databinding.FragmentEventInfoBinding

class EventInfoFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentEventInfoBinding
    private var eventName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEventInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView=binding.recyclerView
        viewModel.currentEventName.observe(viewLifecycleOwner){
            recyclerView.adapter=EventInfoAdapter(viewModel.eventDetails.value?.get(it)!!)
        }
        recyclerView.layoutManager=LinearLayoutManager(context)
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
