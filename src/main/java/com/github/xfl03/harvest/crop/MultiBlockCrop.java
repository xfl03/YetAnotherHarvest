package com.github.xfl03.harvest.crop;

import com.github.xfl03.harvest.api.HarvestMapping;
import com.github.xfl03.harvest.api.HarvestableCrop;
import com.github.xfl03.harvest.util.HarvestUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@HarvestMapping({"SUGAR_CANE", "BAMBOO"})
public class MultiBlockCrop implements HarvestableCrop {
    @Override
    public boolean isHarvestable(@NotNull Player player, @NotNull Block block) {
        return getCropBlocks(block).size() > 1;
    }

    @Override
    public void harvest(@NotNull Player player, @NotNull Block block) {
        var blocks = getCropBlocks(block);
        var b = blocks.get(1);
        b.breakNaturally();
    }

    private List<Block> getCropBlocks(@NotNull Block block) {
        //The length of crop block is no more than 16
        var blocks = new ArrayList<Block>(16);
        //Search crop block from down to up
        for (var i = -15; i <= 15; ++i) {
            var block1 = HarvestUtil.getBlock(block, 0, i, 0);
            if (block1.getType() == block.getType()) {
                blocks.add(block1);
                continue;
            }
            //When reach here, means sugar canes is unlinked
            //Output is the linked sugar canes with given block
            if (i < 0) {
                //When unlink part is under block, clear the list
                blocks.clear();
            } else {
                //When unlink part is above block, search end
                break;
            }
        }
        return blocks;
    }
}
