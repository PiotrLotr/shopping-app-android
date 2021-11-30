package com.example.shoppingapp

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoppingapp.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productViewModel = ProductViewModel(application)

        // buttons actions:
        binding.returnBT.setOnClickListener(){
            val intentReturn = Intent(this, ProductListActivity::class.java)
            startActivity(intentReturn)
        }

        binding.addBT.setOnClickListener {
            val product = Product(
                productName = binding.nameTV.text.toString(),
                price =  binding.priceTV.text.toString(),
                amount = binding.amountTV.text.toString(),
                mark = binding.markTV.text.toString(),
                isBought = false
            )
            productViewModel.insert(product)
            val info = Toast.makeText(this, "Product added", Toast.LENGTH_LONG)
            info.show()

            // broadcast to app2
             val broadcastIntent = Intent()
             broadcastIntent.component = ComponentName(
              "com.example.secondapp",
              "com.example.secondapp.MyReceiver"
             )
             broadcastIntent.putExtra(
                 "productName",
                 binding.nameTV.text.toString()
             )
             this.sendBroadcast(broadcastIntent)
        }

    }
}