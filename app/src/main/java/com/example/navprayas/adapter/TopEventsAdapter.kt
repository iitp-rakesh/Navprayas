package com.example.navprayas.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navprayas.R
import com.example.navprayas.activity.mainactivity.MainActivityViewModel
import com.example.navprayas.utils.ImageUtils

class TopEventsAdapter(
    private val viewModel: MainActivityViewModel,
    private val imageList: List<String>,
    private val eventNameList: List<String>,
//    private val action: NavDirections
) : RecyclerView.Adapter<TopEventsAdapter.TopEventsViewHolder>() {
    class TopEventsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageview: ImageView = view.findViewById(R.id.event_image)
        val title: TextView = view.findViewById(R.id.tv_event_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopEventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return TopEventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopEventsViewHolder, position: Int) {
        val currentImage = imageList[position]
        val currentEventName = eventNameList[position]
        Glide.with(holder.imageview.context).load(currentImage).into(holder.imageview)
        holder.title.text = currentEventName
        holder.imageview.setOnClickListener {
            viewModel.currentEventName(currentEventName)
            holder.imageview.findNavController().navigate(R.id.eventInfoFragment)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}
