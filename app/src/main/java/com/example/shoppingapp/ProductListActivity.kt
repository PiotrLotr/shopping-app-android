package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.databinding.ActivityProductListBinding
import androidx.lifecycle.Observer

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shoppingListRV.layoutManager = LinearLayoutManager(this)
        binding.shoppingListRV.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val productViewModel = ProductViewModel(application)
        val adapter = CustomAdapter(productViewModel)
        binding.shoppingListRV.adapter = adapter

        // navigation:
        binding.returnToMenuBT2.setOnClickListener(){
            val intentReturnToMenu = Intent(this, MainActivity::class.java)
            startActivity(intentReturnToMenu)
        }

        binding.addNewBT.setOnClickListener(){
            val intentAddProduct = Intent(this, AddProductActivity::class.java)
            startActivity(intentAddProduct)
        }


        productViewModel.allProducts.observe(this, Observer
        {
        it.let {
            adapter.setProduct(it)
        }
        })

    }
}