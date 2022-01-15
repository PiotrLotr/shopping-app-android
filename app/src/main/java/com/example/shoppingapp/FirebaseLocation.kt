package com.example.shoppingapp


data class FirebaseLocation(
    var name: String?= "",
    var description: String?= "",
    var radius: Integer? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
)