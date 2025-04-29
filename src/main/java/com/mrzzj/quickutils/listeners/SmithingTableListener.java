package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class SmithingTableListener implements Listener {

    @EventHandler
    public void onSmithingTableUse(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.SMITHING 
            && e.getView().getTitle().equals("QuickUtils Smithing Table")) {
            // 实现锻造台合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗输入槽材料（0-物品输入槽，1-材料输入槽）
                    ItemStack item = e.getInventory().getItem(0);
                    ItemStack material = e.getInventory().getItem(1);
                    
                    if (item != null) item.setAmount(item.getAmount() - 1);
                    if (material != null) material.setAmount(material.getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void onSmithingTableClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.SMITHING 
            && e.getView().getTitle().equals("QuickUtils Smithing Table")) {
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