package me.nikaron4ik.testPlugin.Events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceListener implements Listener {
    private static final String PLAYER_PLACED_KEY = "player_placed";

    public BlockPlaceListener() {
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        block.setMetadata("player_placed", new FixedMetadataValue(JavaPlugin.getProvidingPlugin(this.getClass()), true));
    }

    public boolean isPlayerPlaced(Block block) {
        return block.hasMetadata("player_placed");
    }
}