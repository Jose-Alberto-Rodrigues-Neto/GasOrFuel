package app.gasorfuel.model

import kotlinx.serialization.Serializable

@Serializable
data class GasStation(
    val id: Int,
    val name: String,
    val gasPrice: Float,
    val alcoholPrice: Float,
    val registrationDate: String,
)
