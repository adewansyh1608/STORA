package com.example.stora.data

import java.util.Date
import java.util.UUID
data class InventoryItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val noinv: String,
    val quantity: Int,
    val category: String,
    val condition : String,
    val location: String,
    val description: String,
    val date: String
)