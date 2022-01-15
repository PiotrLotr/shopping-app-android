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
        Log.d("DEBUG_LOG", "INSIDE GEOFENCE API. ATTEMPT TO CREATE GEOFENCE...")
        geoClient.addGeofences(
            getGeofencingRequest(geo),
            getGeofencePendingIntent(context)
        )
            .addOnSuccessListener {
                Log.d("DEBUG_LOG","GEOFENCE WAS ADDED, CHECK BROADCAST RECEIVER")
                Log.d("DEBUG_LOG","GEO: ${geo}")
                Log.d("DEBUG_LOG","CONTEXT: ${context}")
            }
            .addOnFailureListener {
                Log.d("DEBUG_LOG","GEOFENCE WAS NOT ADDED")
                Log.d("DEBUG_LOG","GEO: ${geo}")
                Log.d("DEBUG_LOG","GEO: ${context}")
                Log.d("DEBUG_LOG","GEO: ${it.stackTraceToString()}")
            }

    }

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofence(geofence)
        .build()
    }


    private fun getGeofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun getRequestId(): String {
        TODO("Not yet implemented")
    }




}

