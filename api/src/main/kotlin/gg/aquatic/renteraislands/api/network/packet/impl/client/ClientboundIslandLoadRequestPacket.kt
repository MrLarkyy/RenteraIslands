package gg.aquatic.renteraislands.api.network.packet.impl.client

import gg.aquatic.renteraislands.api.network.packet.ClientPacket
import java.util.UUID

class ClientboundIslandLoadRequestPacket(
    val playerUUID: UUID
): ClientPacket() {

    companion object {
        val ID: Int = 0
    }

    override val id: Int
        get() = ID

}