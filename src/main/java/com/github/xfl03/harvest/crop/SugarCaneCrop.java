package com.github.xfl03.harvest.crop;

import com.github.xfl03.harvest.api.HarvestMapping;
import com.github.xfl03.harvest.api.HarvestableCrop;
import com.github.xfl03.harvest.util.HarvestUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@HarvestMapping("SUGAR_CANE")
public class SugarCaneCrop implements HarvestableCrop {
    @Override
    public boolean isHarvestable(@NotNull Player player, @NotNull Block block) {
        return getSugarCanes(block).size() > 1;
    }

    @Override
    public void harvest(@NotNull Player player, @NotNull Block block) {
        var sugarCanes = getSugarCanes(block);
        var b = sugarCanes.get(1);
        b.breakNaturally();
    }

    private List<Block> getSugarCanes(@NotNull Block block) {
        //The length of sugar canes is no more than 4
        var sugarCanes = new ArrayList<Block>(4);
        //Search sugar cane from down to up
        for (var i = -3; i <= 3; ++i) {
            var block1 = HarvestUtil.getBlock(block, 0, i, 0);
            if (block1.getType() == block.getType()) {
                sugarCanes.add(block1);
                continue;
            }
            //When reach here, means sugar canes is unlinked
            //Output is the linked sugar canes with given block
            if (i < 0) {
                //When unlink part is under block, clear the list
                sugarCanes.clear();
            } else {
                //When unlink part is above block, search end
                break;
            }
        }
        return sugarCanes;
    }
}
