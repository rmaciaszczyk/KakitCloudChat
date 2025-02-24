package edu.zut.kakit.cloudchat.data.model

data class SnackbarData(
    val message: String,
    val actionLabel: String? = null,
    val onAction: (() -> Unit)? = null
) {

}
