package com.example.shoppingapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FirebaseProduct (
    var name: String = "",
    val price: String = "",
    val amount: String = "",
    val mark: String = "",
    val isBought: Boolean? = null
    ): Parcelable{
}