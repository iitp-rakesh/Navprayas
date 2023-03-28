@file:Suppress("DEPRECATION")

package com.example.navprayas.activity.mainactivity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.navprayas.R
import com.example.navprayas.adapter.CarouselAdapter
import com.example.navprayas.adapter.TopEventsAdapter
import com.example.navprayas.databinding.FragmentMainActivityHomeBinding
import me.relex.circleindicator.CircleIndicator
import java.util.*

class MainActivityHomeFragment : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private lateinit var binding: FragmentMainActivityHomeBinding
    private lateinit var indicator: CircleIndicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainActivityHomeBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.announcementImages.observe(viewLifecycleOwner) {
            binding.loader.visibility = View.GONE
            Log.d("Repos", "1${it.size}")
            val images = it
            val adapter = CarouselAdapter(images, requireContext())
            val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
            viewPager.adapter = adapter
            indicator = requireView().findViewById(R.id.indicator) as CircleIndicator
            indicator.setViewPager(viewPager)
            Log.d("Repos", "2${images.size}")
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
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_events)
        viewModel.eventImages.observe(viewLifecycleOwner) {
            recyclerView.adapter = TopEventsAdapter(
                it,
                viewModel.eventNameList,
                MainActivityHomeFragmentDirections.actionMainActivityHomeFragmentToEventInfoFragment()
            )
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        val seeAll = view.findViewById<View>(R.id.seeAll)
        seeAll.setOnClickListener {
            val action =
                MainActivityHomeFragmentDirections.actionMainActivityHomeFragmentToEventFragment()
            view.findNavController().navigate(action)
        }
        val profilePic = view.findViewById<ImageView>(R.id.profile_image)
        viewModel.student.observe(viewLifecycleOwner) {
            Log.d("Repos", "fs ${it.image}")
            if (it.image != null) {
                Glide.with(this).load(it.image).into(profilePic)
            }
        }
        profilePic.setOnClickListener {
            view.findNavController()
                .navigate(MainActivityHomeFragmentDirections.actionMainActivityHomeFragmentToProfileFragment())
        }
        val name = view.findViewById<TextView>(R.id.name)
        viewModel.student.observe(viewLifecycleOwner) {
            Log.d("Repos", "Main: $it")
            name.text = "Hii, ${it.name}"
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Exit")
                builder.setMessage("Are you sure you want to exit?")
                builder.setPositiveButton("Yes") { _, _ ->
                    activity?.finish()
                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onStart() {
        super.onStart()
        if (!isInternetConnected(requireContext())) {
            view?.findNavController()
                ?.navigate(MainActivityHomeFragmentDirections.actionMainActivityHomeFragmentToNoInternetFragment())
        }

    }

    private fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
