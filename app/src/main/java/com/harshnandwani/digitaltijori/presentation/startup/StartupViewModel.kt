package com.harshnandwani.digitaltijori.presentation.startup

import android.content.ContentResolver
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshnandwani.digitaltijori.domain.use_case.auth.SetAuthenticatedUseCase
import com.harshnandwani.digitaltijori.domain.use_case.auth.ShouldAuthenticateUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.IsEligibleForRestoreUseCase
import com.harshnandwani.digitaltijori.domain.use_case.backup_restore.RestoreUseCase
import com.harshnandwani.digitaltijori.domain.use_case.preference.SetAppOpenedUseCase
import com.harshnandwani.digitaltijori.presentation.startup.util.RestoreStatus
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupEvent
import com.harshnandwani.digitaltijori.presentation.startup.util.StartupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val isEligibleForRestore: IsEligibleForRestoreUseCase,
    private val contentResolver: ContentResolver,
    private val restore: RestoreUseCase,
    private val setAppOpened: SetAppOpenedUseCase,
    private val shouldAuthenticate: ShouldAuthenticateUseCase,
    private val setAuthenticated: SetAuthenticatedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StartupState())
    val state: StateFlow<StartupState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    restoreEligible = isEligibleForRestore(),
                    shouldAuthenticate = shouldAuthenticate()
                )
            }
            if (_state.value.shouldAuthenticate == false)
                _state.update { it.copy(authSuccessful = true) }
        }
    }

    fun onEvent(event: StartupEvent) {
        when (event) {
            StartupEvent.AuthenticationUnavailable -> {
                _state.update { it.copy(authAvailable = false) }
            }

            StartupEvent.AuthenticatedSuccessfully -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setAuthenticated()
                    _state.update { it.copy(authSuccessful = true) }
                }
            }

            is StartupEvent.BackupFileSelected -> {
                _state.update {
                    it.copy(restoreStatus = RestoreStatus.NOT_STARTED, backupFileUri = event.uri)
                }
                readBackupFileName()
            }

            is StartupEvent.EnteredSecretKey -> {
                _state.update {
                    it.copy(restoreStatus = RestoreStatus.NOT_STARTED, secretKey = event.key)
                }
            }

            StartupEvent.StartRestore -> {
                if (_state.value.backupFileUri == null) {
                    _state.update {
                        it.copy(
                            restoreStatus = RestoreStatus.FAILED,
                            restoreErrorMessage = "Please select backup file"
                        )
                    }
                    return
                }
                if (_state.value.secretKey.length < 8) {
                    _state.update {
                        it.copy(
                            restoreStatus = RestoreStatus.FAILED,
                            restoreErrorMessage = "Secret key should be of length more than 8"
                        )
                    }
                    return
                }
                _state.update { it.copy(restoreStatus = RestoreStatus.STARTED) }
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        restore(readBackupFileContent(), _state.value.secretKey)
                        _state.update { it.copy(restoreStatus = RestoreStatus.SUCCESS) }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _state.update {
                            it.copy(
                                backupFileUri = null,
                                backupFileName = null,
                                secretKey = "",
                                restoreStatus = RestoreStatus.FAILED,
                                restoreErrorMessage = "Restore failed, either secret key is incorrect or wrong backup file is chosen"
                            )
                        }
                    }
                }
            }

            StartupEvent.RestoreDoneOrSkipped -> {
                viewModelScope.launch(Dispatchers.IO) { setAppOpened() }
            }
        }
    }


    private fun readBackupFileName() {
        val uri = _state.value.backupFileUri!!
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val fileName = cursor?.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        _state.update { it.copy(backupFileName = fileName) }
        cursor?.close()
    }

    private fun readBackupFileContent(): String {
        val uri = _state.value.backupFileUri!!
        val inputStream = contentResolver.openInputStream(uri)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val fileContents = StringBuilder()
        var s: String?
        while (bufferedReader.readLine().also { s = it } != null) {
            fileContents.append(s)
        }
        bufferedReader.close()
        inputStream?.close()

        return fileContents.toString()
    }

}
