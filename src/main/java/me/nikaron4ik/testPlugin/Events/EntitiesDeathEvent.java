package me.nikaron4ik.testPlugin.Events;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntitiesDeathEvent implements Listener {
    public EntitiesDeathEvent() {
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            event.getDrops().add(new ItemStack(Material.DIRT, (new Random()).nextInt(5) + 1));
        }

    }
}