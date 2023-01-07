package com.mclixiang.verticalfarm;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VerticalFarmCommand implements CommandExecutor {
    private static final VerticalFarm PLUGIN = VerticalFarm.thisPlugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("verticalfarm".equalsIgnoreCase(label)) {
            if (args.length == 1) {
                if ("reload".equalsIgnoreCase(args[0]) && sender.hasPermission("VerticalFarm.reload")) {
                    PLUGIN.onReload();
                    sender.sendMessage(ChatColor.GREEN + "[VerticalFarm] Reloaded!");
                    return true;
                }
            }
        }
        return false;
    }
}
