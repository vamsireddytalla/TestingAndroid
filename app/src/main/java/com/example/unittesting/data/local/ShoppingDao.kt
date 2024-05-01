package com.example.unittesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao
{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("Select * from shopping_item")
    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>


    @Query("Select SUM(price * amount) from shopping_item")
    fun observeTotalPrice() : LiveData<Float>

}