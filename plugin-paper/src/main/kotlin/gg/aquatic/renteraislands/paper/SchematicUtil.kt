package gg.aquatic.renteraislands.paper

import com.fastasyncworldedit.core.FaweAPI
import com.fastasyncworldedit.core.limit.FaweLimit
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.CompletableFuture

object SchematicUtil {

    fun placeSchematic(clipboard: Clipboard, location: Location): CompletableFuture<Nothing> {
        val future = CompletableFuture<Nothing>()

        val world = FaweAPI.getWorld(location.world.name)
        Bukkit.broadcast(Component.text("Placing schematic # SchematicUtil!"))

        object : BukkitRunnable() {
            override fun run() {
                val time = System.currentTimeMillis()
                WorldEdit.getInstance().newEditSession(world).use { editSession ->

                    editSession.limit.set(FaweLimit().apply {
                        this.SPEED_REDUCTION = 100
                    })


                    Bukkit.broadcast(Component.text("Created session # SchematicUtil!"))

                    val operation = ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(location.x,location.y,location.z))
                        .build()
                    Operations.complete(operation)
                }
                Bukkit.broadcast(Component.text("It has taken ${System.currentTimeMillis() - time} to generate!"))
                Bukkit.broadcast(Component.text("Schematic pasted on ${location.world.name}; ${location.x} ${location.y} ${location.z}"))
                future.complete(null)
            }
        }.runTaskAsynchronously(RenteraIslands.INSTANCE)

        return future
    }

}