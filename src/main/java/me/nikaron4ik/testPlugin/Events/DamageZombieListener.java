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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static me.nikaron4ik.testPlugin.utils.Utils.color;

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
            } else {
                // Создаём массив со списком эффектов, которые могут выпасть игроку и рандомно выбираем один из этих эффектов
                PotionEffectType[] effects = {PotionEffectType.BLINDNESS, PotionEffectType.POISON, PotionEffectType.SPEED};
                PotionEffectType randomEffect = effects[new Random().nextInt(effects.length)];

                // Накладываем выбранный эффект игроку
                player.addPotionEffect(new PotionEffect(randomEffect, 400, 1));;
                // (теперь цвета добавляются при помощи цветовых кодов, механика их работы создана в Utils)
                player.sendMessage(color("&3Вы ударили не того зомби, из-за чего на Вас был наложен эффект &4&l" + randomEffect.getName()));
                }
            }
        }
    }
