package gg.aquatic.renteraislands.paper.network

import gg.aquatic.renteraislands.api.model.player.PlayerData
import gg.aquatic.renteraislands.api.network.packet.AbstractPacket
import gg.aquatic.renteraislands.api.network.packet.ClientPacket
import gg.aquatic.renteraislands.api.network.packet.ServerPacket
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.*
import io.ktor.util.reflect.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

object NetworkingHandler {

    val client = HttpClient(CIO) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    fun sendPacket(packet: ClientPacket) {
        outgoingPackets += packet
    }

    private val outgoingPackets = ArrayList<ClientPacket>()

    @OptIn(DelicateCoroutinesApi::class)
    internal fun start() {

        GlobalScope.launch {
            withContext(Dispatchers.IO) {

                client.webSocket(HttpMethod.Get, "127.0.0.1", 8080, "/server-spread") {
                    while (true) {
                        //val othersMessage = incoming.receive() as? Frame.Text

                        for (outgoingPacket in outgoingPackets) {
                            val id = outgoingPacket.id
                            val serialized = Json.encodeToString(outgoingPacket)
                            val data = "$id;;$serialized"
                            send(data)
                        }
                    }
                }
            }
        }
    }

    private suspend fun socketListener() {
        client.webSocket(HttpMethod.Get, "127.0.0.1", 8080, "/server-spread") {

            while (true) {
                val othersMessage = incoming.receive()
            }
        }
    }

}