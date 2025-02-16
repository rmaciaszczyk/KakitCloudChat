package edu.zut.kakit.cloudchat.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import edu.zut.kakit.cloudchat.data.firebase.ChatDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRemoteDataSource@Inject constructor(
    private val firestore: FirebaseFirestore
)
{
    fun getMessages(currentUserIdFlow: Flow<String?>): Flow<List<ChatDataModel>> {
        return currentUserIdFlow.flatMapLatest { ownerId ->
            firestore
                .collection(CHAT_COLLECTION).orderBy("timestamp")
                //.whereEqualTo(OWNER_ID_FIELD, ownerId)
                .dataObjects()



        }
    }
    suspend fun sendMessage(message: ChatDataModel): String {
        return firestore.collection(CHAT_COLLECTION).add(message).await().id
    }


    companion object {
        private const val OWNER_ID_FIELD = "ownerId"
        private const val CHAT_COLLECTION = "kakitchat"
    }
}