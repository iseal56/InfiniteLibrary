package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.registry.DamageSourceRegistry;
import dev.iseal.infinitelibrary.registry.DimensionRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class RemoveExperienceListener {

    private final int reloadTime = 5*20;
    private int time = 0;

    public void registerListener() {
        ServerTickEvents.START_SERVER_TICK.register((server) -> {
            time++;
            if (time < reloadTime) {
                return;
            }
            time = 0;
            // remove experience from all players
            server.getPlayerManager().getPlayerList().forEach(player -> {
                if (!player.getServerWorld().getRegistryKey().getValue().equals(DimensionRegistry.LIBRARY_ID)) {
                    return;
                }
                if (player.totalExperience == 0) {
                    player.damage(DamageSourceRegistry.ABSORB_KNOWLEDGE, 4f);
                } else {
                    player.addExperience(-1);
                }
            });
        });
    }

}
