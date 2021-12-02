package com.example.shoppingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.android.parcel.Parcelize

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val repo: ProductRepo =
        ProductRepo(ProductDB.getDatabase(application.applicationContext).productDao())

    val allProducts: LiveData<List<Product>> = repo.allProducts

    fun insert(product: Product) = repo.insert(product)

    fun delete(product: Product) = repo.delete(product)

    fun update(product: Product) = repo.update(product)



}
