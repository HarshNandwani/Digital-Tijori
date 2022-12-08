package com.harshnandwani.digitaltijori.data.local

import androidx.room.TypeConverter
import com.harshnandwani.digitaltijori.domain.util.ColorScheme

class TypeConverters {

    @TypeConverter
    fun colorSchemeToString(colorScheme: ColorScheme): String {
        return "${colorScheme.bgColorFrom},${colorScheme.bgColorTo},${colorScheme.textColor}"
    }

    @TypeConverter
    fun stringToColorScheme(colorSchemeString: String): ColorScheme {
        val colors = colorSchemeString.split(",")
        return ColorScheme(colors[0].toInt(), colors[1].toInt(), colors[2].toInt())
    }

}
