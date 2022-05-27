package com.maad.worldtickets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val pp: String = "",
    val phoneNo: String = "",
    val facebookLink: String = "",
    val favorites: Array<String> = arrayOf()
) : Parcelable