package com.harshnandwani.digitaltijori.presentation.home.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.harshnandwani.digitaltijori.presentation.bank_account.list.BankAccountsListScreen
import com.harshnandwani.digitaltijori.presentation.card.list.CardsListScreen
import com.harshnandwani.digitaltijori.presentation.credential.list.CredentialsListScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeScreens.BankAccountsList.route
    ) {
        composable(route = HomeScreens.BankAccountsList.route){
            BankAccountsListScreen()
        }
        composable(route = HomeScreens.CardsList.route){
            CardsListScreen()
        }
        composable(route = HomeScreens.CredentialsList.route){
            CredentialsListScreen()
        }
    }
}
