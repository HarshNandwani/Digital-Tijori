package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company
import com.harshnandwani.digitaltijori.presentation.util.Constants.emptyBankAccount
import com.harshnandwani.digitaltijori.presentation.util.Parameters

data class BankAccountState(
    val allBanks: List<Company> = emptyList(),
    val selectedBank: Company? = null,
    val mode: String = Parameters.VAL_MODE_ADD,
    val bankAccount: MutableState<BankAccount> = mutableStateOf(emptyBankAccount),
    var previouslyEnteredBankAccount: BankAccount = emptyBankAccount
)
