package edu.zut.kakit.cloudchat.ui.signin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.zut.kakit.cloudchat.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun signIn(
        email: String,
        password: String,
        //showErrorSnackbar: (ErrorMessage) -> Unit
    ) {
        //launchCatching(showErrorSnackbar)

        viewModelScope.launch {
            //launchCatching(showErrorSnackbar)
            try {
                authRepository.signIn(email, password)
                _shouldRestartApp.value = true
            } catch (e: Exception) {
                // Handle the error here, e.g., show an error message
                //showErrorSnackbar(ErrorMessage(e.message ?: "An error occurred"))
            }
        }
    }
}