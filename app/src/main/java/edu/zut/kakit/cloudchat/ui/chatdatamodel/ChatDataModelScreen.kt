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

package edu.zut.kakit.cloudchat.ui.chatdatamodel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import edu.zut.kakit.cloudchat.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.zut.kakit.cloudchat.data.firebase.ChatDataModel
import kotlinx.serialization.Serializable

@Composable
fun ChatDataModelScreen(modifier: Modifier = Modifier, viewModel: ChatDataModelViewModel = hiltViewModel()) {
    val items by viewModel.messages.collectAsStateWithLifecycle(emptyList())
    //if (items is ChatDataModelUiState.Success) {
        ChatDataModelScreen(
            //items = (items as ChatDataModelUiState.Success).data,
            items = items,
            onSave = viewModel::sendMessage,
            modifier = modifier
        )
    //}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatDataModelScreen(
    items: List<ChatDataModel>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var nameChatDataModel by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = listState,
            reverseLayout = true,
            verticalArrangement = Arrangement.Bottom,
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            items(items.reversed()) {
                MessageItem(it)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = nameChatDataModel,
                onValueChange = { nameChatDataModel = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter your message") }
            )

            Button(
                modifier = Modifier
                    .requiredWidth(96.dp)
                    .padding(start = 4.dp, end = 4.dp),
                enabled = nameChatDataModel.isNotEmpty(),
                onClick = {
                    if (nameChatDataModel.isNotEmpty()) {
                        onSave(nameChatDataModel)
                        nameChatDataModel = ""
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
    LaunchedEffect(items) {
        if (items.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }
}

//@Composable
//internal fun ChatDataModelScreen(
//    items: List<ChatDataModel>,
//    onSave: (name: String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(modifier) {
//        var nameChatDataModel by remember { mutableStateOf("") }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 24.dp),
//
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TextField(
//                value = nameChatDataModel,
//                onValueChange = { nameChatDataModel = it }
//            )
//
//            Button(modifier = Modifier.requiredWidth(96.dp)
//                .padding(start = 4.dp, end = 4.dp),
//                enabled = nameChatDataModel.isNotEmpty(),
//                onClick = { if (nameChatDataModel.isNotEmpty()) {
//                    onSave(nameChatDataModel); nameChatDataModel = "" }
//                }
//            )
//            {
//                Text("Send")
//            }
//        }
//        LazyColumn(modifier = Modifier.weight(1f)) {
//            items(items) {
//                MessageItem(it)
//            }
//        }
//    }
//}

@Composable
fun MessageItem(chatDataModel: ChatDataModel) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = "Username: ${chatDataModel.ownerId}")
        Text(text = "Message: ${chatDataModel.message}")
    }
}

// Previews


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        ChatDataModelScreen(listOf(ChatDataModel()), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        ChatDataModelScreen(listOf(ChatDataModel()), onSave = {})
    }
}
