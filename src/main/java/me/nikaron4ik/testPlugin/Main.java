package me.nikaron4ik.testPlugin;

import me.nikaron4ik.testPlugin.Events.BreakingGrassOrDirtEvent;
import me.nikaron4ik.testPlugin.Events.DamageZombieListener;
import me.nikaron4ik.testPlugin.Events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);

        Bukkit.getServer().getPluginManager().registerEvents(new BreakingGrassOrDirtEvent(this), this);

        Bukkit.getServer().getPluginManager().registerEvents(new DamageZombieListener(this), this);
    }

    @Override
    public void onDisable() {

    }
}
