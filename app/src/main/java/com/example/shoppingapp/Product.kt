package com.example.shoppingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var productName: String,
    var price: String,
    var amount: String,
    var mark: String,
    var isBought: Boolean
    )

    // nazwa  produktu,cena, ilość, oznaczenie czy zostało już zakupione.
