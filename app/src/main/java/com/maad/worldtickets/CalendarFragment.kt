package com.maad.worldtickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maad.worldtickets.databinding.FragmentCalendarBinding
import java.util.*

class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCalendarBinding.inflate(inflater, container, false)

        //binding.calendar.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->  }

        val c = Calendar.getInstance()
        binding.calendar.init(
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            if (dayOfMonth % 10 == 0){

            }
            else if (dayOfMonth % 7 == 0){

            }
        }

        return binding.root
    }

}