package com.example.shoppingapp

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepo {

    private  val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    constructor()

    // Auth
    fun getUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

    fun addProductToFirebase(
        context: Context,
        name: String,
        price: String,
        amount: String,
        mark: String,
        isBought: Boolean
    ){
        val product: MutableMap<String, Any> = HashMap()
        product["name"] = name
        product["price"] = price
        product["amount"] = amount
        product["mark"] = mark
        product["isBought"] = isBought

        this.getFirebaseCollection("products")
            .add(product)
            .addOnSuccessListener {
                Toast.makeText(context, "Product added to firebase", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error while adding to firebase", Toast.LENGTH_LONG).show()
            }
    }

    fun addLocationToFirebase(
        context: Context,
        name: String,
        description: String,
        radius: Int,
        latitude: Double,
        longitude: Double
    ){
        val location: MutableMap<String, Any> = HashMap()
        location["name"] = name
        location["description"] = description
        location["radius"] = radius
        location["latitude"] = latitude
        location["longitude"] = longitude

        this.getFirebaseCollection("locations")
            .add(location)
            .addOnSuccessListener {
                Toast.makeText(context, "Location added to firebase", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error while adding to firebase", Toast.LENGTH_LONG).show()
            }
    }
    fun loadFirebaseLocationList(): List<FirebaseLocation> {
        var listOfFirebaseLocations: List<FirebaseLocation> = ArrayList()
        getFirebaseList("locations")
            .addOnCompleteListener{
                if (it.isSuccessful){
                    listOfFirebaseLocations = it.result!!.toObjects(FirebaseLocation::class.java)
                } else {
                    Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
                }
            }
        return listOfFirebaseLocations
    }




    fun getFirebaseCollection(collectionName: String): CollectionReference {
        return Firebase.firestore.collection(collectionName)
    }

    fun getFirebaseList(collectionName: String): Task<QuerySnapshot> {
        return firebaseFirestore
            .collection(collectionName)
//          .orderBy("name", Query.Direction.DESCENDING)
            .get()
    }

    fun deleteFirebaseProduct(
        firebaseProduct: FirebaseProduct
    ) {
        // firebaseProduct
//        firebaseRepo.getProductsCollection().document("$productQuery").delete()
    }




}