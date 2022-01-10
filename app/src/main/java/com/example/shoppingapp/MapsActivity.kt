package com.example.shoppingapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val geofenceAPI = GeofenceAPI()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
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
                    // add marker
                    map.addMarker(
                        MarkerOptions().position(currentLocation)
                            .title("Current location")
                    ).showInfoWindow()
                    map.addCircle(
                        CircleOptions()
                            .center(currentLocation)
                            .strokeColor(Color.argb(50,70,70,70))
                            .fillColor(Color.argb(70,150,150,150))
                            .radius(geofenceAPI.GEOFENCE_RADIUS.toDouble())
                    )
                    // add geofence
                    geofenceAPI.addGeofence(currentLocation, this)
                    // add location to firebase
                    firebaseRepo.addLocationToFirebase(
                        this,
                        binding.locNamePT.text.toString(),
                        binding.locDescriptionPT.text.toString(),
                        100,
                        currentLocation.latitude,
                        currentLocation.longitude
                    )
                }.addOnFailureListener{
                    Toast.makeText(this, "Location adding failed.", Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        map.uiSettings.isZoomControlsEnabled = true


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled = true

        }


//        var markedLocation = map.setLocationSource()
//
//        map.addMarker(
//            MarkerOptions()
//                .position(markedLocation)
//                .title("Marker in Sydney")
//        )


    }






