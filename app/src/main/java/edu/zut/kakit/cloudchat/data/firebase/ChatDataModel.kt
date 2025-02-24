package edu.zut.kakit.cloudchat.data.firebase

import com.google.firebase.firestore.DocumentId

data class ChatDataModel(

    @DocumentId val id: String = "",
    val message: String = "",
    val sender: String = "",
    val timestamp: Long = System.currentTimeMillis()
)