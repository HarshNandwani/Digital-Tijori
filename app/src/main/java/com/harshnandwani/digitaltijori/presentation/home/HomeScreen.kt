package com.harshnandwani.digitaltijori.presentation.home

import android.content.Intent
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.AddEditBankAccountActivity
import com.harshnandwani.digitaltijori.presentation.home.util.BottomHomeBar
import com.harshnandwani.digitaltijori.presentation.home.util.HomeNavGraph
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = { BottomHomeBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (navController.currentBackStackEntry?.destination?.route) {
                        HomeScreens.BankAccountsList.route -> {
                            Intent(context, AddEditBankAccountActivity::class.java).apply {
                                context.startActivity(this)
                            }
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        HomeNavGraph(navController)
    }
}
