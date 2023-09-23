package com.harshnandwani.digitaltijori.domain.util

import java.math.BigInteger

fun toHexString(hash: ByteArray?): String {
    val number = BigInteger(1, hash)
    val hexString = StringBuilder(number.toString(16))
    while (hexString.length < 64) {
        hexString.insert(0, '0')
    }
    return hexString.toString()
}
