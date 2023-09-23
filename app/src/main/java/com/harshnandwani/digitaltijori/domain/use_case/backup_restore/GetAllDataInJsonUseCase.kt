package com.harshnandwani.digitaltijori.domain.use_case.backup_restore

import com.harshnandwani.digitaltijori.domain.model.AllData
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.GetAllAccountsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.card.GetAllCardsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.GetAllCredentialsUseCase
import com.harshnandwani.digitaltijori.domain.util.gsonExcludingResId
import kotlinx.coroutines.flow.first

class GetAllDataInJsonUseCase(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getAllCredentialsUseCase: GetAllCredentialsUseCase
) {
    suspend operator fun invoke(): String {
        val allData = AllData(
            getAllAccountsUseCase().first(),
            getAllCardsUseCase().first(),
            getAllCredentialsUseCase().first()
        )

        return gsonExcludingResId.toJson(allData)
    }
}
