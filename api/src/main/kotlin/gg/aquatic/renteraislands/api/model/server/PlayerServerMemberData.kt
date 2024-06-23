package gg.aquatic.renteraislands.api.model.server

import java.util.UUID

class PlayerServerMemberData(
    val player: UUID,
    val memberSince: String,
    val role: String
) {
}