package edu.zut.kakit.cloudchat.ui.signin


import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.zut.kakit.cloudchat.R
import edu.zut.kakit.cloudchat.ui.shared.StandardButton
import edu.zut.kakit.cloudchat.ui.theme.DarkBlue
import edu.zut.kakit.cloudchat.ui.theme.MyApplicationTheme
import kotlinx.serialization.Serializable

@Serializable
object SignInRoute




@Composable
fun SignInScreen(
    openHomeScreen: () -> Unit,
    openSignUpScreen: () -> Unit,
    //showErrorSnackbar: (ErrorMessage) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val shouldRestartApp by viewModel.shouldRestartApp.collectAsStateWithLifecycle()

    if (shouldRestartApp) {
        openHomeScreen()
    } else {
        SignInScreenContent(
            openSignUpScreen = openSignUpScreen,
            signIn = viewModel::signIn,
//            showErrorSnackbar = showErrorSnackbar
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenContent(
    openSignUpScreen: () -> Unit,
    signIn: (String, String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // App Logo
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Image(
//                    modifier = Modifier.size(88.dp),
//                    painter = painterResource(id = R.mipmap.ic_launcher),
//                    contentDescription = "App logo"
//                )
            }

            // Form
            Column(
                modifier = Modifier.padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Spacer(Modifier.size(32.dp))

                StandardButton(
                    label = R.string.sign_in_with_email,
                    onButtonClick = {
                        signIn(email, password)
                    }
                )

                Spacer(Modifier.size(16.dp))

                //TODO: Uncomment line below when Google Authentication is implemented
                //AuthWithGoogleButton(R.string.sign_in_with_google) { }
            }

            // Sign Up Text
            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = openSignUpScreen) {
                    Text(
                        text = stringResource(R.string.sign_up_text),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = DarkBlue
                    )
                }
            }
        }
    }
}

//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun SignInScreenContent(
//    openSignUpScreen: () -> Unit,
////    signIn: (String, String, (ErrorMessage) -> Unit) -> Unit,
////    showErrorSnackbar: (ErrorMessage) -> Unit
//) {
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//
//    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
//    ) { innerPadding ->
//        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
//            val (appLogo, form, signUpText) = createRefs()
//
//            Column(
//                modifier = Modifier
//                    .constrainAs(appLogo) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Spacer(Modifier.size(24.dp))
//
//                Image(
//                    modifier = Modifier.size(88.dp),
//                    painter = painterResource(id = R.mipmap.ic_launcher_round),
//                    contentDescription = "App logo"
//                )
//
//                Spacer(Modifier.size(24.dp))
//            }
//
//            Column(
//                modifier = Modifier
//                    .constrainAs(form) {
//                        top.linkTo(appLogo.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Spacer(Modifier.size(24.dp))
//
//                OutlinedTextField(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp),
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text(stringResource(R.string.email)) }
//                )
//
//                Spacer(Modifier.size(16.dp))
//
//                OutlinedTextField(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp),
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text(stringResource(R.string.password)) },
//                    visualTransformation = PasswordVisualTransformation()
//                )
//
//                Spacer(Modifier.size(32.dp))
//
//                StandardButton(
//                    label = R.string.sign_in_with_email,
//                    onButtonClick = {
//                        signIn(email, password, showErrorSnackbar)
//                    }
//                )
//
//                Spacer(Modifier.size(16.dp))
//
//                //TODO: Uncomment line below when Google Authentication is implemented
//                //AuthWithGoogleButton(R.string.sign_in_with_google) { }
//            }
//
//            Column(
//                modifier = Modifier
//                    .constrainAs(signUpText) {
//                        bottom.linkTo(parent.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TextButton(onClick = openSignUpScreen) {
//                    Text(
//                        text = stringResource(R.string.sign_up_text),
//                        textAlign = TextAlign.Center,
//                        fontSize = 16.sp,
//                        color = DarkBlue
//                    )
//                }
//
//                Spacer(Modifier.size(24.dp))
//            }
//        }
//    }
//}

@Composable
@Preview(showSystemUi = true)
fun SignInScreenPreview() {
    MyApplicationTheme(darkTheme = true) {
        SignInScreenContent(
            openSignUpScreen = {},
            signIn = { _, _ -> },
            //showErrorSnackbar = {}
        )
    }
}

