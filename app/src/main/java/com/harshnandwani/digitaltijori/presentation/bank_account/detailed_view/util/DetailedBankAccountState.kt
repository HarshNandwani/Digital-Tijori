package com.harshnandwani.digitaltijori.presentation.bank_account.detailed_view.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount
import com.harshnandwani.digitaltijori.domain.model.Company

data class DetailedBankAccountState(
    val bank: Company = Company(-1, "", false, false, false, -1,-1),
    val account: BankAccount = BankAccount(0,-1,"","","","","")
)
