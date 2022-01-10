package com.example.shoppingapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geoEvent = GeofencingEvent.fromIntent(intent)
        val triggering = geoEvent.triggeringGeofences

        val msg = intent.getStringExtra("message")
        Log.d("MESSAGE", msg.toString())

        for( geo in triggering){
            Log.i("geofence", "Geofence ID: ${geo.requestId} is active.")
        }

        if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            Log.i("geofences", "entry: ${geoEvent.triggeringLocation.toString()}")

            // notification
            var broadcastIntent = Intent(context, GeofenceService::class.java)
            Toast.makeText(context, "intent works", Toast.LENGTH_SHORT).show()
            context.startForegroundService(broadcastIntent)

        }else if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            Log.i("geofences", "out of: ${geoEvent.triggeringLocation.toString()}")
        }else{
            Log.e("geofences", "Error.")
        }
    }

}