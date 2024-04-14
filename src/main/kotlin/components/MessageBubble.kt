package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MessageBubble(
    direction: Boolean,
    sender: String?,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        contentAlignment = if(direction) Alignment.CenterEnd
        else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.padding(
                start = if(direction) 0.dp else 20.dp,
                end = if(direction) 20.dp else 0.dp
            ),
            horizontalAlignment = if(direction) Alignment.End
                else Alignment.Start
        ) {
            if(sender!=null) {
                Text(
                    text = sender
                )
            }
            Card(
                elevation = 10.dp,
                contentColor = Color.White,
                backgroundColor = if(direction) Color.Green.copy(green = 0.5f)
                else Color.DarkGray
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = text
                )
            }
        }
    }
}

@Composable
@Preview
fun MessageBubbleSenderPreview() {
    MessageBubble(
        direction = true,
        sender = "Ian",
        text = "Hola"
    )
}

@Composable
@Preview
fun MessageBubbleReceiverPreview() {
    MessageBubble(
        direction = false,
        sender = "Ian",
        text = "Hola"
    )
}

