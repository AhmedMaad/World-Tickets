package com.maad.worldtickets

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class SevenDaysCalendarAdapter(val activity: FragmentActivity, val days: ArrayList<DayModel>) :
    RecyclerView.Adapter<SevenDaysCalendarAdapter.DayVH>() {

    class DayVH(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.day_number_tv)
        val name: TextView = view.findViewById(R.id.day_name_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DayVH(
        activity.layoutInflater.inflate(
            R.layout.seven_days_calendar_list_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DayVH, position: Int) {
        holder.number.text = days[position].number
        holder.name.text = days[position].name
    }

    override fun getItemCount() = days.size


}