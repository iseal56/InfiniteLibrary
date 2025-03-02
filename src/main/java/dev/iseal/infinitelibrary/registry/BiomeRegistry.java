package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;

public class BiomeRegistry {

    private static BiomeRegistry instance;
    public static BiomeRegistry getInstance() {
        if (instance == null) {
            instance = new BiomeRegistry();
        }
        return instance;
    }

    public static final RegistryKey<Biome> BIOME_KEY = RegistryKey.of(
            RegistryKeys.BIOME,
            Identifier.of(IL.MOD_ID, "library")
    );

    public static void bootstrap(Registerable<Biome> biomeRegisterable) {
        biomeRegisterable.register(
                BIOME_KEY,
                new Biome.Builder()
                        .downfall(0)
                        .temperatureModifier(Biome.TemperatureModifier.NONE)
                        .effects(
                                new BiomeEffects.Builder()
                                        .fogColor(1313793)
                                        .skyColor(0)
                                        .waterColor(4159204)
                                        .waterFogColor(329011)
                                        .particleConfig(
                                                new BiomeParticleConfig(ParticleTypes.ENCHANT, 0.01f)
                                        )
                                        .build()
                        )
                        .precipitation(false)
                        .temperature(0.4f)
                        .generationSettings(new GenerationSettings.Builder().build())
                        .spawnSettings(new SpawnSettings.Builder().build())
                        .build()
        );
    }

    public void initialize() {

    }
}
