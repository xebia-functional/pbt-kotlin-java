package com.xebia.pbt.cart

import com.xebia.pbt.cart.impl2.ShoppingCart
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.*
import io.kotest.property.checkAll
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import java.math.BigDecimal
import java.math.RoundingMode

class ShoppingCartPBTSpec {

    data class CartItemValue(val name: String, val quantity: Int, val price: BigDecimal, val total: BigDecimal)
    data class CartItemWithRemove(val value: CartItemValue, val remove: Int)

    private val cartItemArb: Arb<CartItemValue> = arbitrary {
        val uniqueName = Arb.uuid(allowNilValue = false).map { it.toString() }.bind()
        val quantity = Arb.int(1, 10).bind()
        val price = Arb.double(0.01, 100.00).map { BigDecimal.valueOf(it).setScale(2, RoundingMode.HALF_EVEN) }.bind()
        CartItemValue(uniqueName, quantity, price, price.multiply(BigDecimal(quantity)))
    }

    private val cartItemWithRemoveArb: Arb<CartItemWithRemove> = arbitrary {
        val cartItem = cartItemArb.bind()
        val toRemove = Arb.int(0, cartItem.quantity).bind()
        val newTotal = cartItem.total - cartItem.price.multiply(BigDecimal(toRemove))
        CartItemWithRemove(cartItem.copy(total = newTotal), toRemove)
    }

    private val cartItemListArb: Arb<List<CartItemValue>> =
        Arb.list(cartItemArb, 0 .. 15)

    private val cartItemWithRemoveListArb: Arb<List<CartItemWithRemove>> =
        Arb.list(cartItemWithRemoveArb, 0 .. 15)

    // Add Item tests
    // --------------
    @Test
    fun quantityNeedsToBeGreaterThanZero() {
        runTest {
            checkAll(
                Arb.string(),
                Arb.int(max = 0),
            ) { name, quantity ->
                val cart = ShoppingCart()
                shouldThrow<IllegalArgumentException> { cart.addItem(name, quantity) }
            }
        }
    }
    // --------------

    // Calculate Total Price tests
    // --------------
    @Test
    fun calculateTotalPrice() {
        runTest {
            checkAll(cartItemListArb) { items ->
                val cart = ShoppingCart()
                val expectedAmount = items.sumOf { it.total }
                items.forEach { cart.addItem(it.name, it.quantity) }
                val itemPrices = items.associate { Pair(it.name, it.price.toDouble()) }
                cart.calculateTotalPrice(itemPrices) shouldBe expectedAmount.toDouble()
            }
        }
    }

    @Test
    fun calculateTotalPriceImproved() {
        runTest {
            checkAll(cartItemListArb, cartItemListArb) { items, itemsWithoutPrice ->
                val cart = ShoppingCart()
                val expectedAmount = items.sumOf { it.total }
                items.forEach { cart.addItem(it.name, it.quantity) }
                itemsWithoutPrice.forEach { cart.addItem(it.name, it.quantity) }
                val itemPrices = items.associate { Pair(it.name, it.price.toDouble()) }
                cart.calculateTotalPrice(itemPrices) shouldBe expectedAmount.toDouble()
            }
        }
    }
    // --------------

    // Remove Item tests
    // --------------
    @Test
    fun removingItemsFromCart() {
        runTest {
            checkAll(cartItemWithRemoveListArb) { items ->
                val cart = ShoppingCart()
                items.forEach { cart.addItem(it.value.name, it.value.quantity) }
                items.filter { it.remove > 0 }.forEach { cart.removeItem(it.value.name, it.remove) }
                val itemPrices = items.associate { Pair(it.value.name, it.value.price.toDouble()) }
                val expectedAmount = items.sumOf { it.value.total }
                val actualAmount = cart.calculateTotalPrice(itemPrices)
                actualAmount shouldBe expectedAmount.toDouble()
            }
        }
    }

}