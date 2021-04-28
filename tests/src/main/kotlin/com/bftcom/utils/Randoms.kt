package com.bftcom.utils

import com.google.common.collect.ImmutableList
import java.math.BigInteger
import java.security.SecureRandom
import kotlin.random.Random

object Randoms {
    private val lowercase = "abcdefghijklmnopqrstuvwxyz"
    private val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lowercaseCyrillic = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    private val uppercaseCyrillic = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

    fun letters(length: Int): String {
        return generateInternal(length, ImmutableList.of(lowercase, uppercase))
    }

    fun ruLetters(length: Int): String {
        return generateInternal(length, ImmutableList.of(lowercaseCyrillic, uppercaseCyrillic))
    }

    private fun generateInternal(length: Int, ranges: List<String>): String {
        val result = StringBuilder()
        while (result.length < length) {
            val which = Random.nextInt(ranges.size)
            result.append(randomFrom(ranges[which]))
        }
        return result.toString()
    }

    private fun randomFrom(source: String): String {
        val i = Random.nextInt(source.length)
        return source.substring(i, i + 1)
    }

    @Synchronized
    internal fun getUnique(length: Int): String {
        val secureRandom = SecureRandom()
        return BigInteger(500, secureRandom).toString().substring(0, length)
    }
}

fun String.withRandomPostfix(length: Int = 10): String {
    return this + Randoms.getUnique(length)
}