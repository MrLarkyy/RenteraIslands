package gg.aquatic.renteraislands.paper

import com.infernalsuite.aswm.api.SlimePlugin
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileInputStream
import java.util.UUID


class RenteraIslands: JavaPlugin() {

    companion object {
        lateinit var INSTANCE: RenteraIslands
    }

    val slimePlugin: SlimePlugin = Bukkit.getPluginManager().getPlugin("SlimeWorldManager") as SlimePlugin

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(Listeners(),this)
    }

    override fun onDisable() {

    }

    private inner class Listeners: Listener {

        @EventHandler
        fun PlayerJoinEvent.onJoin() {
            val loader = slimePlugin.getLoader("mysql")!!
            val worldName = "test_${player.uniqueId}"


            var existed = false

            val world = if (loader.listWorlds().contains(worldName)) {
                existed = true
                slimePlugin.loadWorld(loader, worldName, false, SlimePropertyMap())
            } else {
                val w = slimePlugin.createEmptyWorld(
                    loader,
                    worldName,
                    false,
                    SlimePropertyMap()
                )
                w
            }
            slimePlugin.loadWorld(world)

            val bukkitWorld = Bukkit.getWorld(worldName)!!

            if (existed) return
            val file = File(WorldEdit.getInstance().schematicsFolderPath.toFile(), "test.schem")
            val format = ClipboardFormats.findByFile(file) ?: return

            format.getReader(FileInputStream(file))?.use {
                val clipboard = it.read()
                SchematicUtil.placeSchematic(clipboard,bukkitWorld.spawnLocation)
            }

        }
    }

}