package com.example.shoppingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val receivedProduct: Product = intent.getParcelableExtra<Product>("product_object")!!
        // setting field with received info
        binding.editNameTV.setText(receivedProduct.productName)
        binding.editPriceTV.setText(receivedProduct.price)
        binding.editAmountTV.setText(receivedProduct.amount)
        binding.editMarkTV.setText(receivedProduct.mark)

        // saving changes
        binding.saveChangesBT.setOnClickListener(){
            receivedProduct.edit (
                binding.editNameTV.text.toString(),
                binding.editPriceTV.text.toString(),
                binding.editAmountTV.text.toString(),
                binding.editMarkTV.text.toString()
            )
            // how to refresh ProductListActivity?



            Toast.makeText(this, receivedProduct.productName, Toast.LENGTH_LONG).show()
        }


    }


}