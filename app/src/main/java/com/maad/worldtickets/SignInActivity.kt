package com.maad.worldtickets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maad.worldtickets.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}