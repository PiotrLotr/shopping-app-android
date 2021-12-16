package com.example.shoppingapp

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoppingapp.databinding.ActivityAddProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productViewModel = ProductViewModel(application)

        // buttons actions:
        binding.returnBT.setOnClickListener {
            val intentReturn = Intent(this, ProductListActivity::class.java)
            startActivity(intentReturn)
        }

        binding.addBT.setOnClickListener {
            var productName = binding.nameTV.text.toString()
            var price = binding.priceTV.text.toString()
            var amount = binding.amountTV.text.toString()
            var mark = binding.markTV.text.toString()
            var isBought = false

            if (FirebaseAuth.getInstance().currentUser != null){
                addProductToFirebase(
                    productName,
                    price,
                    amount,
                    mark,
                    isBought
                )
            } else {
                 var product = Product(
                     productName = productName,
                     price = price,
                     amount = amount,
                     mark = mark,
                     isBought = isBought
                )

                productViewModel.insert(product)
                Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show()
                // broadcast to app2
                val broadcastIntent = Intent()
                broadcastIntent.component = ComponentName(
                    "com.example.secondapp",
                    "com.example.secondapp.MyReceiver"
                )
//             broadcastIntent.putExtra(
//                 "productID",
//                 product.
//             )
                broadcastIntent.putExtra(
                    "productName",
                    binding.nameTV.text.toString()
                )
                this.sendBroadcast(broadcastIntent)
                }
            }

        }

    fun addProductToFirebase(name: String, price: String, amount: String, mark: String, isBought: Boolean){
        val product: MutableMap<String, Any> = HashMap()
            product["name"] = name
            product["price"] = price
            product["amount"] = amount
            product["mark"] = mark
            product["isBought"] = isBought

        db.collection("products")
            .add(product)
            .addOnSuccessListener {
                Toast.makeText(this, "Product added to firebase", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error while adding to firebase", Toast.LENGTH_LONG).show()
            }
    }

}




