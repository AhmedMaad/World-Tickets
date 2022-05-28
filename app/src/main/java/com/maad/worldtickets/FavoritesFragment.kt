package com.maad.worldtickets

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentFavoritesBinding
import com.maad.worldtickets.event.Event


class FavoritesFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var events = arrayListOf<Event>()
    val favEvent = arrayListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        events.add(
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
        events.add(
            Event(
                R.drawable.event_image,
                "Art & Meet in Street Plaza",
                "250$",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley",
                "Greenfields, Sector 42, Faridabad",
                "Y Company Organizer",
                "https://www.planningpod.com/online-event-booking-software.cfm",
                "Sports"
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
                "https://about.facebook.com/meta/",
                "Technology"
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
                "https://www.healthcareconferencesuk.co.uk/virtual-online-courses/amat-clinical-audit-conference",
                "Music"
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
                "https://www.automech-formula.com/en/home.html",
                "Cars"
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
                "https://www.formula1.com/en/racing/2022.html",
                "Sports"
            )
        )

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
                binding.progress.visibility = View.INVISIBLE
                val user = it.toObject(User::class.java)!!
                val favorites = user.favorites
                if (favorites.isEmpty()) {
                    binding.emptyFavoritesLayout.root.visibility = View.VISIBLE
                    binding.favoritesRv.visibility = View.INVISIBLE
                } else {
                    binding.emptyFavoritesLayout.root.visibility = View.INVISIBLE
                    binding.favoritesRv.visibility = View.VISIBLE
                    //connect to adapter after looping
                    for (fav in user.favorites) {
                        for (event in events)
                            if (fav == event.category)
                                favEvent.add(event)
                    }
                    val adapter = FavoriteEventAdapter(requireActivity(), favEvent)
                    binding.favoritesRv.adapter = adapter


                }
            }

        binding.emptyFavoritesLayout.chooseFavorites.setOnClickListener {
            //show weired dialog, and after choosing store all choices in array of strings
            //then send them as an "update" to user object in firebase using user id, and
            //"favorites" as a field
            //Clicking "Save my Favorites" will show progress bar while uploading to firebase
            val bottomUp: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            binding.emptyFavoritesLayout.root.startAnimation(bottomUp)
            binding.emptyFavoritesLayout.root.visibility = View.VISIBLE
        }

        return binding.root
    }

}