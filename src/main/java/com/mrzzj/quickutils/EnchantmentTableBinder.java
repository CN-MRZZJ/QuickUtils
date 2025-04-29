package com.mrzzj.quickutils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;

public class EnchantmentTableBinder {
    private static final String FILE_NAME = "enchantment_tables.yml";

    public static boolean bindEnchantmentTable(Player player) {
        if (!player.hasPermission("quickutils.bind")) {
            player.sendMessage("§c你没有使用 qu bind 命令的权限");
            player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
            return false;
        }

        Block targetBlock = player.getTargetBlockExact(5);
        if (targetBlock != null && targetBlock.getType() == org.bukkit.Material.ENCHANTING_TABLE) {
            Location location = targetBlock.getLocation();
            saveEnchantmentTableLocation(player.getUniqueId().toString(), location);
            player.sendMessage("§a附魔台坐标已成功记录");
            return true;
        } else {
            player.sendMessage("§c你没有指向附魔台");
            return false;
        }
    }

    private static void saveEnchantmentTableLocation(String playerId, Location location) {
        File file = new File(Bukkit.getPluginManager().getPlugin("QuickUtils").getDataFolder(), FILE_NAME);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(playerId + ".x", location.getX());
        config.set(playerId + ".y", location.getY());
        config.set(playerId + ".z", location.getZ());
        config.set(playerId + ".world", location.getWorld().getName());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getEnchantmentTableLocation(String playerId) {
        File file = new File(Bukkit.getPluginManager().getPlugin("QuickUtils").getDataFolder(), FILE_NAME);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        double x = config.getDouble(playerId + ".x");
        double y = config.getDouble(playerId + ".y");
        double z = config.getDouble(playerId + ".z");
        String worldName = config.getString(playerId + ".world");

        if (worldName != null) {
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                return new Location(world, x, y, z);
            }
        }
        return null;
    }
}