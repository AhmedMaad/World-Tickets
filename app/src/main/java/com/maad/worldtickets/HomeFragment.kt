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
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var days = arrayListOf<DayModel>()

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
            binding.progress.visibility = View.GONE
            val user = it.toObject(User::class.java)!!
            if (user.pp.isEmpty())
                binding.profileIv.setImageResource(R.drawable.ic_person)
            else
                Glide.with(requireActivity()).load(user.pp).into(binding.profileIv)

            val parts = user.name.split(" ").toMutableList()
            val firstName = parts.firstOrNull()
            binding.nameTv.text = "Hello, $firstName !"
            binding.exploreTv.visibility = View.VISIBLE
        }

        for (i in 0..6)
            days.add(DayModel(0, 0, getCalculatedDate(i)))

        return binding.root
    }

    private fun getCalculatedDate(next: Int): String {
        val cal = Calendar.getInstance()
        //val today = "${cal.get(Calendar.DAY_OF_MONTH)}-${cal.get(Calendar.MONTH) + 1}-${cal.get(Calendar.YEAR)}"
        //Log.d("trace", today)
        val s = SimpleDateFormat("dd-MM-yyyy")
        cal.add(Calendar.DAY_OF_YEAR, next)

        Log.d("trace", "All: ${s.format(cal.time)}")
        val formattedDate = s.format(cal.time)
        val parts = formattedDate.split("-").toMutableList()
        val day = parts.firstOrNull()
        Log.d("trace", day!!)

        //cal.time = cal.time
        val dayOfWeek = cal[Calendar.DAY_OF_WEEK]
        Log.d("trace", "$dayOfWeek")

        val dayName = SimpleDateFormat("EE").format(cal.time)
        Log.d("trace", "$dayName")


        return s.format(cal.time)
    }

}