package edu.zut.kakit.cloudchat.data

import edu.zut.kakit.cloudchat.data.datasource.MessageRemoteDataSource
import edu.zut.kakit.cloudchat.data.firebase.ChatDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository  @Inject constructor(
    private val messageRemoteDataSource: MessageRemoteDataSource
)

{
    suspend fun sendMessage(message: ChatDataModel): String {
        return messageRemoteDataSource.sendMessage(message)
    }

    fun getMessages(currentUserIdFlow: Flow<String?>): Flow<List<ChatDataModel>> {
        return messageRemoteDataSource.getMessages(currentUserIdFlow)
    }

}