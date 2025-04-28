package com.mrzzj.quickutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import java.util.Iterator;

public class WorkbenchListener implements Listener {

    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.WORKBENCH
                && e.getView().getTitle().equals("QuickUtils Workbench")) {
            // 检测合成配方并更新结果栏
            updateCraftingResult(e);

            // 原有的合成逻辑
            if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack result = e.getCurrentItem();
                if (result != null) {
                    // 消耗原材料
                    for (int i = 1; i <= 9; i++) {
                        if (i == 5)
                            continue; // 跳过结果槽
                        ItemStack item = e.getInventory().getItem(i);
                        if (item != null) {
                            item.setAmount(item.getAmount() - 1);
                        }
                    }
                }
            }
        }
    }

    private void updateCraftingResult(InventoryClickEvent e) {
        // 获取工作台的物品
        ItemStack[] matrix = new ItemStack[9];
        for (int i = 1; i <= 9; i++) {
            if (i == 5)
                continue; // 跳过结果槽
            matrix[i > 5 ? i - 1 : i - 1] = e.getInventory().getItem(i);
        }

        // 遍历所有配方进行匹配
        Iterator<Recipe> iterator = e.getWhoClicked().getServer().recipeIterator();
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                if (matchesShapedRecipe(shapedRecipe, matrix)) {
                    e.getInventory().setItem(0, recipe.getResult());
                    return;
                }
            } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
                if (matchesShapelessRecipe(shapelessRecipe, matrix)) {
                    e.getInventory().setItem(0, recipe.getResult());
                    return;
                }
            }
        }
        // 没有匹配的配方，清空结果槽
        e.getInventory().setItem(0, null);
    }

    private boolean matchesShapedRecipe(ShapedRecipe recipe, ItemStack[] matrix) {
        String[] shape = recipe.getShape();
        int width = shape[0].length();
        int height = shape.length;

        // 检查是否匹配
        for (int y = 0; y <= 3 - height; y++) {
            for (int x = 0; x <= 3 - width; x++) {
                if (checkMatch(recipe, matrix, x, y, width, height)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(ShapedRecipe recipe, ItemStack[] matrix, int x, int y, int width, int height) {
        for (int dy = 0; dy < 3; dy++) {
            for (int dx = 0; dx < 3; dx++) {
                int matrixIndex = dy * 3 + dx;
                int subX = dx - x;
                int subY = dy - y;
                RecipeChoice target = null;

                if (subX >= 0 && subX < width && subY >= 0 && subY < height) {
                    // 由于 shape 变量在 checkMatch 方法中未定义，需要从外部传入，这里从 recipe 参数中重新获取 shape
                    String[] shape = recipe.getShape();
                    char c = shape[subY].charAt(subX);
                    target = recipe.getChoiceMap().get(c);
                }

                ItemStack item = matrix[matrixIndex];

                if (target == null && item != null) {
                    return false;
                } else if (target != null && (item == null || !target.test(item))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matchesShapelessRecipe(ShapelessRecipe recipe, ItemStack[] matrix) {
        java.util.List<RecipeChoice> ingredients = new java.util.ArrayList<>(recipe.getChoiceList());

        for (ItemStack item : matrix) {
            if (item == null)
                continue;

            boolean found = false;
            Iterator<RecipeChoice> iterator = ingredients.iterator();
            while (iterator.hasNext()) {
                RecipeChoice choice = iterator.next();
                if (choice.test(item)) {
                    iterator.remove();
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return ingredients.isEmpty();
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