package com.example.shoppingapp

class FirebaseProduct (
    val name: String,
    val price: String,
    val amount: String,
    val mark: String,
    val isBought:Boolean){

    constructor() : this ("","", "", "", false)

}