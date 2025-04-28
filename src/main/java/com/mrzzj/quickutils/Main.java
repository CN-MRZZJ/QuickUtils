package com.mrzzj.quickutils;

import com.mrzzj.quickutils.commands.GUICommand;
import com.mrzzj.quickutils.listeners.AnvilListener;
import com.mrzzj.quickutils.listeners.WorkbenchListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 注册监听器
        getServer().getPluginManager().registerEvents(new WorkbenchListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this); // 新增这行
        
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
