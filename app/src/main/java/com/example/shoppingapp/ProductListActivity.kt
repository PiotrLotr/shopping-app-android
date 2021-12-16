package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.databinding.ActivityProductListBinding
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val db = Firebase.firestore
    private var listOfFirebaseProducts =  ArrayList<FirebaseProduct>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recycler view options ========================
        binding.shoppingListRV.layoutManager = LinearLayoutManager(this)
        binding.shoppingListRV.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        // ==============================================

        // signed/not signed adapter ====================
        if (FirebaseAuth.getInstance().currentUser != null) {
            fetchDataFromFirebase()
            val firebaseAdapter = FirebaseDataAdapter(listOfFirebaseProducts)
            binding.shoppingListRV.adapter = firebaseAdapter
        } else {
            val productViewModel = ProductViewModel(application)
            val adapter = CustomAdapter(productViewModel, this)
            binding.shoppingListRV.adapter = adapter

            productViewModel.allProducts.observe(this, Observer
            {
                it.let {
                    adapter.setProduct(it)
                }
            })
        }
        // =============================================

        // ====================================== navigation:
        binding.returnToMenuBT2.setOnClickListener() {
            lateinit var intentReturnToMenu: Intent
            if (FirebaseAuth.getInstance().currentUser == null) {
                intentReturnToMenu = Intent(this, MainActivity::class.java)
            } else {
                intentReturnToMenu = Intent(this, UserMenuActivity::class.java)
            }
            startActivity(intentReturnToMenu)
        }

        binding.addNewBT.setOnClickListener() {
            val intentAddProduct = Intent(this, AddProductActivity::class.java)
            startActivity(intentAddProduct)
        }

    }

    private fun fetchDataFromFirebase() {
        db.collection("products")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        var firebaseProduct = FirebaseProduct(
                            document.data["name"].toString(),
                            document.data["price"].toString(),
                            document.data["amount"].toString(),
                            document.data["mark"].toString(),
                            document.data["isBought"] as Boolean
                        )
                        listOfFirebaseProducts.add(firebaseProduct)
                    }
                } else {
                    Toast.makeText(this, "Error: ${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}