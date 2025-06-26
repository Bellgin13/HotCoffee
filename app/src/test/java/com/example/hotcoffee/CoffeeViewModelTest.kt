package com.example.hotcoffee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.hotcoffee.model.Coffee
import com.example.hotcoffee.ui.coffee.CoffeeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CoffeeViewModelTest {

    @get:Rule
    val instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CoffeeViewModel

    private val mockCoffees = listOf(
        Coffee(id = 34, title = "Espresso", ingredients = listOf("Coffee"), description = "Sabrina's Favorite", image =""),
        Coffee(id = 35, title = "Latte", ingredients = listOf("Coffee", "Milk"), description = "Summer Queen", image =""),
        Coffee(id = 36, title = "Cappuccino", ingredients = listOf("Coffee", "Milk", "Foam"), description = "Latte but fancy.", image ="")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CoffeeViewModel()
        viewModel.setCoffees(mockCoffees)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_initialCoffeeListIsLoaded() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.coffees.getOrAwaitValue()
        assertEquals(3, result.size)
    }

    @Test
    fun test_filterReturnsCorrectCoffees() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.updateFilters(setOf("Milk"))
        val filtered = viewModel.filteredCoffees.getOrAwaitValue()
        assertEquals(2, filtered.size)
        assert(filtered.all { "Milk" in it.ingredients })
    }
}
