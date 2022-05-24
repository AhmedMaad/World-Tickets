package com.maad.worldtickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.worldtickets.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIv.setOnClickListener { finish() }

        //update users info, including (email from update email in firebase auth if needed)

    }
}