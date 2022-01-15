package com.example.shoppingapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

            val geoEvent = GeofencingEvent.fromIntent(intent)
            val triggering = geoEvent.triggeringGeofences

            var serviceIntent = Intent(context, NotificationService::class.java)
             serviceIntent.putExtra("locName", intent.getStringExtra("locName") )

            for( geo in triggering){
                Log.i("DEBUG_LOG", "GEOFENCE ID: ${geo.requestId} ACTIVE.")
            }
            if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
                Log.d("DEBUG_LOG", "ENTRY: ${geoEvent.triggeringLocation}")
                serviceIntent.putExtra("VARIANT", "ENTRY")
                context.startService(serviceIntent)
            }else if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
                Log.i("DEBUG_LOG", "EXIT: ${geoEvent.triggeringLocation}")
                serviceIntent.putExtra("VARIANT", "EXIT")
                context.startService(serviceIntent)
            }else{
                Log.e("DEBUG_LOG", "Error.")
            }
        }

    }

