package com.harshnandwani.digitaltijori.presentation.credential.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.company.GetCompaniesHavingCredentialsUseCase
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialState
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialSubmitResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEditCredentialViewModel @Inject constructor(
    private var getCompaniesHavingCredentialsUseCase: GetCompaniesHavingCredentialsUseCase
) : ViewModel() {

    private var getAllEntitiesJob: Job? = null

    private val _state = mutableStateOf(CredentialState())
    val state: State<CredentialState> = _state

    private val _eventFlow = MutableSharedFlow<CredentialSubmitResultEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllEntities()
    }

    private fun getAllEntities() {
        getAllEntitiesJob?.cancel()
        getAllEntitiesJob = getCompaniesHavingCredentialsUseCase()
            .onEach { entities ->
                _state.value = state.value.copy(
                    allEntities = entities
                )
            }
            .launchIn(viewModelScope)
    }
}
