package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ParticleRegistry {
    private static ParticleRegistry INSTANCE;
    public static ParticleRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ParticleRegistry();
        }
        return INSTANCE;
    }

    public static final SimpleParticleType SWORD_ENCHANT_PULSE = FabricParticleTypes.simple();

    public void serverInit() {
        Registry.register(Registries.PARTICLE_TYPE, IL.identifier("sword_enchant_pulse"), SWORD_ENCHANT_PULSE);
    }
}
