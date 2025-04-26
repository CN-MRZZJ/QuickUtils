// 文件位置: src/main/java/com/mrzzj/quickutils/commands/GUICommand.java
package com.mrzzj.quickutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class GUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用此命令");
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        String guiType = args[0].toLowerCase();
        String permission = "quickutils.gui." + guiType;

        if (!player.hasPermission(permission)) {
            player.sendMessage("§c你没有使用 " + getGUIName(guiType) + " 的权限");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
            return true;
        }

        switch (guiType) {
            case "workbench":
                // 新版工作台打开方式
                player.openInventory(Bukkit.createInventory(player, InventoryType.WORKBENCH));
                player.sendMessage("§a已打开虚拟工作台");
                player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_TRAPDOOR_OPEN, 1.0f, 1.0f);
                break;
            case "enderchest":
                // 末影箱保持原方式
                player.openInventory(player.getEnderChest());
                player.sendMessage("§a已打开末影箱");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_STARE, 0.8f, 1.2f);
                break;
            case "enchant":
                // 新版附魔台打开方式
                player.openInventory(Bukkit.createInventory(player, InventoryType.ENCHANTING));
                player.sendMessage("§a已打开附魔台");
                player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 0.8f);
                break;
            case "anvil":
                // 新增铁砧界面
                player.openInventory(Bukkit.createInventory(player, InventoryType.ANVIL));
                player.sendMessage("§a已打开铁砧界面");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
                break;
            default:
                sendUsage(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1.0f, 1.0f);
                return true;
        }
        return true;
    }
    // sendUsage 方法：发送命令使用帮助
    private void sendUsage(Player player) {
        player.sendMessage(ChatColor.GOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        player.sendMessage(ChatColor.YELLOW + "命令帮助: /qu <类型>");
        player.sendMessage(ChatColor.GREEN + "可用类型:");
        player.sendMessage(ChatColor.AQUA + "  workbench - 虚拟工作台");
        player.sendMessage(ChatColor.AQUA + "  enderchest - 个人末影箱");
        player.sendMessage(ChatColor.AQUA + "  enchant - 附魔界面");
        player.sendMessage(ChatColor.AQUA + "  anvil - 铁砧界面");
        player.sendMessage(ChatColor.GOLD + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        player.playSound(
                player.getLocation(),
                Sound.BLOCK_NOTE_BLOCK_HARP,
                0.8f,  // 音量
                1.2f   // 音高（1.0=正常，>1.0升调）
        );
    }

    // getGUIName 方法：获取GUI类型的中文名称
    private String getGUIName(String type) {
        return switch (type.toLowerCase()) {
            case "workbench" -> ChatColor.DARK_GREEN + "虚拟工作台";
            case "enderchest" -> ChatColor.DARK_PURPLE + "末影箱";
            case "enchant" -> ChatColor.BLUE + "附魔台";
            case "anvil" -> ChatColor.DARK_GRAY + "铁砧";
            default -> ChatColor.RED + "未知界面";
        };
    }

    // 其他方法保持不变...
}
