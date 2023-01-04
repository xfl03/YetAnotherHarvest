package com.github.xfl03.harvest.crop;

import com.github.xfl03.harvest.api.HarvestMapping;
import com.github.xfl03.harvest.util.HarvestUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@HarvestMapping({"WHEAT", "POTATOES", "CARROTS", "BEETROOTS", "NETHER_WART", "COCOA"})
public class StandardCrop extends AgeableCrop {
    @Override
    public void harvest(@NotNull Player player, @NotNull Block block) {
        HarvestUtil.harvestCrop(player, block, getSeed(block.getType()));
    }

    private Material getSeed(Material material) {
        return switch (material) {
            case WHEAT -> Material.WHEAT_SEEDS;
            case POTATOES -> Material.POTATO;
            case CARROTS -> Material.CARROT;
            case BEETROOTS -> Material.BEETROOT_SEEDS;
            case NETHER_WART -> Material.NETHER_WART;
            case COCOA -> Material.COCOA_BEANS;
            default -> Material.AIR;
        };
    }
}
