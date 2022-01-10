package com.example.shoppingapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var price: String,
    var amount: String,
    var mark: String,
    var isBought: Boolean
    ) : Parcelable {
}

