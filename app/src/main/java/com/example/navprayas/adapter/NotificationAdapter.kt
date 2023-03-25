package com.example.navprayas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navprayas.R
import com.example.navprayas.models.Notification

class NotificationAdapter(private val notificationList: List<Notification>): RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageview: ImageView=view.findViewById(R.id.notification_image)
        val notificationText: TextView=view.findViewById(R.id.notification_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
//        val currentItem = notificationList[position]
//        Glide.with(holder.imageview.context).load(currentItem.image).into(holder.imageview)
//        holder.notificationText.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return 10
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopEventsAdapter.TopEventsViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
//        return TopEventsAdapter.TopEventsViewHolder(view)
//    }
//    override fun onBindViewHolder(holder: TopEventsViewHolder, position: Int) {
//        val currentImage = imageList[position]
//        val currentEventName = eventNameList[position]
//        Glide.with(holder.imageview.context).load(currentImage).into(holder.imageview)
//        holder.title.text = currentEventName.toString()
//        holder.imageview.setOnClickListener {
//            holder.imageview.findNavController().navigate(action)
//        }
//    }
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
}
