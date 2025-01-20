package com.core.network.authenticator

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponseDTO(
    val data: RefreshTokenDTO,
)
