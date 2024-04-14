import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.GlobalInstance
import screens.chatScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        chatScreen()
    }
}


fun main() = application {
    GlobalInstance()
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
