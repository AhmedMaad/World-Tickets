package com.maad.worldtickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maad.worldtickets.databinding.FragmentCalendarBinding
import com.maad.worldtickets.event.Event
import java.util.*

class CalendarFragment : Fragment() {

    private var modTenEvents = arrayListOf<Event>()
    private var modSevenEvents = arrayListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCalendarBinding.inflate(inflater, container, false)

        modTenEvents.add(
            Event(
                R.drawable.event_image_3,
                "VR World in Metaverse",
                "50$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Place parallel to the physical world",
                "Metaverser",
                "https://about.facebook.com/meta/",
                "Technology"
            )
        )
        modTenEvents.add(
            Event(
                R.drawable.event_image_6,
                "F1's Sprint events",
                "300$",
                "bring Formula 1's Sprint format to next year's event at the Baku City Circuit.",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "Azerbaijan Grand Prix organisers",
                "https://www.formula1.com/en/racing/2022.html",
                "Sports"
            )
        )
        modTenEvents.add(
            Event(
                R.drawable.event_image_2,
                "Sports Meet in Galaxy Field",
                "250$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Greenfields, Sector 42, Faridabad",
                "X Company Organizer",
                "https://www.eventpro.net/online-epconnect-suite.html",
                "Sports"
            )
        )
        modTenEvents.add(
            Event(
                R.drawable.event_image,
                "Art & Meet in Street Plaza",
                "250$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Greenfields, Sector 42, Faridabad",
                "Y Company Organizer",
                "https://www.planningpod.com/online-event-booking-software.cfm",
                "Arts"
            )
        )
        modSevenEvents.add(
            Event(
                R.drawable.event_image_4,
                "Manchester Graduates Gathering",
                "25$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "2022 Graduates",
                "https://www.healthcareconferencesuk.co.uk/virtual-online-courses/amat-clinical-audit-conference",
                "Music"
            )
        )
        modSevenEvents.add(
            Event(
                R.drawable.event_image_5,
                "BMW Lovers",
                "100$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Manchester, United Kingdom, Stretford, United Kingdom",
                "WWCC",
                "https://www.automech-formula.com/en/home.html",
                "Cars"
            )
        )


        val c = Calendar.getInstance()
        binding.calendar.init(
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ) { _, _, _, dayOfMonth ->
            when {
                dayOfMonth % 10 == 0 -> {
                    binding.dayRv.visibility = View.VISIBLE
                    binding.noEventsLayout.root.visibility = View.INVISIBLE
                    binding.dayRv.adapter = CalendarEventAdapter(requireActivity(), modTenEvents)
                }
                dayOfMonth % 7 == 0 -> {
                    binding.dayRv.visibility = View.VISIBLE
                    binding.noEventsLayout.root.visibility = View.INVISIBLE
                    binding.dayRv.adapter = CalendarEventAdapter(requireActivity(), modSevenEvents)
                }
                else -> {
                    binding.noEventsLayout.root.visibility = View.VISIBLE
                    binding.dayRv.visibility = View.INVISIBLE
                }
            }
        }

        return binding.root
    }

}