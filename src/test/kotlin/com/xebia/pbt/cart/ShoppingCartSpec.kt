package com.xebia.pbt.cart

import com.xebia.pbt.cart.impl1.ShoppingCart
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ShoppingCartSpec {

    // Add Item tests
    // --------------
    @Test
    fun quantityZeroThrowsAnError() {
        val cart = ShoppingCart()
        shouldThrow<IllegalArgumentException> { cart.addItem("Item 1", 0) }
    }

    @Test
    fun quantityLowerThanZeroThrowsAnError() {
        val cart = ShoppingCart()
        shouldThrow<IllegalArgumentException> { cart.addItem("Item 1", -10) }
    }
    // --------------

    // Remove Item tests
    // --------------
    @Test
    fun itemDoesNotExistsThrowsAnError() {
        val cart = ShoppingCart()
        shouldThrow<IllegalArgumentException> { cart.removeItem("Item 1", 0) }
    }

    @Test
    fun quantityLowerThanCurrentThrowsAnError() {
        val cart = ShoppingCart()
        cart.addItem("Item 1", 2)
        shouldThrow<IllegalArgumentException> { cart.removeItem("Item 1", 5) }
    }
    // --------------

    // Calculate Total Price tests
    // --------------
    @Test
    fun calculateTotalPriceOfEmptyList() {
        val cart = ShoppingCart()
        val items = mapOf<String, Int>()
        items.forEach { cart.addItem(it.key, it.value) }
        val itemPrices = mapOf<String, Double>()
        cart.calculateTotalPrice(itemPrices) shouldBe 0.0
    }

    @Test
    fun calculateTotalPriceOfAList() {
        val cart = ShoppingCart()
        val items = mapOf("Item 1" to 2, "Item 2" to 3, "Item 3" to 4)
        items.forEach { cart.addItem(it.key, it.value) }
        val itemPrices = mapOf("Item 1" to 1.2, "Item 2" to 2.6, "Item 3" to 3.1)
        cart.calculateTotalPrice(itemPrices) shouldBe 22.6
    }

    @Test
    fun calculateTotalPriceOfAListWhenSomePricesAreMissing() {
        val cart = ShoppingCart()
        val items = mapOf("Item 1" to 2, "Item 2" to 3, "Item 3" to 4)
        items.forEach { cart.addItem(it.key, it.value) }
        val itemPrices = mapOf("Item 1" to 1.2, "Item 2" to 2.5)
        cart.calculateTotalPrice(itemPrices) shouldBe 9.9
    }
    // --------------

}