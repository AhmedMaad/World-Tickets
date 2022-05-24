package com.maad.worldtickets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

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
            if (user.pp.isEmpty()){}

        }

        return binding.root
    }

}