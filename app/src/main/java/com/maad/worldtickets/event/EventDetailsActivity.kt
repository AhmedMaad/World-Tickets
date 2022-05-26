package com.maad.worldtickets.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.maad.worldtickets.databinding.ActivityEventDetailsBinding

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backIv.setOnClickListener { finish() }

        val event = intent.getParcelableExtra<Event>("event")!!

        binding.eventIv.setImageResource(event.image)
        binding.nameTv.text = event.title
        binding.priceTv.text = event.price
        binding.descriptionTv.text = event.description
        binding.locationTv.text = event.location
        binding.organizerTv.text = event.organizer

        binding.bookNowBtn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, event.website.toUri()))
        }

    }
}