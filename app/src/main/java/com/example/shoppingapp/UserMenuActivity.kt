package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingapp.databinding.ActivityLoginBinding
import com.example.shoppingapp.databinding.ActivityUserMenuBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

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


    }
}