package com.maad.worldtickets.event

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.maad.worldtickets.R

class EventAdapter(val activity: Activity, val event: Event) :
    RecyclerView.Adapter<EventAdapter.EventVH>() {

    class EventVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val name: TextView = view.findViewById(R.id.name_tv)
        val description: TextView = view.findViewById(R.id.description_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val price: TextView = view.findViewById(R.id.price_tv)
        val image: ImageView = view.findViewById(R.id.event_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventVH(activity.layoutInflater.inflate(R.layout.event_list_item, parent, false))

    override fun onBindViewHolder(holder: EventVH, position: Int) {
        holder.image.setImageResource(event.image)
        holder.name.text = event.title
        holder.description.text = event.description
        holder.location.text = event.location
        holder.price.text = event.price
        holder.parent.setOnClickListener {
            val i = Intent(activity, EventDetailsActivity::class.java)
            i.putExtra("event", event)
            activity.startActivity(i)
        }
    }

    override fun getItemCount() = 1

}