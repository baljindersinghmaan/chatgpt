package com.baljinder.chatgpt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(mutableListOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display messages
        MessageList(messages)

        // Input field and send button
        ChatInputField(
            messageText = messageText,
            onMessageChanged = { newMessage -> messageText = newMessage },
            onSendClicked = {
                if (messageText.isNotBlank()) {
                    messages.add(messageText)
                    messageText = ""
                }
            }
        )
    }
}

@Composable
fun MessageList(messages: List<String>) {
    LazyColumn {
        items(messages) { message ->
            MessageBubble(text = message)
        }
    }
}

@Composable
fun MessageBubble(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.copy(color = White),
                )
            }
        )
    }
}

@Composable
fun ChatInputField(
    messageText: String,
    onMessageChanged: (String) -> Unit,
    onSendClicked: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = messageText,
                onValueChange = { newMessage -> onMessageChanged(newMessage) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = { onSendClicked() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )

            IconButton(
                onClick = { onSendClicked() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}
