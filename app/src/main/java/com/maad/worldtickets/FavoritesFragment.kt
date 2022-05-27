package com.maad.worldtickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        //retrieve fav from firebase
        //if array is empty then show "empty fav layout" (save fav on firebase)
        //else show the recycler view
        val editor =
            requireContext().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        db
            .collection("users")
            .document(editor.getString("id", null)!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)!!
                val favorites = user.favorites
                if (favorites.isEmpty()){
                    binding.emptyFavoritesLayout.root.visibility = View.VISIBLE
                    
                }
                else{

                }
            }

        return binding.root
    }

}