package com.github.xfl03.harvest.manager;

import com.github.xfl03.harvest.HarvestPlugin;
import com.github.xfl03.harvest.api.HarvestMapping;
import com.github.xfl03.harvest.api.HarvestableCrop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public class HarvestableCropManager {
    private final Map<Material, HarvestableCrop> harvestMapping = new HashMap<>();
    private final Logger logger = HarvestPlugin.getInstance().getLogger();

    public void loadHarvestMapping() {
        var services = ServiceLoader.load(HarvestableCrop.class, HarvestPlugin.class.getClassLoader());
        for (var service : services) {
            var mapping = service.getClass().getAnnotation(HarvestMapping.class);
            if (mapping == null) {
                logger.warning(
                        String.format("Class '%s' didn't have HarvestMapping annotation.",
                                getDisplayClassName(service.getClass())));
                continue;
            }
            for (var materialStr : mapping.value()) {
                var material = Material.getMaterial(materialStr);
                if (material == null) {
                    logger.warning(String.format("Material '%s' not found.", materialStr));
                    continue;
                }
                harvestMapping.put(material, service);
            }
        }
    }

    public void initHarvestMappingConfig(FileConfiguration config) {
        var removeList = new ArrayList<Material>(harvestMapping.size());
        for (var e : harvestMapping.entrySet()) {
            var key = toConfigString(e.getKey());
            config.addDefault(key, true);
            if (!config.getBoolean(key)) {
                removeList.add(e.getKey());
            }
        }
        for (var key : removeList) {
            harvestMapping.remove(key);
        }
    }

    private String toConfigString(Material material) {
        var key = material.getKey();
        return "crops." + key.getNamespace() + '.' + key.getKey();
    }

    public void debugHarvestMapping(boolean detail) {
        logger.info(String.format("%s crops could be harvested by right click.", harvestMapping.size()));
        if (!detail) {
            return;
        }
        for (var e : harvestMapping.entrySet()) {
            logger.info(
                    String.format("Crop '%s' has been mapped to class '%s'.",
                            e.getKey().getKey(), getDisplayClassName(e.getValue().getClass())));
        }
    }

    private String getDisplayClassName(Class<?> clazz) {
        if (clazz.getName().startsWith("com.github.xfl03.harvest")) {
            return clazz.getSimpleName();
        } else {
            return clazz.getName();
        }
    }

    public boolean tryHarvest(@NotNull Player player, @NotNull Block block) {
        var crop = harvestMapping.get(block.getType());
        if (crop == null || !crop.isHarvestable(player, block)) {
            return false;
        }
        crop.harvest(player, block);
        crop.postHarvest(player, block);
        return true;
    }

    private static HarvestableCropManager instance;

    public static HarvestableCropManager getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (HarvestableCropManager.class) {
            if (instance != null) {
                return instance;
            }
            instance = new HarvestableCropManager();
            return instance;
        }
    }
}
