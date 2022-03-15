package com.harshnandwani.digitaltijori.presentation.util

import android.content.Intent
import com.harshnandwani.digitaltijori.domain.model.BankAccount

class BankAccountHelperFunctions {

    companion object {

        fun getShareIntent(bankName: String, account: BankAccount): Intent {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getFormattedBankAccount(bankName, account))
                type = "text/plain"
            }
            return Intent.createChooser(sendIntent, null)
        }

        private fun getFormattedBankAccount(bankName: String, account: BankAccount): String {
            return "$bankName account details: \n" +
                    "Account Holder Name: ${account.holderName} \n" +
                    "Account number: ${account.accountNumber} \n" +
                    "IFSC: ${account.ifsc}"
        }
    }
}
