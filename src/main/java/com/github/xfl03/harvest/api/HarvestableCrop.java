package com.github.xfl03.harvest.api;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface HarvestableCrop {
    default boolean isHarvestable(@NotNull Player player, @NotNull Block block) {
        return true;
    }

    void harvest(@NotNull Player player, @NotNull Block block);

    default void postHarvest(@NotNull Player player, @NotNull Block block) {
    }
}
