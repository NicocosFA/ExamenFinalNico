package com.programacionandroid.examenfinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    var message by remember { mutableStateOf("") }
    var topic by remember { mutableStateOf("test/topic") }
    var items by remember { mutableStateOf(listOf<Item>()) }

    val mqttClient = remember { MQTTClient() }
    val firebaseRepository = remember { FirebaseRepository() }

    LaunchedEffect(Unit) {
        items = firebaseRepository.getItems()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("MQTT Messages", style = MaterialTheme.typography.titleMedium)

        TextField(
            value = topic,
            onValueChange = { topic = it },
            label = { Text("Topic") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (message.isNotEmpty()) {
                    mqttClient.publishMessage(topic, message)
                    firebaseRepository.addItem(Item(
                        name = topic,
                        description = message,
                        id = ("")
                    ))
                    message = ""
                    items = firebaseRepository.getItems()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Enviar Mensaje")
        }

        Text("Firebase Items", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(items) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(item.name, style = MaterialTheme.typography.bodyMedium)
                        Text(item.description, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}