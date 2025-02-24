/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.zut.kakit.cloudchat.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.zut.kakit.cloudchat.ui.chatdatamodel.ChatDataModelRoute
import edu.zut.kakit.cloudchat.ui.chatdatamodel.ChatDataModelScreen
import edu.zut.kakit.cloudchat.ui.signin.SignInRoute
import edu.zut.kakit.cloudchat.ui.signin.SignInScreen
import edu.zut.kakit.cloudchat.ui.singup.SignUpRoute
import edu.zut.kakit.cloudchat.ui.singup.SignUpScreen

@Composable
fun MainNavigation(

) {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = SignInRoute) {
        composable<ChatDataModelRoute> { ChatDataModelScreen(modifier = Modifier.padding(16.dp)) }
        // TODO: Add more destinations
        composable<SignInRoute> { SignInScreen(
            openHomeScreen = {
                navController.navigate(ChatDataModelRoute) { launchSingleTop = true }
            },
            openSignUpScreen = {
                navController.navigate(SignUpRoute) { launchSingleTop = true }
            },
        ) }
        composable<SignUpRoute> { SignUpScreen(
            openHomeScreen = {
                navController.navigate(ChatDataModelRoute) { launchSingleTop = true }
            },
        ) }
    }
}
