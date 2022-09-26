package com.example.testbase

import com.example.testbase.util.FizzBuzz
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     * The exercise is complete when the following input:

    [1, 2, 3, 5, 6, 10, 15, 30]
    results in the following output:

    "1, 2, Fizz, Buzz, Fizz, Buzz, FizzBuzz, FizzBuzz"
     */

    @Test
    fun testProcessNumber() {
        val fizzBuzz = FizzBuzz()
//        assertEquals(fizzBuzz.processNumber(1), "1")
//        assertEquals(fizzBuzz.processNumber(2), "2")
//        assertEquals(fizzBuzz.processNumber(3), "Fizz")
//        assertEquals(fizzBuzz.execute(intArrayOf(1,2,3,4)), "1, 2, Fizz, 4")
        val strResult = "1, 2, Fizz, Buzz, Fizz, Buzz, FizzBuzz, FizzBuzz"
        assertEquals(fizzBuzz.processNumber(1), "1")
        assertEquals(fizzBuzz.processNumber(2), "2")
        assertEquals(fizzBuzz.execute(intArrayOf(1, 2, 3, 5, 6, 10, 15, 30)), strResult)

    }


}