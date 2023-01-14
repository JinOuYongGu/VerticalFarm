package com.mclixiang.verticalfarm;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 69142
 */
public class VerticalFarmListener implements Listener {
    private final Map<UUID, Long> notifyMap = new HashMap<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onCropGrowEvent(BlockGrowEvent event) {
        Block block = event.getBlock();
        if (!VerticalFarmUtils.blockInEnabledWorld(block)){
            return;
        }

        int cropNum = VerticalFarmUtils.getVerticalCropNum(block);
        if (VerticalFarm.thisPlugin.mode == 1 && VerticalFarmUtils.isCrop(block) && cropNum > 1) {
            double probability = 1.0 / (VerticalFarmUtils.getVerticalCropNum(block) + 1);
            if (Math.random() > probability) {
                event.setCancelled(true);
            }
        } else if (VerticalFarm.thisPlugin.mode == 2 && !VerticalFarmUtils.isUnderAir(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onCropPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (!VerticalFarmUtils.blockInEnabledWorld(block)){
            return;
        }

        UUID uuid = event.getPlayer().getUniqueId();
        if (System.currentTimeMillis() - notifyMap.getOrDefault(uuid, -1L) > (VerticalFarm.thisPlugin.noticeInterval * 1000L)) {
            if (VerticalFarm.thisPlugin.mode == 1 && VerticalFarmUtils.isCrop(block)) {
                int numCrops = VerticalFarmUtils.getVerticalCropNum(block);
                if (numCrops > 1) {
                    String noticeMsg = VerticalFarm.thisPlugin.noticeMsg1;
                    noticeMsg = noticeMsg.replace("{number1}", String.valueOf(numCrops));
                    noticeMsg = noticeMsg.replace("{number2}", String.valueOf(numCrops + 1));
                    event.getPlayer().sendMessage(noticeMsg);
                }
            } else if (VerticalFarm.thisPlugin.mode == 2 && !VerticalFarmUtils.isUnderAir(block)) {
                event.getPlayer().sendMessage(VerticalFarm.thisPlugin.noticeMsg2);
            }

            notifyMap.put(uuid, System.currentTimeMillis());
        }
    }
}
