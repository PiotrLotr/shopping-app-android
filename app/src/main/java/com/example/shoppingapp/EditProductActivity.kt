package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.widget.Toast
import com.example.shoppingapp.databinding.ActivityEditProductBinding

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation:
        binding.returnBT3.setOnClickListener(){
            val intentReturn = Intent(this, ProductListActivity::class.java)
            startActivity(intentReturn)
        }

        val receivedProduct: Product = intent.getParcelableExtra("product_object")!!
        // setting field with received info
        binding.editNameTV.setText(receivedProduct.productName)
        binding.editPriceTV.setText(receivedProduct.price)
        binding.editAmountTV.setText(receivedProduct.amount)
        binding.editMarkTV.setText(receivedProduct.mark)

        val productViewModel = ProductViewModel(application)
        // saving changes
        binding.saveChangesBT.setOnClickListener(){
            receivedProduct.productName= binding.editNameTV.text.toString()
            receivedProduct.price= binding.editPriceTV.text.toString()
            receivedProduct.amount= binding.editAmountTV.text.toString()
            receivedProduct.mark= binding.editMarkTV.text.toString()

            productViewModel.update(receivedProduct)
            Toast.makeText(this, "Product modified", Toast.LENGTH_LONG).show()
        }


    }


}