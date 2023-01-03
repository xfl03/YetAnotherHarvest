package com.github.xfl03.harvest.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HarvestUtil {
    public static void harvestCrop(@NotNull Player player, @NotNull Block block, @Nullable Material seed) {
        var location = block.getLocation();
        var world = block.getWorld();
        block.getDrops().stream()
                .peek(item -> item.setAmount(item.getAmount() - (item.getType() == seed ? 1 : 0)))
                .filter(item -> item.getAmount() != 0)
                .forEach(item -> world.dropItemNaturally(location, item));
    }

    public static Block getBlock(@NotNull Block block, int dx, int dy, int dz) {
        return block.getLocation().clone().add(dx, dy, dz).getBlock();
    }
}
