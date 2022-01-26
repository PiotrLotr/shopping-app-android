package com.example.shoppingapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.shoppingapp.databinding.ActivityMapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap

    private val firebaseRepo = FirebaseRepo()
    private val geofenceService = GeofenceService()

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        perms()
        retrieveGeofenceOnMap()

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        // ZOOMING AT CURRENT LOCATION
        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener {
                var location = LatLng(it.latitude, it.longitude)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
            }

        binding.locListBT.setOnClickListener() {
            val goToLocListIntent = Intent(this, LocationListActivity::class.java)
            startActivity(goToLocListIntent)
        }

        binding.addLocationBT.setOnClickListener() {
            // TODO extract repeating function
            LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    val currentLocation = LatLng(it.latitude, it.longitude)
                    var locName = binding.locNamePT.text.toString()
                    // add marker
                    addMarker(currentLocation, locName)
                    // add geofence
                    Log.d("DEBUG_LOG", "ADDING SUCCESS. REQUEST TO GEOFENCE API...")
                    geofenceService.addGeofence(currentLocation, locName, this)
                    // add location to firebase
                    firebaseRepo.addLocationToFirebase(
                        this,
                        locName,
                        binding.locDescriptionPT.text.toString(),
                        50,
                        currentLocation.latitude,
                        currentLocation.longitude
                    )
                }.addOnFailureListener{
                    Toast.makeText(this, "Location adding failed.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        this.map = map
        perms()
        map.uiSettings.isZoomControlsEnabled = true
        map.isMyLocationEnabled = true

        }

    private fun addMarker(loc: LatLng, locName: String){
        map.addMarker(
            MarkerOptions().position(loc)
                .title(locName)
        ).showInfoWindow()
        map.addCircle(
            CircleOptions()
                .center(loc)
                .strokeColor(Color.argb(50,70,70,70))
                .fillColor(Color.argb(70,150,150,150))
                .radius(geofenceService.GEOFENCE_RADIUS.toDouble())
        )
    }

    private fun retrieveGeofenceOnMap(){
        Log.d("DEBUG_LOG","ATTEMPTING RETRIEVE GEOPOINTS...")

        var listOfFirebaseLocations: List<FirebaseLocation> = arrayListOf<FirebaseLocation>()

        firebaseRepo.getFirebaseList("locations")
            .addOnCompleteListener{
                if (it.isSuccessful){
                    listOfFirebaseLocations = it.result!!.toObjects(FirebaseLocation::class.java)

                    Log.d("DEBUG_LOG","FIREBASE LOC LIST CONSISTS: ${listOfFirebaseLocations.size}")
                    for(loc in listOfFirebaseLocations){
                        if(loc != null) {
                            var lat = loc.latitude!!
                            var lng = loc.longitude!!
                            var latLng = LatLng (lat, lng)
                            addMarker(latLng, loc.name.toString())
                            geofenceService.addGeofence(latLng,loc.name.toString(), this)
                            Log.d("DEBUG_LOG","LOC: ${lat} + ${lng}")
                        }
                    }
                } else {
                    Log.d(ContentValues.TAG, "Error: ${it.exception!!.message}")
                }
            }
    }

    private fun perms() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(permissions, 1)
        }
    }


}






