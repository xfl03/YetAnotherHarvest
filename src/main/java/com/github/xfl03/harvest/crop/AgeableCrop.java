package com.github.xfl03.harvest.crop;

import com.github.xfl03.harvest.api.HarvestableCrop;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AgeableCrop implements HarvestableCrop {
    @Override
    public boolean isHarvestable(@NotNull Player player, @NotNull Block block) {
        if (!(block.getBlockData() instanceof Ageable ageBlock)) {
            throw new IllegalArgumentException(String.format("Block %s don't have age.", block));
        }
        return ageBlock.getAge() == ageBlock.getMaximumAge();
    }

    @Override
    public void postHarvest(@NotNull Player player, @NotNull Block block) {
        if (!(block.getBlockData() instanceof Ageable ageBlock)) {
            throw new IllegalArgumentException(String.format("Block %s don't have age.", block));
        }
        //Replant
        ageBlock.setAge(0);
        block.setBlockData(ageBlock);
    }
}
