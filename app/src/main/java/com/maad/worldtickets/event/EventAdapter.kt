package com.maad.worldtickets.event

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.maad.worldtickets.R

class EventAdapter(val activity: Activity, val events: ArrayList<Event>) :
    RecyclerView.Adapter<EventAdapter.EventVH>() {

    class EventVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val name: TextView = view.findViewById(R.id.name_tv)
        val description: TextView = view.findViewById(R.id.description_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val price: TextView = view.findViewById(R.id.price_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventVH(activity.layoutInflater.inflate(R.layout.event_list_item, parent, false))

    override fun onBindViewHolder(holder: EventVH, position: Int) {
        holder.name.text = events[position].title
        holder.description.text = events[position].description
        holder.location.text = events[position].location
        holder.price.text = events[position].price
        holder.parent.setOnClickListener {
            val i = Intent(activity, EventDetailsActivity::class.java)
            i.putExtra("event", events[position])
            activity.startActivity(i)
        }
    }

    override fun getItemCount() = events.size

}