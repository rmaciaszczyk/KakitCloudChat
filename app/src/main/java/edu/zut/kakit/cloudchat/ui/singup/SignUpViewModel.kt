package edu.zut.kakit.cloudchat.ui.singup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.zut.kakit.cloudchat.data.AuthRepository
import edu.zut.kakit.cloudchat.data.model.SnackbarData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?>
        get() = _snackbarData.asStateFlow()

    fun signUp(
        email: String,
        password: String,
        repeatPassword: String,
    ) {
        if (!email.isValidEmail()) {
            _snackbarData.update { SnackbarData(message = "Invalid email")  }
            return
        }

        if (!password.isValidPassword()) {
            _snackbarData.update { SnackbarData(message =  "Invalid password") }
            return
        }

        if (password != repeatPassword) {
            _snackbarData.update { SnackbarData(message = "Passwords do not match") }
            return
        }

        viewModelScope.launch {
            try {
                authRepository.signUp(email, password)
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                _snackbarData.update { SnackbarData(message = e.message ?: "Unknown error") }
            }
        }
    }
    fun clearSnackbar() {
        _snackbarData.update { null }
    }
}