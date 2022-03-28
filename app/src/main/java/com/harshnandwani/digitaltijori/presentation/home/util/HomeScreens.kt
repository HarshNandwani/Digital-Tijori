package com.harshnandwani.digitaltijori.presentation.home.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeScreens(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object BankAccountsList : HomeScreens(
        route = "Accounts",
        title = "Banks",
        icon = Icons.Default.AccountBalance
    )
    object CardsList : HomeScreens(
        route = "Cards",
        title = "Cards",
        icon = Icons.Default.CreditCard
    )
    object CredentialsList : HomeScreens(
        route = "Credentials",
        title = "Credentials",
        icon = Icons.Default.Lock
    )
}
