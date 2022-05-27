package com.maad.worldtickets

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.maad.worldtickets.R
import com.maad.worldtickets.event.Event
import com.maad.worldtickets.event.EventDetailsActivity

class CalendarEventAdapter(val activity: Activity, val events: ArrayList<Event>) :
    RecyclerView.Adapter<CalendarEventAdapter.EventVH>() {

    class EventVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val name: TextView = view.findViewById(R.id.name_tv)
        val location: TextView = view.findViewById(R.id.location_tv)
        val image: ImageView = view.findViewById(R.id.event_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventVH(activity.layoutInflater.inflate(R.layout.calendar_event_list_item, parent, false))

    override fun onBindViewHolder(holder: EventVH, position: Int) {
        holder.image.setImageResource(events[position].image)
        holder.name.text = events[position].title
        holder.location.text = events[position].location
        holder.parent.setOnClickListener {
            val i = Intent(activity, EventDetailsActivity::class.java)
            i.putExtra("event", events[position])
            activity.startActivity(i)
        }
    }

    override fun getItemCount() = events.size

}