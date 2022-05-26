package com.maad.worldtickets

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class SevenDaysCalendarAdapter(
    val activity: FragmentActivity,
    val days: ArrayList<DayModel>,
    val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<SevenDaysCalendarAdapter.DayVH>() {

    private var clickedIndex = -1

    interface ItemClickListener {
        fun onItemClick(position: Int, parent: CardView, number: TextView, name: TextView)
    }

    inner class DayVH(view: View) : RecyclerView.ViewHolder(view) {
        val parent: CardView = view.findViewById(R.id.parent)
        val number: TextView = view.findViewById(R.id.day_number_tv)
        val name: TextView = view.findViewById(R.id.day_name_tv)

        init {
            view.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition, parent, number, name)
            }
        }

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

        holder.parent.setOnClickListener {
            clickedIndex = holder.adapterPosition
            notifyDataSetChanged()
        }

        if (holder.adapterPosition == clickedIndex){
            holder.number.setTextColor(ContextCompat.getColor(activity, R.color.dark_blue))
            holder.name.setTextColor(ContextCompat.getColor(activity, R.color.dark_blue))
            holder.parent.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.gold))
        }
        else{
            holder.number.setTextColor(ContextCompat.getColor(activity, R.color.white))
            holder.name.setTextColor(ContextCompat.getColor(activity, R.color.white))
            holder.parent.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.dark_blue))
        }

    }

    override fun getItemCount() = days.size


}