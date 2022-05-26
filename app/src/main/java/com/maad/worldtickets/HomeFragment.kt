package com.maad.worldtickets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentHomeBinding
import com.maad.worldtickets.event.Event
import com.maad.worldtickets.event.EventAdapter
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), SevenDaysCalendarAdapter.ItemClickListener {

    private lateinit var db: FirebaseFirestore
    private var days = arrayListOf<DayModel>()
    private lateinit var sevenDaysAdapter: SevenDaysCalendarAdapter
    private lateinit var binding: FragmentHomeBinding
    private var events = arrayListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        events.add(
            Event(
                R.drawable.event_image_2,
                "Sports Meet in Galaxy Field",
                "250$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Greenfields, Sector 42, Faridabad",
                "X Company Organizer",
                "https://www.eventpro.net/online-epconnect-suite.html"
            )
        )
        events.add(
            Event(
                R.drawable.event_image,
                "Art & Meet in Street Plaza",
                "250$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Greenfields, Sector 42, Faridabad",
                "Y Company Organizer",
                "https://www.planningpod.com/online-event-booking-software.cfm"
            )
        )
        events.add(
            Event(
                R.drawable.event_image_3,
                "VR World in Metaverse",
                "50$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Place parallel to the physical world",
                "Metaverser",
                "https://about.facebook.com/meta/"
            )
        )
        events.add(
            Event(
                R.drawable.event_image_4,
                "Manchester Graduates Gathering",
                "25$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "2022 Graduates",
                "https://www.healthcareconferencesuk.co.uk/virtual-online-courses/amat-clinical-audit-conference"
            )
        )
        events.add(
            Event(
                R.drawable.event_image_5,
                "BMW Lovers",
                "100$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "WWCC",
                "https://www.automech-formula.com/en/home.html"
            )
        )
        events.add(
            Event(
                R.drawable.event_image_6,
                "F1's Sprint events",
                "300$",
                "bring Formula 1's Sprint format to next year's event at the Baku City Circuit.",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "Azerbaijan Grand Prix organisers",
                "https://www.formula1.com/en/racing/2022.html"
            )
        )


        binding.profileIv.setOnClickListener {
            val i = Intent(context, ProfileActivity::class.java)
            startActivity(i)
        }

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val id = prefs.getString("id", null)!!

        db.collection("users").document(id).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            if (user.pp.isEmpty())
                binding.profileIv.setImageResource(R.drawable.ic_person)
            else
                Glide.with(requireActivity()).load(user.pp).into(binding.profileIv)

            val parts = user.name.split(" ").toMutableList()
            val firstName = parts.firstOrNull()
            binding.nameTv.text = "Hello, $firstName !"
            binding.exploreTv.visibility = View.VISIBLE

            for (i in 0..6)
                getCalculatedDate(i)
            sevenDaysAdapter = SevenDaysCalendarAdapter(requireActivity(), days, this)
            binding.calendarSevenRv.adapter = sevenDaysAdapter

            binding.progress.visibility = View.GONE
        }

        return binding.root
    }

    private fun getCalculatedDate(next: Int) {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat("dd-MM-yyyy")
        cal.add(Calendar.DAY_OF_YEAR, next)
        //Log.d("trace", "All: ${s.format(cal.time)}")
        val formattedDate = s.format(cal.time)
        val parts = formattedDate.split("-").toMutableList()
        val day = parts.first()
        //Log.d("trace", day!!)
        //val dayOfWeek = cal[Calendar.DAY_OF_WEEK]
        //Log.d("trace", "$dayOfWeek")
        val dayName = SimpleDateFormat("EE").format(cal.time)
        //Log.d("trace", "$dayName")
        days.add(DayModel(day, dayName, formattedDate))
    }

    override fun onItemClick(position: Int) {
        //Log.d("trace", "Item Clicked from fragment")

        sevenDaysAdapter.sendPosition(position)
        sevenDaysAdapter.notifyDataSetChanged()

        val eventAdapter = EventAdapter(requireActivity(), events)
        binding.eventRv.adapter = eventAdapter
    }

}