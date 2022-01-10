package com.example.shoppingapp

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class GeofenceAPI: Geofence{

    var id = 0
    val GEOFENCE_RADIUS = 500

    private fun createGeofence(loc: LatLng): Geofence {
        return Geofence.Builder()
            .setRequestId("Geo${id++}")
            .setCircularRegion(
                loc.latitude,
                loc.longitude,
                GEOFENCE_RADIUS.toFloat()
            )
            .setExpirationDuration(60 * 60 * 1000)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
            )
            .setLoiteringDelay(1)
            .build()
    }

    fun addGeofence(loc: LatLng, context: Context) {
        val geoClient = LocationServices.getGeofencingClient(context)
        val geo = createGeofence(loc)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        geoClient.addGeofences(
            getGeofencingRequest(geo),
            getGeofencePendingIntent(context)
        )
            .addOnSuccessListener {
                Toast.makeText(context,"Geofence added.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Geofence adding failed", Toast.LENGTH_SHORT).show()
            }
        Log.d("MESSAGE","GEOFENCE WAS ADDED, PROBLEM WITH BROADCAST RECEIVER")
    }

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofence(geofence)
        .build()
    }


    private fun getGeofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceReceiver::class.java)
            .putExtra("message", "Broadcast intent works fine!")
        return PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }


    override fun getRequestId(): String {
        TODO("Not yet implemented")
    }




}

