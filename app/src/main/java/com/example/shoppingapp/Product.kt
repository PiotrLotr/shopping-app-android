package com.example.shoppingapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var productName: String,
    var price: String,
    var amount: String,
    var mark: String,
    var isBought: Boolean
    ) : Parcelable {

    fun edit(
        productName: String,
        price: String,
        amount: String,
        mark: String
    ) {
        this.productName = productName
        this.price = price
        this.amount = amount
        this.mark = mark
    }




}


    // nazwa  produktu,cena, ilość, oznaczenie czy zostało już zakupione.
