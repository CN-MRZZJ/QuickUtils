package com.mrzzj.quickutils;

import com.mrzzj.quickutils.commands.GUICommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 注册命令
        GUICommand guiCommand = new GUICommand();
        getCommand("quickutils").setExecutor(guiCommand);
        getCommand("quickutils").setTabCompleter(guiCommand);

        // 简化启动日志
        getLogger().info("QuickUtils v" + getDescription().getVersion() + " 已激活");
    }

    @Override
    public void onDisable() {
        getLogger().info("QuickUtils 已安全关闭");
    }
}
