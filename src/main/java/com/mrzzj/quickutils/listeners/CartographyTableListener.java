package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class CartographyTableListener implements Listener {

    @EventHandler
    public void onCartographyTableUse(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.CARTOGRAPHY 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 实现制图台合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗输入槽材料，0-地图输入槽，1-纸张或其他材料输入槽
                    ItemStack map = e.getInventory().getItem(0);
                    ItemStack material = e.getInventory().getItem(1);
                    
                    if (map != null) map.setAmount(map.getAmount() - 1);
                    if (material != null) material.setAmount(material.getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void onCartographyTableClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.CARTOGRAPHY 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 返还剩余材料
            for (int i = 0; i < 2; i++) { // 处理输入槽
                ItemStack item = e.getInventory().getItem(i);
                if (item != null) {
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }
    }
}