package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class GrindstoneListener implements Listener {

    @EventHandler
    public void onGrindstoneUse(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.GRINDSTONE 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 实现砂轮合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗输入槽材料（0-左输入槽，1-右输入槽）
                    ItemStack left = e.getInventory().getItem(0);
                    ItemStack right = e.getInventory().getItem(1);
                    
                    if (left != null) left.setAmount(left.getAmount() - 1);
                    if (right != null) right.setAmount(right.getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void onGrindstoneClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.GRINDSTONE 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 返还剩余材料
            for (int i = 0; i < 2; i++) { // 只处理输入槽
                ItemStack item = e.getInventory().getItem(i);
                if (item != null) {
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }
    }
}