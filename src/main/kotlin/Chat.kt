import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun chatScreen() {

    var textFieldValue by remember {
        mutableStateOf("")
    }

    val messageList = remember {
        mutableListOf<String>()
    }

    chatScreenContent(
        textFieldValue = textFieldValue,
        onTextFieldValueChange = { textFieldValue = it },
        onSend = {

        }
    )
}

@Composable
fun chatScreenContent(
    textFieldValue : String,
    onTextFieldValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxHeight(0.9f)
            ) {
                drawCircle(Color.Black)
            }
            Spacer(Modifier.width(20.dp))
            Text("Ian")
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {

        }
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(10.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = textFieldValue,
                onValueChange = { onTextFieldValueChange(it) }
            )
            Spacer(Modifier.width(10.dp))
            Button(
                onClick = { onSend() }
            ) {
                Text("Enviar")
            }
            Spacer(Modifier.width(10.dp))
        }
    }
}
