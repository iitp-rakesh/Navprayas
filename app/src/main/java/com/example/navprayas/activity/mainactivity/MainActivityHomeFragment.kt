package com.example.navprayas.activity.mainactivity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.navprayas.R
import com.example.navprayas.adapter.CarouselAdapter
import com.example.navprayas.adapter.TopEventsAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import me.relex.circleindicator.CircleIndicator
import java.util.*

class MainActivityHomeFragment : Fragment() {
private val viewModel: MainActivityViewModel by activityViewModels()
    private val auth = Firebase.auth
    private lateinit var indicator: CircleIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setName(auth.currentUser?.displayName.toString())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = listOf(R.drawable.ic_baseline_call_24, R.drawable.np_logo,R.drawable.imag3,R.drawable.image2,R.drawable.image,R.drawable.image4)
        val adapter = CarouselAdapter(images, requireContext())
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = adapter
        indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
        indicator.setViewPager(viewPager)
        val handler = Handler(Looper.getMainLooper())
        val updateImageRunnable = Runnable {
            viewPager.currentItem = (viewPager.currentItem + 1) % images.size
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(updateImageRunnable)
            }
        }, 3000, 3000)


        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_events)
        recyclerView.adapter = TopEventsAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        val seeAll=view.findViewById<View>(R.id.seeAll)
        seeAll.setOnClickListener {
            val action=MainActivityHomeFragmentDirections.actionMainActivityHomeFragmentToEventFragment()
            view.findNavController().navigate(action)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Exit")
                builder.setMessage("Are you sure you want to exit?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    activity?.finish()
                }
                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}
