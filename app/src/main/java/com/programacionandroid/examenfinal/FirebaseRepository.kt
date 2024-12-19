package com.programacionandroid.examenfinal

import com.google.firebase.Firebase
import com.google.firebase.database.database

class FirebaseRepository {
    private val database = Firebase.database.reference

    fun getItems(): List<Item> {
        val items = mutableListOf<Item>()

        database.child("items").get()
            .addOnSuccessListener { snapshot ->
                snapshot.children.forEach { child ->
                    val item = Item(
                        id = child.key ?: "",
                        name = child.child("name").value as? String ?: "",
                        description = child.child("description").value as? String ?: ""
                    )
                    items.add(item)
                }
                println("Items guardados: ${items.size}")
            }
            .addOnFailureListener {
                println("Error al obtener items: ${it.message}")
            }

        return items
    }

    fun addItem(item: Item) {
        val itemMap = hashMapOf(
            "name" to item.name,
            "description" to item.description
        )

        database.child("items").push().setValue(itemMap)
            .addOnSuccessListener {
                println("Item agregado")
            }
            .addOnFailureListener {
                println("Error al agregar item: ${it.message}")
            }
    }
}