package edu.zut.kakit.cloudchat.data.firebase

import com.google.firebase.firestore.DocumentId

data class ChatDataModel(

    @DocumentId val id: String = "",
    val message: String = "",
    val ownerId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)