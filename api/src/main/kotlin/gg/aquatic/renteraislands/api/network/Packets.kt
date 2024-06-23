package gg.aquatic.renteraislands.api.network

import gg.aquatic.renteraislands.api.network.packet.ClientPacket
import gg.aquatic.renteraislands.api.network.packet.ServerPacket
import gg.aquatic.renteraislands.api.network.packet.impl.client.ClientboundIslandLoadRequestPacket

object Packets {

    val CLIENT_BOUND: Map<Int,Class<out ClientPacket>> = mapOf(
        0 to ClientboundIslandLoadRequestPacket::class.java,
    )
    val SERVER_BOUND: Map<Int,Class<out ServerPacket>> = mapOf(
    )

}