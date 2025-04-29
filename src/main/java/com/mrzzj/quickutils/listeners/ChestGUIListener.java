package com.mrzzj.quickutils.listeners;

import com.mrzzj.quickutils.commands.GUICommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ChestGUIListener implements Listener {
    private final GUICommand guiCommand;
    private final Map<String, String> itemNameToType = new HashMap<>();

    public ChestGUIListener(GUICommand guiCommand) {
        this.guiCommand = guiCommand;
        // 初始化物品名称到 GUI 类型的映射
        itemNameToType.put("§2虚拟工作台", "workbench");
        itemNameToType.put("§5末影箱", "enderchest");
        itemNameToType.put("§9附魔台", "enchant");
        itemNameToType.put("§8铁砧", "anvil");
        itemNameToType.put("§7砂轮", "grindstone");
        itemNameToType.put("§d织布机", "loom");
        itemNameToType.put("§3制图台", "cartography_table");
        itemNameToType.put("§6锻造台", "smithing");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 因为 getTitle() 方法对 Inventory 类型未定义，在 Bukkit 中可以使用 getView().getTitle() 来获取标题
if (!event.getView().getTitle().equals("QuickUtils 综合界面")) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null) {
            return;
        }

        String displayName = meta.getDisplayName();
        String guiType = itemNameToType.get(displayName);
        if (guiType != null) {
            Player player = (Player) event.getWhoClicked();
            // 模拟调用原有的命令逻辑
            String[] args = {guiType};
            guiCommand.onCommand(player, null, null, args);
        }
    }
}