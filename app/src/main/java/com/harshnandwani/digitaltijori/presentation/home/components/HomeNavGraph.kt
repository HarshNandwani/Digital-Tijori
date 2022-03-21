package com.harshnandwani.digitaltijori.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harshnandwani.digitaltijori.presentation.bank_account.list.BankAccountsListScreen
import com.harshnandwani.digitaltijori.presentation.card.list.CardsListScreen
import com.harshnandwani.digitaltijori.presentation.credential.list.CredentialsListScreen
import com.harshnandwani.digitaltijori.presentation.home.HomeViewModel
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreenEvent
import com.harshnandwani.digitaltijori.presentation.home.util.HomeScreens

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeNavGraph(viewModel: HomeViewModel, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreens.BankAccountsList.route
    ) {
        composable(route = HomeScreens.BankAccountsList.route) {
            viewModel.onEvent(HomeScreenEvent.OnPageChanged(HomeScreens.BankAccountsList.route))
            BankAccountsListScreen(viewModel)
        }
        composable(route = HomeScreens.CardsList.route) {
            viewModel.onEvent(HomeScreenEvent.OnPageChanged(HomeScreens.CardsList.route))
            CardsListScreen()
        }
        composable(route = HomeScreens.CredentialsList.route) {
            viewModel.onEvent(HomeScreenEvent.OnPageChanged(HomeScreens.CredentialsList.route))
            CredentialsListScreen(viewModel)
        }
    }
}
