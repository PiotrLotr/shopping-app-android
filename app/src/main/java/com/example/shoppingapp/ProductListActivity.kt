package com.example.shoppingapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.databinding.ActivityProductListBinding
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth


class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    // firebase variables
    private val firebaseRepo = FirebaseRepo ()
    private var listOfFirebaseProducts: List<FirebaseProduct> = ArrayList()
    private val firebaseAdapter: FirebaseProductDataAdapter = FirebaseProductDataAdapter(this, listOfFirebaseProducts)

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

        // firebase/local db adapter switcher ====================
        if (firebaseRepo.getUser() != null) {
            loadFirebaseProductsList()
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

    private fun loadFirebaseProductsList() {
        firebaseRepo
            .getFirebaseList("products")
            .addOnCompleteListener{
                if (it.isSuccessful){
                    listOfFirebaseProducts = it.result!!.toObjects(FirebaseProduct::class.java)
                    firebaseAdapter.firebaseProducts = listOfFirebaseProducts
                    firebaseAdapter.notifyDataSetChanged()
                } else {
                    Log.d(TAG, "Error: ${it.exception!!.message}")
                }
        }

    }


}