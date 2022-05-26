package com.maad.worldtickets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(), SevenDaysCalendarAdapter.ItemClickListener {

    private lateinit var db: FirebaseFirestore
    private var days = arrayListOf<DayModel>()
    private lateinit var sevenDaysAdapter: SevenDaysCalendarAdapter
    //private var clickedIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

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

    override fun onItemClick(position: Int, parent: CardView, number: TextView, name: TextView) {
       /* clickedIndex = position
        sevenDaysAdapter.notifyDataSetChanged()

        if (position == clickedIndex){
            number.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_blue))
            name.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_blue))
            parent.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.gold))
        }
        else{
            number.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            name.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            parent.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.dark_blue))
        }*/


    }

}