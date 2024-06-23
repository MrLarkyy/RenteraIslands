package gg.aquatic.renteraislands.api.model.server

import gg.aquatic.renteraislands.api.model.player.PlayerData
import java.util.UUID

class PlayerServerData(
    val type: String,
    val owner: UUID,
    val members: HashMap<UUID,PlayerServerMemberData>,
    val creationDateTime: String,
    val settings: ArrayList<String>,
    val dimensions: ArrayList<String>,
    val unlockedAreas: ArrayList<String>,
    val visitors: ArrayList<UUID>,
    val cooping: ArrayList<UUID>
) {



}