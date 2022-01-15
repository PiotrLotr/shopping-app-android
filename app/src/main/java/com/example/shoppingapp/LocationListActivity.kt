package com.example.shoppingapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.databinding.ActivityLocationListBinding


class LocationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationListBinding

    // firebase variables
    private val firebaseRepo = FirebaseRepo ()
    private var listOfFirebaseLocations: List<FirebaseLocation> = ArrayList()
    private val firebaseAdapter: FirebaseLocationDataAdapter = FirebaseLocationDataAdapter(this, listOfFirebaseLocations)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.locListRV.layoutManager = LinearLayoutManager(this)
        binding.locListRV.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        loadFirebaseLocationList()
        binding.locListRV.adapter = firebaseAdapter
    }

    fun loadFirebaseLocationList() {
        firebaseRepo.getFirebaseList("locations")
            .addOnCompleteListener{
                if (it.isSuccessful){
                    listOfFirebaseLocations = it.result!!.toObjects(FirebaseLocation::class.java)
                    firebaseAdapter.firebaseLocations = listOfFirebaseLocations
                    firebaseAdapter.notifyDataSetChanged()
                } else {
                    Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
                }
            }
    }



}
