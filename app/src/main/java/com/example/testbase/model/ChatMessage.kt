package com.example.testbase.model

import java.io.Serializable

data class ChatMessage(
    val text: String = "",
    val fromID: String = "",
    val toID: String = "",
    val timestamp: Long = 0L
)