package com.maad.worldtickets.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.worldtickets.databinding.ActivityEventDetailsBinding

class EventDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIv.setOnClickListener { finish() }

    }
}