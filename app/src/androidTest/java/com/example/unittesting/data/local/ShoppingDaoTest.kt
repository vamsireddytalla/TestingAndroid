package com.example.unittesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var shoppingItemDatabase: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = shoppingItemDatabase.shoppingDao()

    }

    @After
    fun tearDown() {
        shoppingItemDatabase.close()
    }


    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shopItem = ShoppingItem("Mango", 3, 1f, "sample", 2)
        dao.insertItem(shopItem)

        val allShoppingItems: List<ShoppingItem> = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shopItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shopItem = ShoppingItem("Mango",3,1f,"url",2)
        dao.insertItem(shopItem)
        dao.deleteShoppingItem(shopItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shopItem)

    }

    @Test
    fun getTotalOrderedPrice() = runBlockingTest {
        val shopItem1 = ShoppingItem("Mango",3,10f,"url",2)
        val shopItem2 = ShoppingItem("Mango2",2,5f,"url",3)

        dao.insertItem(shopItem1)
        dao.insertItem(shopItem2)

        val res = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(res).isEqualTo(3 * 10 + 2 * 5)

    }

}