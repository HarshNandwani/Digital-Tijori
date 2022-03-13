package com.harshnandwani.digitaltijori.presentation.bank_account.add_edit

import androidx.lifecycle.ViewModel
import com.harshnandwani.digitaltijori.domain.use_case.bank_account.AddBankAccountUseCase
import com.harshnandwani.digitaltijori.domain.use_case.company.GetAllBanksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditBankAccountViewModel @Inject constructor(
    private val getAllBanksUseCase: GetAllBanksUseCase,
    private val addBankAccountUseCase: AddBankAccountUseCase
) : ViewModel() {

}
