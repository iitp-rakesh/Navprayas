package com.example.navprayas.activity.mainactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navprayas.R
import com.example.navprayas.adapter.NotificationAdapter
import com.example.navprayas.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private val viewModel : MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.notificationRecyclerview
        recyclerView.adapter = NotificationAdapter(viewModel.notificationList.value!!)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

    }
}
