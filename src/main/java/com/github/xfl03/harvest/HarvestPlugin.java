package com.github.xfl03.harvest;

import com.github.xfl03.harvest.manager.HarvestableCropManager;
import com.github.xfl03.harvest.listener.PlayerInteractiveListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "YetAnotherHarvest", version = "1.0")
@Author("xfl03")
@Description("Harvest and replant crops by right click.")
@Website("https://github.com/xfl03")
@ApiVersion(ApiVersion.Target.v1_13)
public class HarvestPlugin extends JavaPlugin {
    private static HarvestPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        var manager = HarvestableCropManager.getInstance();
        manager.loadHarvestMapping();
        initConfig(manager);
        getServer().getPluginManager().registerEvents(new PlayerInteractiveListener(), this);
        manager.debugHarvestMapping(getConfig().getBoolean("printHarvestMapping"));
    }

    private void initConfig(HarvestableCropManager manager) {
        var config = getConfig();
        config.addDefault("printHarvestMapping", false);
        config.addDefault("cancelInteractEvent", false);
        manager.initHarvestMappingConfig(config);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public static HarvestPlugin getInstance() {
        return instance;
    }
}