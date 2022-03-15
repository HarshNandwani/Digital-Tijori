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
import com.harshnandwani.digitaltijori.presentation.home.components.AppBarWithSearchView
import com.harshnandwani.digitaltijori.presentation.home.components.BottomHomeBar
import com.harshnandwani.digitaltijori.presentation.home.components.HomeNavGraph
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppBarWithSearchView(
                titleText = "Digital Tijori",
                searchText = viewModel.state.value.searchText,
                onSearchTextChange = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchTextChanged(it))
                },
                onSearchImeClicked = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchDone)
                },
                onCloseClicked = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchDone)
                }
            )         
        },
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
        HomeNavGraph(viewModel, navController)
    }
}
