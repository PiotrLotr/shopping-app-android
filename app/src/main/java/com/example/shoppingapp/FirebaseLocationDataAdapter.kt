package com.example.shoppingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.LocationElementBinding

private val firebaseRepo = FirebaseRepo ()

class FirebaseLocationDataAdapter(
    var context: Context,
    var firebaseLocations: List<FirebaseLocation>
):
    RecyclerView.Adapter<FirebaseLocationDataAdapter.FirebaseDataHolder>() {

    class FirebaseDataHolder(val binding: LocationElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationElementBinding.inflate(inflater)
        return FirebaseDataHolder(binding)
    }

    override fun onBindViewHolder(holder: FirebaseDataHolder, position: Int) {
        val currentLocation = firebaseLocations[position]

        //text views:
        holder.binding.locNameTV.text = currentLocation.locName.toString()
        holder.binding.descriptionTV.text = currentLocation.locDescription.toString()
        holder.binding.radiusTV.text = currentLocation.radius.toString()
        holder.binding.latitudeTV.text = currentLocation.latitude.toString()
        holder.binding.longitudeTV.text = currentLocation.longitude.toString()
    }

    override fun getItemCount(): Int {
        return firebaseLocations.size
    }
}



