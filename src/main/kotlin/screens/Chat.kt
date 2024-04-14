package screens

import models.Message
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import components.MessageBubble
import di.GlobalInstance

@Composable
fun chatScreen() {

    val websocketController by lazy {
        GlobalInstance.websocketController
    }
    val websocketMessage by websocketController.message.collectAsState(null)

    var textFieldValue by remember {
        mutableStateOf("")
    }

    val messageList = remember {
        mutableStateListOf<Message>()
    }

    LaunchedEffect(websocketMessage) {
        websocketMessage?.also {
            messageList.add(it)
        }
    }

    chatScreenContent(
        textFieldValue = textFieldValue,
        onTextFieldValueChange = { textFieldValue = it },
        onSend = {
            messageList.add(
                Message(
                    direction = true,
                    text = textFieldValue,
                )
            )
            websocketController.send(textFieldValue)
            textFieldValue = ""
        },
        messages = messageList
    )
}

@Composable
fun chatScreenContent(
    textFieldValue : String,
    onTextFieldValueChange: (String) -> Unit,
    messages: List<Message>,
    onSend: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            backgroundColor = Color.LightGray
        ) {
            Row(
                Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource("ian.png"),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clip(CircleShape)
                    )
                }
                Spacer(Modifier.width(20.dp))
                Text("Ian")
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true
            ) {
                items(messages.sortedByDescending {it.timestamp}) {tMessage->
                    MessageBubble(
                        direction = tMessage.direction,
                        text = tMessage.text,
                        sender = tMessage.sender
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.width(10.dp))
                TextField(
                    modifier = Modifier.weight(1f),
                    value = textFieldValue,
                    onValueChange = { onTextFieldValueChange(it) },
                    singleLine = true
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
}

@Composable
@Preview
fun chatScreenContent() {
    Box(
        modifier = Modifier
            .aspectRatio(1.4f)
            .fillMaxSize()
    ) {
        chatScreenContent(
            textFieldValue = "",
            onTextFieldValueChange = {},
            onSend = {},
            messages = listOf(
                Message(
                    direction = true,
                    text = "Hola",
                    timestamp = 0
                ),
                Message(
                    direction = false,
                    text = "Como estas",
                    timestamp = 1
                ),
                Message(
                    direction = true,
                    text = "Bien",
                    timestamp = 2
                ),
            )
        )
    }
}