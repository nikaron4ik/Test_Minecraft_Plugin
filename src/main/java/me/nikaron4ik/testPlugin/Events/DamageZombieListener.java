package me.nikaron4ik.testPlugin.Events;

import java.util.Random;
import me.nikaron4ik.testPlugin.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageZombieListener implements Listener {
    public final JavaPlugin plugin;

    public DamageZombieListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Zombie && event.getDamager() instanceof Player) {
            Zombie zombie = (Zombie)event.getEntity();
            Player player = (Player)event.getDamager();
            if (zombie.getPersistentDataContainer().has(new NamespacedKey(this.plugin, "special_zombie"), PersistentDataType.BYTE)) {
                double damage = (double)Math.round(event.getFinalDamage() * (double)100.0F) / (double)100.0F;
                double remainingHealth = (double)Math.round(Math.max((double)0.0F, zombie.getHealth() - damage) * (double)100.0F) / (double)100.0F;
                String var10001 = String.valueOf(ChatColor.RED);
                player.sendMessage("Вы нанесли " + var10001 + String.valueOf(ChatColor.BOLD) + damage + String.valueOf(ChatColor.RESET) + " урона! Оставшееся здоровье зомби: " + String.valueOf(ChatColor.DARK_GREEN) + String.valueOf(ChatColor.BOLD) + remainingHealth);
                if (remainingHealth <= (double)0.0F) {
                    player.sendMessage(String.valueOf(ChatColor.GOLD) + "Зомби успешно повершен!");
                } else {
                    zombie.setHealth(remainingHealth);
                }
            } else {
                PotionEffectType[] effects = new PotionEffectType[]{PotionEffectType.BLINDNESS, PotionEffectType.POISON, PotionEffectType.SPEED};
                PotionEffectType randomEffect = effects[(new Random()).nextInt(effects.length)];
                player.addPotionEffect(new PotionEffect(randomEffect, 400, 1));
                player.sendMessage(Utils.color("&3Вы ударили не того зомби, из-за чего на Вас был наложен эффект &4&l" + randomEffect.getName()));
            }
        }

    }
}
