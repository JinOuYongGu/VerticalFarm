package com.mclixiang.verticalfarm;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class VerticalFarm extends JavaPlugin {
    public static VerticalFarm thisPlugin;

    public int mode = 1;
    public String noticeMsg1 = "";
    public String noticeMsg2 = "";
    public int noticeInterval = 5;
    public List<String> enabledWorld;

    @Override
    public void onEnable() {
        thisPlugin = this;
        saveDefaultConfig();
        onReload();

        VerticalFarmListener vfListener = new VerticalFarmListener();
        getServer().getPluginManager().registerEvents(vfListener, this);

        getCommand("VerticalFarm").setExecutor(new VerticalFarmCommand());
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " is disabled!");
    }

    public void onReload() {
        reloadConfig();
        FileConfiguration config = this.getConfig();
        if (config.contains("mode")) {
            this.mode = config.getInt("mode");
        } else {
            this.mode = 1;
        }
        if (this.mode != 1 && this.mode != 2) {
            this.mode = 1;
        }

        if (this.mode == 1) {
            String msgKey = "mode1_notice_message";
            if (config.contains(msgKey)) {
                this.noticeMsg1 = config.getString(msgKey);
            } else {
                this.noticeMsg1 = "There are another {number1} crops in vertical direction, the growth rate will be reduced to 1/{number2}";
            }
        }
        if (this.mode == 2) {
            String msgKey = "mode2_notice_message";
            if (config.contains(msgKey)) {
                this.noticeMsg2 = config.getString(msgKey);
            } else {
                this.noticeMsg2 = "The crop is not under the sky directly and won't grow. Try to remove the blocks above it.";
            }
        }

        if (config.contains("notice_interval")){
            this.noticeInterval = config.getInt("notice_interval");
        }

        if (config.contains("enabled_world")){
            this.enabledWorld = config.getStringList("enabled_world");
        }
    }
}
