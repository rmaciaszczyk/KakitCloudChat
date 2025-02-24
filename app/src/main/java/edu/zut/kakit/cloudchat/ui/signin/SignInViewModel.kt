package edu.zut.kakit.cloudchat.ui.signin


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
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
)
    : ViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    private val _snackbarData = MutableStateFlow<SnackbarData?>(null)
    val snackbarData: StateFlow<SnackbarData?>
        get() = _snackbarData.asStateFlow()


    fun signIn(
        email: String,
        password: String,

    ) {
        viewModelScope.launch {
            try {
                authRepository.signIn(email, password)
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                _snackbarData.update { SnackbarData(message = e.message ?: "Unknown error") }
            }

        }
    }

    fun anonymousSignIn() {
        viewModelScope.launch {
            try {
                authRepository.createGuestAccount()
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                // Handle the error here, e.g., show an error message
            }
        }
    }
    fun clearSnackbar() {
        _snackbarData.update { null }
    }

}