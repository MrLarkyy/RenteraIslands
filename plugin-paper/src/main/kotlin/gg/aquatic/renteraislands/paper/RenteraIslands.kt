package gg.aquatic.renteraislands.paper

import com.infernalsuite.aswm.api.SlimePlugin
import com.infernalsuite.aswm.api.world.properties.SlimePropertyMap
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import gg.aquatic.renteraislands.paper.network.NetworkingHandler
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.io.FileInputStream
import java.util.ArrayList
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

        NetworkingHandler.start()
    }

    override fun onDisable() {

    }

    private inner class Listeners: Listener {

        @EventHandler
        fun PlayerJoinEvent.onJoin() {
            val loader = slimePlugin.getLoader("mysql")!!

            for (listWorld in loader.listWorlds()) {
                if (listWorld.startsWith("stress")) {
                    loader.deleteWorld(listWorld)
                }
            }

            val names = ArrayList<String>()

            for (i in 0..100) {
                val name = "stress_$i"
                names += name
            }
            
            val originalWorld = slimePlugin.getWorld("template1")
            for (name in names) {
                object : BukkitRunnable() {
                    override fun run() {
                        val cloned = originalWorld.clone(name, loader)

                        object:BukkitRunnable() {
                            override fun run() {
                                slimePlugin.loadWorld(cloned)
                            }

                        }.runTask(this@RenteraIslands)
                    }
                }.runTaskAsynchronously(this@RenteraIslands)

            }



            /*
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

            val bukkitWorld = Bukkit.getWorld(worldName)!!

            if (existed) return
            val file = File(WorldEdit.getInstance().schematicsFolderPath.toFile(), "test.schem")
            val format = ClipboardFormats.findByFile(file) ?: return

            format.getReader(FileInputStream(file))?.use {
                val clipboard = it.read()
                SchematicUtil.placeSchematic(clipboard,bukkitWorld.spawnLocation)
            }
             */

        }
    }

}