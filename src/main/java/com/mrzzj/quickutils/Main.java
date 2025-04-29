package com.mrzzj.quickutils;

import com.mrzzj.quickutils.commands.GUICommand;
import com.mrzzj.quickutils.listeners.AnvilListener;
import com.mrzzj.quickutils.listeners.WorkbenchListener;
import com.mrzzj.quickutils.listeners.GrindstoneListener;
import com.mrzzj.quickutils.listeners.SmithingTableListener;

import org.bukkit.plugin.java.JavaPlugin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // 注册监听器
        getServer().getPluginManager().registerEvents(new WorkbenchListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new GrindstoneListener(), this);
        getServer().getPluginManager().registerEvents(new SmithingTableListener(), this);
        
        // 注册命令
        GUICommand guiCommand = new GUICommand();
        getCommand("quickutils").setExecutor(guiCommand);
        getCommand("quickutils").setTabCompleter(guiCommand);
        // 简化启动日志
        getLogger().info("QuickUtils v" + getDescription().getVersion() + " 已激活");

        // 启动定时更新检查任务，每 2 小时执行一次（20 ticks = 1 秒，7200 秒 = 2 小时）
        getServer().getScheduler().runTaskTimerAsynchronously(this, this::checkGitHubReleaseVersion, 0L, 20L * 7200);
    }

    private void checkGitHubReleaseVersion() {
        try {
            // 替换为你的 GitHub 仓库信息
            String repoOwner = "CN-MRZZJ";
            String repoName = "QuickUtils";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/repos/" + repoOwner + "/" + repoName + "/releases/latest"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            String latestVersion = jsonResponse.getString("tag_name");
            String currentVersion = getDescription().getVersion();

            if (!currentVersion.equals(latestVersion)) {
                getLogger().info("发现新版本: " + latestVersion + "，当前版本: " + currentVersion);
            } else {
                getLogger().info("当前已是最新版本: " + currentVersion);
            }

        } catch (Exception e) {
            getLogger().warning("检查 GitHub 版本时出错: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("QuickUtils 已安全关闭");
    }
}
