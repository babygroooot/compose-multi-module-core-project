package com.core.model

data class ErrorModel(
    val MessageList: List<String> = emptyList(),
    val Messages: String = "",
    val Succeeded: Boolean = false,
)
