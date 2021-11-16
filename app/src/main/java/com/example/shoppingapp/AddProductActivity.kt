package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppingapp.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnBT.setOnClickListener(){
            val intentReturn = Intent(this, ProductListActivity::class.java)
            startActivity(intentReturn)
        }

        val productViewModel = ProductViewModel(application)

        binding.addBT.setOnClickListener {
            val product = Product(
                productName = binding.nameTV.text.toString(),
                price =  binding.priceTV.text.toString(),
                amount = binding.amountTV.text.toString(),
                mark = binding.markTV.toString(),
                isBought = binding.checkBoxWasBought.isChecked
            )
            productViewModel.insert(product)
        }

    }



}