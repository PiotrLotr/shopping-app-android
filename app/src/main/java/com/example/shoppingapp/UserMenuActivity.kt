package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingapp.databinding.ActivityUserMenuBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class UserMenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(applicationContext)
        val fbAuth = FirebaseAuth.getInstance()

        binding.welcomeTV.text = "Welcome ${fbAuth.currentUser!!.email}!"

        // navigation ====================
        binding.signOutBT.setOnClickListener(){
            fbAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.userShoppingListBT.setOnClickListener(){
            val intentShoppingListActivity = Intent(this, ProductListActivity::class.java)
            startActivity(intentShoppingListActivity)
        }

        binding.mapBT.setOnClickListener(){
            val intentMapActivity = Intent (this, MapsActivity::class.java)
            startActivity(intentMapActivity)
        }

        binding.settBT.setOnClickListener(){
            val intentSettingsActivity = Intent(this, OptionsActivity::class.java)
            startActivity(intentSettingsActivity)
        }


    }
}