package controller

import models.Message
import kotlinx.coroutines.flow.SharedFlow

interface WebsocketController {
    val message: SharedFlow<Message?>
    fun send(message: String)
}