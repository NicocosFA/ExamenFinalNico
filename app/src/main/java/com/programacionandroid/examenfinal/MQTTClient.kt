package com.programacionandroid.examenfinal

import org.eclipse.paho.client.mqttv3.*

class MQTTClient {
    private var client: MqttClient? = null

    init {
        try {
            client = MqttClient(
                "tcp://broker.hivemq.com:1883",
                "Nicolas123",
                null
            )
            client?.connect()
        } catch (e: Exception) {
            println("Error al conectar MQTT: ${e.message}")
        }
    }

    fun publishMessage(topic: String, message: String) {
        try {
            if (validarMensaje(message)) {
                val mqttMessage = MqttMessage()
                mqttMessage.payload = message.toByteArray()
                client?.publish(topic, mqttMessage)
                println("Mensaje enviado: $message")
            }
        } catch (e: Exception) {
            println("Error al publicar: ${e.message}")
        }
    }

    fun mensajeRecibido(topic: String, message: String) {
        println("Mensaje recibido en $topic: $message")
    }

    fun validarMensaje(message: String): Boolean {
        return message.isNotEmpty()
    }
}