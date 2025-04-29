package com.mrzzj.quickutils;

import org.bukkit.plugin.java.JavaPlugin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class VersionChecker {
    private final JavaPlugin plugin;
    private final String repoOwner;
    private final String repoName;

    public VersionChecker(JavaPlugin plugin, String repoOwner, String repoName) {
        this.plugin = plugin;
        this.repoOwner = repoOwner;
        this.repoName = repoName;
    }

    public void checkGitHubReleaseVersion() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/repos/" + repoOwner + "/" + repoName + "/releases/latest"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            String latestVersion = jsonResponse.getString("tag_name");
            String currentVersion = plugin.getDescription().getVersion();

            if (!currentVersion.equals(latestVersion)) {
                plugin.getLogger().info("发现新版本: " + latestVersion + "，当前版本: " + currentVersion);
            } else {
                plugin.getLogger().info("当前已是最新版本: " + currentVersion);
            }

        } catch (Exception e) {
            plugin.getLogger().warning("检查 GitHub 版本时出错: " + e.getMessage());
        }
    }
}