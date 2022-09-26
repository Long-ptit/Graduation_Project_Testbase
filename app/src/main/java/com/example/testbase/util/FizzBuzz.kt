package com.example.testbase.util

import java.util.*

class FizzBuzz {

    fun execute(ints: IntArray) : String{
        var strResult = ""
        for(i in ints) {
            strResult += "${processNumber(i)}, "
        }
        return strResult.substring(0, strResult.length - 2)
    }

    fun processNumber(num: Int) : String {


        if (isMultipleOf3(num) && isMultipleOf5(num)) {
            return "FizzBuzz";
        }

        if (num % 3 == 0) {
            return "Fizz";
        }

        if (num % 5 == 0) {
            return "Buzz";
        }

        return num.toString()
       // return "1"
    }

    private fun isMultipleOf5(number: Int): Boolean {
        return number % 5 == 0
    }

    private fun isMultipleOf3(number: Int): Boolean {
        return number % 3 == 0
    }



}