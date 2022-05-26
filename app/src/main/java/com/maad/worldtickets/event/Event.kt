package com.maad.worldtickets.event

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
class Event(
    @DrawableRes val image: Int,
    val title: String,
    val price: String,
    val description: String,
    val location: String,
    val organizer: String,
    val website: String,
    /*val date: String*/
) : Parcelable