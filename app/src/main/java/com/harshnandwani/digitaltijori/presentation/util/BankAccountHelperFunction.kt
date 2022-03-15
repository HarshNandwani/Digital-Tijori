package com.harshnandwani.digitaltijori.presentation.util

import com.harshnandwani.digitaltijori.domain.model.BankAccount

class BankAccountHelperFunction {

    companion object {
        fun getFormattedBankAccount(bankName: String, account: BankAccount): String {
            return "$bankName account details: \n" +
                    "Account Holder Name: ${account.holderName} \n" +
                    "Account number: ${account.accountNumber} \n" +
                    "IFSC: ${account.ifsc}"
        }
    }
}
