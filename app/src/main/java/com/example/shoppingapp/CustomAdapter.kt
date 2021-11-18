package com.example.shoppingapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ElementBinding


class CustomAdapter(private val productViewModel: ProductViewModel, context: Context
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private var listOfProducts = emptyList<Product>()
    private var parentContext = context

    class CustomViewHolder(val binding: ElementBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementBinding.inflate(inflater)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int, ) {
        val currentProduct = listOfProducts[position]

        //text views:
        holder.binding.productNameTV.text = currentProduct.productName
        holder.binding.priceTV.text = currentProduct.price
        holder.binding.amountTV.text = currentProduct.amount
        holder.binding.productMarkTV.text = currentProduct.mark

        // buttons:
        holder.binding.wasBoughtCB.setOnClickListener(){
            currentProduct.isBought = holder.binding.wasBoughtCB.isChecked
        }
        holder.binding.deleteIB.setOnClickListener {
            productViewModel.delete(currentProduct)
        }

        holder.binding.modifyIB.setOnClickListener(){
            val editIntent = Intent(parentContext, EditProductActivity::class.java)
            // passing info about product (serializable???)
            editIntent.putExtra("product_object", currentProduct)
            parentContext.startActivity(editIntent)
        }


    }

    override fun getItemCount(): Int = listOfProducts.size

    fun setProduct(products: List<Product>){
        listOfProducts = products
        notifyDataSetChanged()
    }

}
