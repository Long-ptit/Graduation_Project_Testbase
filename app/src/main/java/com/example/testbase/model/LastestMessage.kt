package com.example.testbase.model

data class LastestMessage(
    var chatMsg: ChatMessage = ChatMessage(),
    var seller: Seller = Seller()
)