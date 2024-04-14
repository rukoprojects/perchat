package models

import java.util.*

data class Message(
    val direction: Boolean,
    val sender: String? = null,
    val text: String,
    val timestamp: Long = Calendar.getInstance().timeInMillis
)
