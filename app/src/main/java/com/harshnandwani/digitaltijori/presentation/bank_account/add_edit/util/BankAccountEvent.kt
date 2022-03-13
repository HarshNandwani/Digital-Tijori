package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit.util

import com.harshnandwani.digitaltijori.domain.model.Company

sealed class BankAccountEvent {
    data class SelectBank(val bank: Company): BankAccountEvent()
    data class EnteredAccountNumber(val accountNumber: String): BankAccountEvent()
    data class EnteredIFSC(val ifsc :String): BankAccountEvent()
    data class EnteredHolderName(val holderName :String): BankAccountEvent()
    data class EnteredPhoneNumber(val phoneNumber :String): BankAccountEvent()
    data class EnteredAlias(val alias :String): BankAccountEvent()
    object BankAccountSubmit: BankAccountEvent()
}
