package com.maad.worldtickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maad.worldtickets.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)


        return binding.root
    }

}