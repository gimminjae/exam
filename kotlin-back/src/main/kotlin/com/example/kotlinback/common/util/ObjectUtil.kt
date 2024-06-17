package com.example.kotlinback.common.util

import java.text.DecimalFormat
import java.util.*

class ObjectUtil {
    companion object {
        private const val CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        private const val CHARACTERS_ONLY_NUMBER = "0123456789"
        private const val LENGTH = 8

        fun generateRandomString(): String {
            return generateRandomStringByInput(CHARACTERS)
        }

        fun <T> isNullExceptionElseReturnObject(optionalT: Optional<T>): T {
            val t = optionalT.orElse(null)
            if (t == null) {
                throw NullPointerException("데이터가 존재하지 않습니다.")
            }
            return t
        }

        fun <T> isNullExceptionElseReturnObject(optionalT: Optional<T>, message: String): T {
            return optionalT.orElse(null) ?: throw NullPointerException(message)
        }

        fun divide(numerator: Float, denominator: Float): String {
            require(denominator != 0f) { "Denominator cannot be zero." }
            val result = numerator / denominator
            val decimalFormat = DecimalFormat("#.####") // Format to 4 decimal places
            return decimalFormat.format(result.toDouble())
        }

        fun generateRandomStringOnlyNumber(): String {
            return generateRandomStringByInput(CHARACTERS_ONLY_NUMBER)
        }

        private fun generateRandomStringByInput(stringType: String): String {
            val sb = StringBuilder(LENGTH)
            val random = Random()
            for (i in 0 until LENGTH) {
                val randomIndex = random.nextInt(stringType.length)
                val randomChar = stringType[randomIndex]
                sb.append(randomChar)
            }
            return sb.toString()
        }
    }
}
