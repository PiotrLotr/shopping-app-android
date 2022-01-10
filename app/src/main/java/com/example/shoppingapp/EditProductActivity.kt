package com.example.shoppingapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.shoppingapp.databinding.ActivityEditProductBinding
import com.google.firebase.firestore.SetOptions
import com.squareup.okhttp.Dispatcher

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private val firebaseRepo = FirebaseRepo ()

    private lateinit var receivedProduct: Product
    private lateinit var receivedFirebaseProduct: FirebaseProduct

    private lateinit var newName: String
    private lateinit var newPrice: String
    private lateinit var newAmount: String
    private lateinit var newMark: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation:
        binding.returnBT3.setOnClickListener(){
            val intentReturn = Intent(this, ProductListActivity::class.java)
            startActivity(intentReturn)
        }

        if (firebaseRepo.getUser() != null){
            receivedFirebaseProduct = intent.getParcelableExtra("product_object")!!
            bindTextFieldsWithFirebaseProduct(receivedFirebaseProduct)
        } else {
            receivedProduct = intent.getParcelableExtra("product_object")!!
            bindTextFieldsWithProduct(receivedProduct)
        }

        val productViewModel = ProductViewModel(application)

        // saving changes
        binding.saveChangesBT.setOnClickListener(){

            if(firebaseRepo.getUser() != null){
                updateFirebaseProduct(getNewFirebaseProductMap())
                Toast.makeText(this, "Firebase product modified", Toast.LENGTH_LONG).show()
            } else {
                receivedProduct.name= newName
                receivedProduct.price= newPrice
                receivedProduct.amount= newAmount
                receivedProduct.mark= newMark

                productViewModel.update(receivedProduct)
                Toast.makeText(this, "Product modified", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun bindTextFieldsWithProduct(receivedProduct: Product){
        binding.editNameTV.setText(receivedProduct.name)
        binding.editPriceTV.setText(receivedProduct.price)
        binding.editAmountTV.setText(receivedProduct.amount)
        binding.editMarkTV.setText(receivedProduct.mark)
    }

    private fun bindTextFieldsWithFirebaseProduct(receivedProduct: FirebaseProduct){
        binding.editNameTV.setText(receivedProduct.name)
        binding.editPriceTV.setText(receivedProduct.price)
        binding.editAmountTV.setText(receivedProduct.amount)
        binding.editMarkTV.setText(receivedProduct.mark)
    }

    private fun setNewValues() {
        newName = binding.editNameTV.text.toString()
        newPrice = binding.editPriceTV.text.toString()
        newAmount = binding.editAmountTV.text.toString()
        newMark = binding.editMarkTV.text.toString()
    }

    private fun getNewFirebaseProductMap(): Map<String, Any>{
        setNewValues()
        val name = newName
        val amount = newAmount
        val price = newPrice
        val mark = newMark

        val map = mutableMapOf<String, Any>()
        if(name.isNotEmpty()){
            map["name"] = name
        }
        if(name.isNotEmpty()){
            map["price"] = price
        }
        if(name.isNotEmpty()){
            map["amount"] = amount
        }
        if(name.isNotEmpty()){
            map["mark"] = mark
        }
        return map
    }

    private fun updateFirebaseProduct(
        newProductMap: Map<String, Any>
    ) {
        firebaseRepo.getFirebaseCollection("products").document().set(
            newProductMap,
            SetOptions.merge()
            )
    }

}



//private fun updateFirebaseProduct(
//    firebaseProduct: FirebaseProduct,
//    newProductMap: Map<String, Any>
//) {
//    val productQuery = firebaseRepo.getProductsCollection()
//        .whereEqualTo("name", firebaseProduct.name)
//        .whereEqualTo("price", firebaseProduct.price)
//        .whereEqualTo("amount", firebaseProduct.amount)
//        .whereEqualTo("mark", firebaseProduct.mark)
//        .get()
//
//    if(productQuery != null ){
//        firebaseRepo.getProductsCollection().document().set(
//            newProductMap,
//            SetOptions.merge()
//        )
//    } else {
//        Toast.makeText(this, "Document not found", Toast.LENGTH_LONG).show()
//    }
//}