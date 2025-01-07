package me.nikaron4ik.testPlugin.Events;

import me.nikaron4ik.testPlugin.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    private final BlockPlaceListener blockPlaceListener;

    public BlockBreakListener(BlockPlaceListener blockPlaceListener) {
        this.blockPlaceListener = blockPlaceListener;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!this.blockPlaceListener.isPlayerPlaced(event.getBlock())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.color("&2&lВы можете ломать только блоки, установленные игроками!"));
        }

    }
}
