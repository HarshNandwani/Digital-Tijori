package com.harshnandwani.digitaltijori.presentation.credential.detailed_view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.credential.DeleteCredentialUseCase
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util.DetailedCredentialEvent
import com.harshnandwani.digitaltijori.presentation.credential.detailed_view.util.DetailedCredentialState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedCredentialViewModel @Inject constructor(
    private val deleteCredentialUseCase: DeleteCredentialUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DetailedCredentialState())
    val state: State<DetailedCredentialState> = _state

    fun onEvent(event: DetailedCredentialEvent) {
        when (event) {
            is DetailedCredentialEvent.LoadEntity -> {
                _state.value = state.value.copy(
                    entity = event.entity
                )
            }
            is DetailedCredentialEvent.LoadCredential -> {
                _state.value = state.value.copy(
                    credential = event.credential
                )
            }
            is DetailedCredentialEvent.DeleteCredential -> {
                viewModelScope.launch {
                    deleteCredentialUseCase(_state.value.credential)
                }
            }
        }
    }

}
