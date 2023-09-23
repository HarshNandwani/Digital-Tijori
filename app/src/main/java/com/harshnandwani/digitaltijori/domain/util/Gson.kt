package com.harshnandwani.digitaltijori.domain.util

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gsonExcludingResId: Gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.name.equals("iconResId") || f?.name.equals("logoResId")
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

}).create()
