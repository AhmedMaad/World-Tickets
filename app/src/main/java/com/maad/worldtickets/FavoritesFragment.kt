package com.maad.worldtickets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maad.worldtickets.databinding.FragmentFavoritesBinding
import com.maad.worldtickets.event.Event


class FavoritesFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private var events = arrayListOf<Event>()
    val favEvent = arrayListOf<Event>()
    lateinit var binding: FragmentFavoritesBinding
    var techCounter = 0
    var musicCounter = 0
    var sportsCounter = 0
    var artCounter = 0
    var carCounter = 0
    private lateinit var favorites: ArrayList<String>
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

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
                "Arts"
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

        getFavorites()

        binding.editTv.setOnClickListener {
            val bottomUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            binding.chooseFavoritesLayout.root.startAnimation(bottomUp)
            binding.chooseFavoritesLayout.root.visibility = View.VISIBLE
            for (fav in favorites) {
                when (fav) {
                    "Arts" -> {
                        artCounter = 1
                        changeBtnBG(
                            binding.chooseFavoritesLayout.artsBtn,
                            R.drawable.border_dark_blue
                        )
                    }
                    "Cars" -> {
                        carCounter = 1
                        changeBtnBG(
                            binding.chooseFavoritesLayout.carsBtn,
                            R.drawable.border_dark_blue
                        )
                    }
                    "Music" -> {
                        musicCounter = 1
                        changeBtnBG(
                            binding.chooseFavoritesLayout.musicBtn,
                            R.drawable.border_dark_blue
                        )
                    }
                    "Sports" -> {
                        sportsCounter = 1
                        changeBtnBG(
                            binding.chooseFavoritesLayout.sportsBtn,
                            R.drawable.border_dark_blue
                        )
                    }
                    "Technology" -> {
                        techCounter = 1
                        changeBtnBG(
                            binding.chooseFavoritesLayout.techBtn,
                            R.drawable.border_dark_blue
                        )
                    }
                }
            }
        }

        binding.emptyFavoritesLayout.chooseFavorites.setOnClickListener {
            val bottomUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
            binding.chooseFavoritesLayout.root.startAnimation(bottomUp)
            binding.chooseFavoritesLayout.root.visibility = View.VISIBLE
        }

        binding.root.setOnClickListener {
            if (binding.chooseFavoritesLayout.root.visibility == View.VISIBLE) {
                val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
                binding.chooseFavoritesLayout.root.startAnimation(slideDown)
                binding.chooseFavoritesLayout.root.visibility = View.INVISIBLE
            }
        }

        binding.chooseFavoritesLayout.artsBtn.setOnClickListener {
            if (artCounter % 2 == 0)
                changeBtnBG(binding.chooseFavoritesLayout.artsBtn, R.drawable.border_dark_blue)
            else
                changeBtnBG(binding.chooseFavoritesLayout.artsBtn, R.drawable.border_blue)
            ++artCounter
        }

        binding.chooseFavoritesLayout.carsBtn.setOnClickListener {
            if (carCounter % 2 == 0)
                changeBtnBG(binding.chooseFavoritesLayout.carsBtn, R.drawable.border_dark_blue)
            else
                changeBtnBG(binding.chooseFavoritesLayout.carsBtn, R.drawable.border_blue)
            ++carCounter
        }

        binding.chooseFavoritesLayout.musicBtn.setOnClickListener {
            if (musicCounter % 2 == 0)
                changeBtnBG(binding.chooseFavoritesLayout.musicBtn, R.drawable.border_dark_blue)
            else
                changeBtnBG(binding.chooseFavoritesLayout.musicBtn, R.drawable.border_blue)
            ++musicCounter
        }

        binding.chooseFavoritesLayout.sportsBtn.setOnClickListener {
            if (sportsCounter % 2 == 0)
                changeBtnBG(binding.chooseFavoritesLayout.sportsBtn, R.drawable.border_dark_blue)
            else
                changeBtnBG(binding.chooseFavoritesLayout.sportsBtn, R.drawable.border_blue)
            ++sportsCounter
        }

        binding.chooseFavoritesLayout.techBtn.setOnClickListener {
            if (techCounter % 2 == 0)
                changeBtnBG(binding.chooseFavoritesLayout.techBtn, R.drawable.border_dark_blue)
            else
                changeBtnBG(binding.chooseFavoritesLayout.techBtn, R.drawable.border_blue)
            ++techCounter
        }

        binding.chooseFavoritesLayout.saveFavoritesBtn.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            val favList = arrayListOf<String>()
            if (artCounter % 2 != 0)
                favList.add("Arts")
            if (carCounter % 2 != 0)
                favList.add("Cars")
            if (musicCounter % 2 != 0)
                favList.add("Music")
            if (sportsCounter % 2 != 0)
                favList.add("Sports")
            if (techCounter % 2 != 0)
                favList.add("Technology")

            db.collection("users").document(userId).update("favorites", favList)
                .addOnSuccessListener {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Favorites Edited", Toast.LENGTH_SHORT).show()

                    binding.chooseFavoritesLayout.root.visibility = View.GONE
                    getFavorites()


                }

        }

        return binding.root
    }

    private fun getFavorites() {
        binding.progress.visibility = View.VISIBLE
        val editor =
            requireContext().getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        userId = editor.getString("id", null)!!
        db
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                val user = it.toObject(User::class.java)!!
                favorites = user.favorites
                if (favorites.isEmpty()) {
                    binding.emptyFavoritesLayout.root.visibility = View.VISIBLE
                    binding.favoritesRv.visibility = View.INVISIBLE
                } else {
                    binding.editTv.visibility = View.VISIBLE
                    binding.emptyFavoritesLayout.root.visibility = View.INVISIBLE
                    binding.favoritesRv.visibility = View.VISIBLE
                    favEvent.clear()
                    for (fav in user.favorites)
                        for (event in events)
                            if (fav == event.category)
                                favEvent.add(event)
                    val adapter = FavoriteEventAdapter(requireActivity(), favEvent)
                    binding.favoritesRv.adapter = adapter
                }
            }
    }

    private fun changeBtnBG(btn: AppCompatButton, @DrawableRes borderFile: Int) {
        btn.background = ActivityCompat.getDrawable(requireContext(), borderFile)
    }

}