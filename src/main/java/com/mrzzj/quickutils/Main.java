package com.mrzzj.quickutils;

import com.mrzzj.quickutils.commands.GUICommand;
import com.mrzzj.quickutils.listeners.AnvilListener;
import com.mrzzj.quickutils.listeners.CartographyTableListener;
import com.mrzzj.quickutils.listeners.WorkbenchListener;
import com.mrzzj.quickutils.listeners.GrindstoneListener;
import com.mrzzj.quickutils.listeners.LoomListener;
import com.mrzzj.quickutils.listeners.SmithingTableListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {
    private YamlConfiguration config;
    private VersionChecker versionChecker;

    @Override
    public void onEnable() {
        // 加载配置文件
        loadConfig();

        // 注册监听器
        registerListeners();
        
        // 注册命令
        GUICommand guiCommand = new GUICommand();
        getCommand("quickutils").setExecutor(guiCommand);
        getCommand("quickutils").setTabCompleter(guiCommand);
        // 简化启动日志
        getLogger().info("QuickUtils v" + getDescription().getVersion() + " 已激活");

        // 初始化 VersionChecker
        versionChecker = new VersionChecker(this, "CN-MRZZJ", "QuickUtils");

        // 根据配置启动定时更新检查任务
        if (config.getBoolean("enableVersionCheck")) {
            long interval = config.getLong("versionCheckInterval");
            getServer().getScheduler().runTaskTimerAsynchronously(this, versionChecker::checkGitHubReleaseVersion, 0L, 20L * interval);
        }
    }

    private void registerListeners() {
        List<Listener> listeners = new ArrayList<>();
        listeners.add(new WorkbenchListener());
        listeners.add(new AnvilListener());
        listeners.add(new GrindstoneListener());
        listeners.add(new SmithingTableListener());
        listeners.add(new LoomListener());
        listeners.add(new CartographyTableListener());

        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    @Override
    public void onDisable() {
        getLogger().info("QuickUtils 已安全关闭");
    }
}
