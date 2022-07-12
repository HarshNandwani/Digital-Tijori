package com.harshnandwani.digitaltijori.presentation.credential.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.company.GetCompaniesHavingCredentialsUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.AddCredentialUseCase
import com.harshnandwani.digitaltijori.domain.use_case.credential.UpdateCredentialUseCase
import com.harshnandwani.digitaltijori.domain.util.InvalidCredentialException
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialEvent
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialState
import com.harshnandwani.digitaltijori.presentation.credential.add_edit.util.CredentialSubmitResultEvent
import com.harshnandwani.digitaltijori.presentation.util.Parameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCredentialViewModel @Inject constructor(
    private val getCompaniesHavingCredentialsUseCase: GetCompaniesHavingCredentialsUseCase,
    private val addCredentialUseCase: AddCredentialUseCase,
    private val updateCredentialUseCase: UpdateCredentialUseCase
) : ViewModel() {

    private var getAllEntitiesJob: Job? = null

    private val _state = mutableStateOf(CredentialState())
    val state: State<CredentialState> = _state

    private val _eventFlow = MutableSharedFlow<CredentialSubmitResultEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllEntities()
    }


    fun onEvent(event: CredentialEvent) {
        when (event) {
            is CredentialEvent.SelectEntity -> {
                _state.value = state.value.copy(
                    selectedEntity = event.entity
                )
                _state.value.credential.value = state.value.credential.value.copy(
                    companyId = event.entity.companyId
                )
            }
            is CredentialEvent.LinkToAccount -> {
                _state.value.credential.value = state.value.credential.value.copy(
                    isLinkedToBank = true,
                    bankAccountId = event.linkedAccountId
                )
            }
            is CredentialEvent.EnteredUsername -> {
                _state.value.credential.value = state.value.credential.value.copy(
                    username = event.username
                )
            }
            is CredentialEvent.EnteredPassword -> {
                _state.value.credential.value = state.value.credential.value.copy(
                    password = event.password
                )
            }
            is CredentialEvent.EnteredAlias -> {
                _state.value.credential.value = state.value.credential.value.copy(
                    alias = event.alias
                )
            }
            is CredentialEvent.CredentialSubmit -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val credential = _state.value.credential.value
                    try {
                        if (_state.value.mode == Parameters.VAL_MODE_ADD) {
                            addCredentialUseCase(credential)
                        } else {
                            if (_state.value.previouslyEnteredCredential == credential) {
                                _eventFlow.emit(CredentialSubmitResultEvent.ShowError("No values changed"))
                                return@launch
                            }
                            updateCredentialUseCase(credential)
                        }
                        _eventFlow.emit(CredentialSubmitResultEvent.CredentialSaved)
                    } catch (e: InvalidCredentialException) {
                        _eventFlow.emit(
                            CredentialSubmitResultEvent.ShowError(
                                e.message ?: "Cannot save credentials"
                            )
                        )
                    }
                }
            }
            is CredentialEvent.ChangeToEditMode -> {
                getAllEntitiesJob?.cancel()
                _state.value = state.value.copy(
                    mode = Parameters.VAL_MODE_EDIT,
                    selectedEntity = event.linkedEntity
                )
                _state.value.credential.value = event.credential
                _state.value.previouslyEnteredCredential = event.credential
            }
        }
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
