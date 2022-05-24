package com.maad.worldtickets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val name: String = "",
    val email: String = "",
    val pp: String = "",
    val phoneNo: String = "",
    val facebookLink: String = "",
    val userId: String = ""
    ) : Parcelable