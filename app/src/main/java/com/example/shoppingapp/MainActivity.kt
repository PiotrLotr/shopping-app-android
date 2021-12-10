package com.example.shoppingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.shoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // checking night mode state
        val appSettings: SharedPreferences = getSharedPreferences("AppSettingsPreferences", MODE_PRIVATE)
        val isNightModeOn: Boolean = appSettings.getBoolean("NightMode", false)
        if(isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.shoppingListBT.setOnClickListener(){
            val intentShoppingListActivity = Intent(this, ProductListActivity::class.java)
            startActivity(intentShoppingListActivity)
        }

        binding.btSettings.setOnClickListener(){
            val intentSettingsActivity = Intent(this, OptionsActivity::class.java)
            startActivity(intentSettingsActivity)
        }
        binding.backToLoginBT.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }


}



