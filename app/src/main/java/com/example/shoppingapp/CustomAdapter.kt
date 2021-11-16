package com.example.shoppingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.ElementBinding

class CustomAdapter(private val productViewModel: ProductViewModel
) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private var listOfProducts = emptyList<Product>()

    class CustomViewHolder(val binding: ElementBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAdapter.CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ElementBinding.inflate(inflater)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomAdapter.CustomViewHolder, position: Int) {
        val currentProduct = listOfProducts[position]
        holder.binding.productNameTV.text = currentProduct.productName
        holder.binding.priceTV.text = currentProduct.price
        holder.binding.amountTV.text = currentProduct.amount
        holder.binding.markTV.text = currentProduct.mark
        holder.binding.wasBoughtCB.isChecked = currentProduct.isBought

        holder.binding.deleteIB.setOnClickListener {
            productViewModel.delete(currentProduct)
        }
        holder.binding.modifyIB.setOnClickListener{

        }
    }

    override fun getItemCount(): Int = listOfProducts.size

    fun setProduct(products: List<Product>){
        listOfProducts = products
        notifyDataSetChanged()
    }

}
