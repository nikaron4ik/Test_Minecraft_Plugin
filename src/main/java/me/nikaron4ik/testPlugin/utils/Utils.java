package me.nikaron4ik.testPlugin.utils;

import org.bukkit.ChatColor;

public class Utils {
    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
