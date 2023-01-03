package com.github.xfl03.harvest.crop;

import com.github.xfl03.harvest.api.HarvestMapping;
import com.github.xfl03.harvest.api.HarvestableCrop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@HarvestMapping({Material.PUMPKIN, Material.MELON})
public class FullBlockCrop implements HarvestableCrop {
    @Override
    public void harvest(@NotNull Player player, @NotNull Block block) {
        block.breakNaturally();
    }
}
