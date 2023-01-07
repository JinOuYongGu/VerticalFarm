package com.mclixiang.verticalfarm;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class VerticalFarmUtils {
    static public boolean isCrop(Block block) {
        Material material = block.getType();
        return material.equals(Material.SUGAR_CANE)
                || material.equals(Material.WHEAT)
                || material.equals(Material.MELON_STEM)
                || material.equals(Material.PUMPKIN_STEM)
                || material.equals(Material.POTATOES)
                || material.equals(Material.CARROTS)
                || material.equals(Material.NETHER_WART);
    }

    static public int getVerticalCropNum(Block block) {
        int xLocation = block.getX();
        int zLocation = block.getZ();
        World world = block.getWorld();
        int numCrop = 0;

        List<Block> blockList = new ArrayList<>();
        for (int yLocation = world.getMinHeight(); yLocation < world.getMaxHeight(); yLocation++) {
            blockList.add(world.getBlockAt(new Location(world, xLocation, yLocation, zLocation)));
        }

        Material prevMaterial = blockList.get(0).getType();
        for (Block verticalBlock : blockList) {
            if (isCrop(verticalBlock)) {
                if (verticalBlock.getType().equals(Material.SUGAR_CANE)) {
                    if (!prevMaterial.equals(Material.SUGAR_CANE)) {
                        numCrop++;
                    }
                } else {
                    numCrop++;
                }
            }
            prevMaterial = verticalBlock.getType();
        }
        return numCrop;
    }

    static public boolean isUnderAir(Block block) {
        int xLocation = block.getX();
        int zLocation = block.getZ();
        World world = block.getWorld();
        boolean isUnderAir = false;

        for (int yLocation = world.getMaxHeight(); yLocation >= block.getY(); yLocation--) {
            Block aboveBlock = world.getBlockAt(new Location(world, xLocation, yLocation, zLocation));
            if (!aboveBlock.getType().equals(Material.AIR)) {
                if (aboveBlock.getY() == block.getY()) {
                    isUnderAir = true;
                }
                break;
            }
        }
        return isUnderAir;
    }

    static public boolean blockInEnabledWorld(Block block) {
        String worldName = block.getWorld().getName();
        return (VerticalFarm.thisPlugin.enabledWorld.contains(worldName));
    }
}
