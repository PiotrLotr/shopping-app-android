package com.example.shoppingapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)


//    @Query("SELECT productName FROM product")
//    fun getProductName(product: Product)

}