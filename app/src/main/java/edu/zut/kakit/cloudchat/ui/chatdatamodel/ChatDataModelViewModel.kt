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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.zut.kakit.cloudchat.data.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import edu.zut.kakit.cloudchat.data.ChatDataModelRepository
import edu.zut.kakit.cloudchat.data.MessageRepository
import edu.zut.kakit.cloudchat.data.firebase.ChatDataModel
import edu.zut.kakit.cloudchat.ui.chatdatamodel.ChatDataModelUiState.Error
import edu.zut.kakit.cloudchat.ui.chatdatamodel.ChatDataModelUiState.Loading
import edu.zut.kakit.cloudchat.ui.chatdatamodel.ChatDataModelUiState.Success
import javax.inject.Inject

@HiltViewModel
class ChatDataModelViewModel @Inject constructor(
    private val chatDataModelRepository: ChatDataModelRepository,
    private val authRepository: AuthRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    val uiState: StateFlow<ChatDataModelUiState> = chatDataModelRepository
        .chatDataModels.map<List<String>, ChatDataModelUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addChatDataModel(name: String) {
        sendMessage(name)
        viewModelScope.launch {
            chatDataModelRepository.add(name)
        }
    }

    val messages = messageRepository.getMessages(authRepository.currentUserIdFlow)

    fun sendMessage(message: String) {
        var ownerId = authRepository.currentUser?.uid
        if (ownerId.isNullOrBlank()) {
            ownerId="anonymous"

//            showErrorSnackbar(ErrorMessage.IdError(R.string.could_not_find_account))
//            return
        }

        val ChatDataModel = ChatDataModel(
            ownerId = ownerId,
            message = message
        )

        viewModelScope.launch {
            messageRepository.sendMessage(ChatDataModel)
        }
    }


}

sealed interface ChatDataModelUiState {
    object Loading : ChatDataModelUiState
    data class Error(val throwable: Throwable) : ChatDataModelUiState
    data class Success(val data: List<String>) : ChatDataModelUiState
}
