package edu.zut.kakit.cloudchat.ui.singup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.zut.kakit.cloudchat.R
import edu.zut.kakit.cloudchat.ui.shared.StandardButton
import edu.zut.kakit.cloudchat.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
object SignUpRoute

@Composable
fun SignUpScreen(
    openHomeScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val shouldRestartApp by viewModel.shouldRestartApp.collectAsStateWithLifecycle()
    val snackbarData by viewModel.snackbarData.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarData) {
        if (snackbarData != null) {
            snackbarHostState.showSnackbar(
                message = snackbarData!!.message,
                actionLabel = snackbarData!!.actionLabel,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSnackbar()
        }
    }

    if (shouldRestartApp) {
        openHomeScreen()
    } else {
        SignUpScreenContent(
            signUp = viewModel::signUp,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignUpScreenContent(
    signUp: (String, String, String) -> Unit,
    snackbarHostState: SnackbarHostState
    
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // App Logo
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

//                Image(
//                    modifier = Modifier.size(88.dp),
//                    painter = painterResource(id = R.mipmap.ic_launcher_round),
//                    contentDescription = "App logo"
//                )

                Spacer(Modifier.size(24.dp))
            }

            // Form
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.size(24.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email)) }
                )

                Spacer(Modifier.size(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.size(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    label = { Text(stringResource(R.string.repeat_password)) },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.size(32.dp))

                StandardButton(
                    label = R.string.sign_up_with_email,
                    onButtonClick = {
                        signUp(
                            email,
                            password,
                            repeatPassword,
                            //showErrorSnackbar
                        )
                    }
                )

                Spacer(Modifier.size(16.dp))
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun SignUpScreenPreview() {
    MyApplicationTheme(darkTheme = true) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope() // Add a coroutine scope

        // Simulate an error to show the Snackbar
        LaunchedEffect(key1 = true) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "This is a preview Snackbar!",
                    duration = SnackbarDuration.Short
                )
            }
        }

        SignUpScreenContent(
            signUp = { _, _, _ -> },
            snackbarHostState = snackbarHostState
        )
    }
}
