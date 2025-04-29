// 文件位置: src/main/java/com/mrzzj/quickutils/commands/GUICommand.java
package com.mrzzj.quickutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GUICommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用此命令");
            return true;
        }

        if (args.length == 0) {
            // 添加权限检查
            if (!player.hasPermission("quickutils.gui.menu")) {
                player.sendMessage("§c你没有使用 /qu 命令打开综合界面的权限");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                return true;
            }
            // 新增判断：如果输入 /qu 命令，打开新的箱子 GUI
            openNewChestGUI(player);
            return true;
        }

        // 处理 qu help 命令
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
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
                // 创建带有特殊标识的工作台
                Inventory workbench = Bukkit.createInventory(player, InventoryType.WORKBENCH, "QuickUtils Workbench");
                player.openInventory(workbench);
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
                // 创建带有标识的铁砧界面
                Inventory anvil = Bukkit.createInventory(player, InventoryType.ANVIL, "QuickUtils Anvil");
                player.openInventory(anvil);
                player.sendMessage("§a已打开铁砧界面");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
                break;
            case "grindstone":
                // 新增砂轮界面
                player.openInventory(Bukkit.createInventory(player, InventoryType.GRINDSTONE));
                player.sendMessage("§a已打开砂轮界面");
                player.playSound(player.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0f, 1.0f);
                break;
            case "loom":
                player.openInventory(Bukkit.createInventory(player, InventoryType.LOOM));
                player.sendMessage("§a已打开织布机");
                break;
            case "cartography_table":
                player.openInventory(Bukkit.createInventory(player, InventoryType.CARTOGRAPHY));
                player.sendMessage("§a已打开制图台");
                break;
            case "smithing":
                // 创建带有标识的锻造台界面
                Inventory smithing = Bukkit.createInventory(player, InventoryType.SMITHING, "QuickUtils Smithing Table");
                player.openInventory(smithing);
                player.sendMessage("§a已打开锻造台界面");
                player.playSound(
                    player.getLocation(), 
                    Sound.BLOCK_SMITHING_TABLE_USE, 
                    1.0f,  // 音量
                    1.0f   // 音高
                );
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
        player.sendMessage(ChatColor.AQUA + "  grindstone - 砂轮界面");
        player.sendMessage(ChatColor.AQUA + "  loom - 织布机");
        player.sendMessage(ChatColor.AQUA + "  cartography_table - 制图台");
        player.sendMessage(ChatColor.AQUA + "  smithing - 锻造台界面");
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
            case "grindstone" -> ChatColor.GRAY + "砂轮";
            case "loom" -> ChatColor.LIGHT_PURPLE + "织布机";
            case "cartography_table" -> ChatColor.DARK_AQUA + "制图台";
            case "smithing" -> ChatColor.GOLD + "锻造台";
            default -> ChatColor.RED + "未知界面";
        };
    }

    // 新增方法：创建新的箱子 GUI
    private void openNewChestGUI(Player player) {
        // 创建一个 6 行的箱子 GUI
        Inventory chestGUI = Bukkit.createInventory(player, 6 * 9, "QuickUtils 综合界面");

        // 填充周围一圈玻璃板
        ItemStack glassPane = new ItemStack(Material.GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassMeta);

        // 顶部和底部
        for (int i = 0; i < 9; i++) {
            chestGUI.setItem(i, glassPane);
            chestGUI.setItem(45 + i, glassPane);
        }

        // 左侧和右侧
        for (int i = 1; i < 5; i++) {
            chestGUI.setItem(i * 9, glassPane);
            chestGUI.setItem(i * 9 + 8, glassPane);
        }

        // 中间放置各种 GUI 对应的物品
        String[] guiTypes = {
            "workbench", "enderchest", "enchant", "anvil",
            "grindstone", "loom", "cartography_table", "smithing"
        };
        int[] slots = {11, 12, 13, 14, 15, 20, 21, 22};

        for (int i = 0; i < guiTypes.length; i++) {
            String type = guiTypes[i];
            ItemStack item = getItemForGUI(type);
            if (item != null) {
                chestGUI.setItem(slots[i], item);
            }
        }

        player.openInventory(chestGUI);
    }

    // 新增方法：根据 GUI 类型获取对应的物品
    private ItemStack getItemForGUI(String type) {
        ItemStack item = null;
        switch (type) {
            case "workbench":
                item = new ItemStack(Material.CRAFTING_TABLE);
                break;
            case "enderchest":
                item = new ItemStack(Material.ENDER_CHEST);
                break;
            case "enchant":
                item = new ItemStack(Material.ENCHANTING_TABLE);
                break;
            case "anvil":
                item = new ItemStack(Material.ANVIL);
                break;
            case "grindstone":
                item = new ItemStack(Material.GRINDSTONE);
                break;
            case "loom":
                item = new ItemStack(Material.LOOM);
                break;
            case "cartography_table":
                item = new ItemStack(Material.CARTOGRAPHY_TABLE);
                break;
            case "smithing":
                item = new ItemStack(Material.SMITHING_TABLE);
                break;
        }

        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(getGUIName(type));
            item.setItemMeta(meta);
        }

        return item;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            String[] validTypes = {
                "workbench", "enderchest", "enchant", "anvil",
                "grindstone", "loom", "cartography_table", "smithing"
            };
            for (String type : validTypes) {
                if (type.startsWith(args[0].toLowerCase())) {
                    completions.add(type);
                }
            }
        }
        return completions;
    }
}
