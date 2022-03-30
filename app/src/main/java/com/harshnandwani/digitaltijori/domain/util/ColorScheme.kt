package com.harshnandwani.digitaltijori.domain.util

import java.io.Serializable

data class ColorScheme(
    val bgColorFrom: Int,
    val bgColorTo: Int,
    val textColor: Int = 0
) : Serializable
