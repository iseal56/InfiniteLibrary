package dev.iseal.infinitelibrary.client.clientRegistries;

import dev.iseal.infinitelibrary.client.particles.DischargeParticle;
import dev.iseal.infinitelibrary.registry.ParticleRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.SoulParticle;

public class ClientParticleRegistry {
    private static ClientParticleRegistry INSTANCE;
    public static ClientParticleRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientParticleRegistry();
        }
        return INSTANCE;
    }

    public void initialize() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SWORD_ENCHANT_PULSE, DischargeParticle.Factory::new);
    }
}
