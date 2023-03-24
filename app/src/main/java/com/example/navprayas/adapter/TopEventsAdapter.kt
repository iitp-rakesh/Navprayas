package com.example.navprayas.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navprayas.R

class TopEventsAdapter : RecyclerView.Adapter<TopEventsAdapter.TopEventsViewHolder>() {
    val imagelist = listOf(R.drawable.ic_baseline_call_24,R.drawable.ic_baseline_double_arrow_24)
    class TopEventsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageview: ImageView=view.findViewById(R.id.event_image)
        val title: TextView=view.findViewById(R.id.tv_event_name)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopEventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return TopEventsViewHolder(view)
    }
    override fun onBindViewHolder(holder: TopEventsViewHolder, position: Int) {
        val currentImage = imagelist[position]
        holder.imageview.setImageResource(currentImage)
    }
    override fun getItemCount(): Int {
        return imagelist.size
    }
}
