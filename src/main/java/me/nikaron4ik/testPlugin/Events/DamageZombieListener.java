package me.nikaron4ik.testPlugin.Events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageZombieListener implements Listener {

    public final JavaPlugin plugin;

    public DamageZombieListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Zombie && event.getDamager() instanceof Player) {
            Zombie zombie = (Zombie) event.getEntity();
            Player player = (Player) event.getDamager();

            if (zombie.getPersistentDataContainer().has(new NamespacedKey(plugin, "special_zombie"), PersistentDataType.BYTE)) {
                double damage = Math.round(event.getFinalDamage() * 100.0) / 100.0;
                double remainingHealth = Math.round(Math.max(0, zombie.getHealth() - damage) * 100.0) / 100.0;

                player.sendMessage("Вы нанесли " + ChatColor.RED + ChatColor.BOLD + damage + ChatColor.RESET + " урона! Оставшееся здоровье зомби: " + ChatColor.DARK_GREEN + ChatColor.BOLD + remainingHealth);

                if (remainingHealth <= 0) {
                    event.getEntity().remove();
                    player.sendMessage(ChatColor.GOLD + "Зомби успешно повершен!");
                } else {
                    zombie.setHealth(remainingHealth);
                }
            }
        }
    }
}
