package gg.aquatic.renteraislands.proxy

import gg.aquatic.renteraislands.api.network.Packets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

object NetworkingHandler {

    fun initialize() {
        embeddedServer(Netty, 8080) {
            install(WebSockets)
            install(Routing)
            configureRouting()
        }.start(true)
    }

    fun Application.configureRouting() {
        routing {
            webSocket("server-spreading") {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText: String = frame.readText()
                    if (!receivedText.contains(";;")) continue
                    val id = receivedText.split(";;")[0].toIntOrNull() ?: continue

                    Packets.existing[id]
                }
            }
        }
    }

}