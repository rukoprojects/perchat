package controller

import models.Message
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlin.io.readText

class WebSocketControllerImpl: WebsocketController {
    private val _message = MutableSharedFlow<Message>()

    override val message: SharedFlow<Message?>
        get() = _message.asSharedFlow()

    private val sendChannel = Channel<String>()

    override fun send(message: String) {
        CoroutineScope(Dispatchers.Default).launch {
            sendChannel.send(message)
        }
    }

    suspend fun DefaultClientWebSocketSession.inputMessages() {
        try {
            sendChannel.consumeEach {
                send(it)
                delay(1000)
            }
        } catch (e: Exception) {
            println("Error while sending: " + e.localizedMessage)
            return
        }
    }

    suspend fun DefaultClientWebSocketSession.outputMessages() {
        try {
            for (message in incoming) {
                message as? Frame.Text ?: continue
                deserialize(message.readText() as String)?.let {
                    if(it.first != "niko") {
                        _message.emit(
                            Message(
                                direction = false,
                                sender = it.first,
                                text = it.second
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            println("Error while receiving: " + e.localizedMessage)
        }
    }

    suspend fun DefaultClientWebSocketSession.laconchadetumadre() {
        delay(5000)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val client = HttpClient {
                install(WebSockets)
            }
            runBlocking {
                client.webSocket(method = HttpMethod.Get, host = "192.168.0.143", port = 8080, path = "/events") {
                    send("{\"id_pub\": \"niko\", \"room\": \"a\"}")
                    val messageOutputRoutine = launch { outputMessages() }
                    val userInputRoutine = launch { inputMessages() }

                    userInputRoutine.join()
                    messageOutputRoutine.cancelAndJoin()
                }
            }
            client.close()
            println("Connection closed. Goodbye!")
        }
    }

    private fun deserialize(input: String): Pair<String, String>? {
        val regex = Regex("""([^:]+): (.+)""")
        val matchResult = regex.find(input)
        if (matchResult != null) {
            val (user, message) = matchResult.destructured
            return Pair(user, message)
        } else {
            return null
        }
    }
}