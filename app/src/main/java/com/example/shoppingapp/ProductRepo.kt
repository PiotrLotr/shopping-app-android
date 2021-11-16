package com.example.shoppingapp

class ProductRepo(private val productDao: ProductDao) {

    val allProducts = productDao.getProducts()

    fun insert(product: Product) = productDao.insert(product)

    fun delete(product: Product) = productDao.delete(product)

}
