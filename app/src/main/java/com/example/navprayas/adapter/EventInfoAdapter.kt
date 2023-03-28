package com.example.navprayas.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navprayas.R
import com.example.navprayas.adapter.EventInfoAdapter.EventInfoViewHolder

class EventInfoAdapter(val data: List<List<String>>) : RecyclerView.Adapter<EventInfoViewHolder>() {
    class EventInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val body: TextView = view.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_info, parent, false)
        return EventInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventInfoViewHolder, position: Int) {
        val currentData=data[position]
        holder.title.text=currentData[0]
        val stringBuilder=StringBuilder()
        for (i in 1 until currentData.size){
            stringBuilder.append(currentData[i]).append("\n")
        }
        holder.body.text=stringBuilder.toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
