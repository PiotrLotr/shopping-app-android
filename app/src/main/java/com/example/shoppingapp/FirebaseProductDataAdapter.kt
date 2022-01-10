package com.example.shoppingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ElementBinding

private val firebaseRepo = FirebaseRepo ()

class FirebaseProductDataAdapter(
    var context: Context,
    var firebaseProducts: List<FirebaseProduct>
    ):
    RecyclerView.Adapter<FirebaseProductDataAdapter.FirebaseDataHolder>() {

    class FirebaseDataHolder(val binding: ElementBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseDataHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementBinding.inflate(inflater)
        return FirebaseDataHolder(binding)
    }

    override fun onBindViewHolder(holder: FirebaseDataHolder, position: Int) {
        val currentProduct = firebaseProducts[position]

        //text views:
        holder.binding.productNameTV.text = currentProduct.name
        holder.binding.priceTV.text = currentProduct.price
        holder.binding.amountTV.text = currentProduct.amount
        holder.binding.productMarkTV.text = currentProduct.mark

        // buttons:
        holder.binding.wasBoughtCB.setOnClickListener() {
//            currentProduct.isBought = holder.binding.wasBoughtCB.isChecked
        }
        holder.binding.deleteIB.setOnClickListener {
            firebaseRepo.deleteFirebaseProduct(currentProduct)
        }
        holder.binding.modifyIB.setOnClickListener() {
            val editIntent = Intent(context, EditProductActivity::class.java)
            editIntent.putExtra("product_object", currentProduct)
            context.startActivity(editIntent)
        }

    }

    override fun getItemCount(): Int {
        return firebaseProducts.size
    }
}



