package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      register =========================
        FirebaseApp.initializeApp(applicationContext)
        val fbAuth = FirebaseAuth.getInstance()

        binding.signUpTV.setOnClickListener {
            fbAuth.createUserWithEmailAndPassword(
                binding.emailTV.text.toString(),
                binding.passwdTV.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Account created",
                    Toast.LENGTH_LONG
                )
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Register failed. Check credentials and try again...",
                    Toast.LENGTH_LONG
                )
            }
        }
//       =======================================

//       login =========================
        binding.loginBT.setOnClickListener() {
            fbAuth.signInWithEmailAndPassword(
                binding.emailTV.text.toString(),
                binding.passwdTV.text.toString()
            ).addOnSuccessListener {
                val intent = Intent(this, UserMenuActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "User not recognized.. Please check credentials.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // navigation:
        binding.skipBT.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

}

