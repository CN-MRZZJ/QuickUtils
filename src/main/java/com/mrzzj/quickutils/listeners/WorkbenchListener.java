package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class WorkbenchListener implements Listener {

    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.WORKBENCH 
            && e.getView().getTitle().equals("QuickUtils Workbench")) {
            // 实现合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗原材料
                    for (int i = 1; i <= 9; i++) {
                        if (i == 5) continue; // 跳过结果槽
                        ItemStack item = e.getInventory().getItem(i);
                        if (item != null) {
                            item.setAmount(item.getAmount() - 1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.WORKBENCH 
            && e.getView().getTitle().equals("QuickUtils Workbench")) {
            // 返还剩余材料
            for (int i = 0; i < e.getInventory().getSize(); i++) {
                ItemStack item = e.getInventory().getItem(i);
                if (item != null && i != 0) { // 跳过结果槽
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }
    }
}