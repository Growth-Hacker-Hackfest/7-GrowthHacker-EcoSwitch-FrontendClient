package com.example.ecoswitch.model.response.eco_assistant

data class SingleChatbotHistoryResponse(
    val id: String,
    val cluster_id: String,
    val user_id: String,
    val role: String,
    val message: String
)
