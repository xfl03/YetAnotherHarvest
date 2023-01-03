package com.github.xfl03.harvest.listener;

import com.github.xfl03.harvest.manager.HarvestableCropManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractiveListener implements Listener {
    @EventHandler
    public void onPlayerInteractive(PlayerInteractEvent e) {
        var block = e.getClickedBlock();
        if (block == null) {
            return;
        }
        var result = HarvestableCropManager.getInstance().tryHarvest(e.getPlayer(), block);
        if (result) {
            e.setCancelled(true);
        }
    }
}
