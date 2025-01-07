package me.nikaron4ik.testPlugin.Events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class BreakingGrassOrDirtEvent implements Listener {
    private final JavaPlugin plugin;

    public BreakingGrassOrDirtEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreackingBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getBlock();
        Material BlockType = block.getType();
        Location playerLocation = player.getLocation();
        Vector direction = player.getEyeLocation().getDirection();
        if (BlockType == Material.GRASS_BLOCK || BlockType == Material.DIRT) {
            Sheep sheep = (Sheep)world.spawnEntity(block.getLocation().add((double)0.0F, (double)0.0F, (double)1.0F), EntityType.SHEEP);
            sheep.setColor(DyeColor.PURPLE);
            player.sendMessage(String.valueOf(ChatColor.WHITE) + "Сломал траву или землю? - ДЕРЖИ ОВЕЧКУ!");
        }

        if (BlockType == Material.STONE | BlockType == Material.COBBLESTONE) {
            for(int i = 5; i > 0; --i) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                    Player var10000 = event.getPlayer();
                    String var10001 = String.valueOf(ChatColor.GREEN);
                    var10000.sendMessage(var10001 + "До спавна зомби осталось: " + finalI + " секунд");
                }, (long)(20 * (5 - i)));
            }

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                Location newPlayerLocation = event.getPlayer().getLocation();
                Vector newDirection = newPlayerLocation.getDirection();
                Location spawnLocation = newPlayerLocation.add(newDirection.multiply(3)).clone();
                Zombie zombie = (Zombie) world.spawnEntity(spawnLocation, EntityType.ZOMBIE);
                zombie.setCustomName(String.valueOf(ChatColor.YELLOW) + "Убейте меня пожалуйста");
                zombie.setCustomNameVisible(true);
                zombie.getPersistentDataContainer().set(new NamespacedKey(this.plugin, "special_zombie"), PersistentDataType.BYTE, (byte)1);
                ItemStack[] ZombieArmor = new ItemStack[]{new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)};

                for(ItemStack armoritem : ZombieArmor) {
                    LeatherArmorMeta armmeta = (LeatherArmorMeta)armoritem.getItemMeta();
                    armmeta.setColor(Color.YELLOW);
                    armoritem.setItemMeta(armmeta);
                }

                zombie.getEquipment().setHelmet(ZombieArmor[0]);
                zombie.getEquipment().setChestplate(ZombieArmor[1]);
                zombie.getEquipment().setLeggings(ZombieArmor[2]);
                zombie.getEquipment().setBoots(ZombieArmor[3]);
            }, 100L);
        }

    }
}
