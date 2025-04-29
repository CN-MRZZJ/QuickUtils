package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class LoomListener implements Listener {

    @EventHandler
    public void onLoomUse(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.LOOM 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 实现织布机合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗输入槽材料，具体槽位根据织布机界面确定
                    // 这里假设 0-旗帜输入槽，1-图案输入槽，2-染料输入槽
                    ItemStack banner = e.getInventory().getItem(0);
                    ItemStack pattern = e.getInventory().getItem(1);
                    ItemStack dye = e.getInventory().getItem(2);
                    
                    if (banner != null) banner.setAmount(banner.getAmount() - 1);
                    if (pattern != null) pattern.setAmount(pattern.getAmount() - 1);
                    if (dye != null) dye.setAmount(dye.getAmount() - 1);
                }
            }
        }
    }

    @EventHandler
    public void onLoomClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.LOOM 
            && e.getView().getTitle().contains("QuickUtils")) {
            // 返还剩余材料
            for (int i = 0; i < 3; i++) { // 处理输入槽
                ItemStack item = e.getInventory().getItem(i);
                if (item != null) {
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }
    }
}