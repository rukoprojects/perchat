package di

import controller.WebSocketControllerImpl
import controller.WebsocketController

class GlobalInstance {
    companion object {
        lateinit var websocketController: WebsocketController
    }

    init {
        websocketController = WebSocketControllerImpl()
    }
}