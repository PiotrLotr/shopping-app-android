package com.example.shoppingapp
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FirebaseLocation(
    var locName: String? = "",
    var locDescription: String? = "",
    var radius: Integer? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
) : Parcelable {}