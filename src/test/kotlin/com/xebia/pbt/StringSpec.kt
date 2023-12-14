package com.xebia.pbt

import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class StringSpec {
    
    @Test
    fun stringReversal() {
        runTest {
            checkAll<String> { s ->
                s.reversed().reversed() shouldBe s
            }
        }
    }

    @Test
    fun stringReversalLength() {
        runTest {
            checkAll<String> { s ->
                s.reversed().length shouldBe s.length
            }
        }
    }
    
}