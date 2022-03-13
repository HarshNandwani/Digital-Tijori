package com.harshnandwani.digitaltijori.presentation.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.harshnandwani.digitaltijori.presentation.home.util.BottomHomeBar
import com.harshnandwani.digitaltijori.presentation.home.util.HomeNavGraph

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = { BottomHomeBar(navController) }
    ) {
        HomeNavGraph(navController)
    }
}
