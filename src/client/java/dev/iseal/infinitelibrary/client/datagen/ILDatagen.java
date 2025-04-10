package dev.iseal.infinitelibrary.client.datagen;

import dev.iseal.infinitelibrary.registry.worldgen.BiomeRegistry;
import dev.iseal.infinitelibrary.registry.worldgen.StructureRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ILDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnchantmentGenerator::new);
        pack.addProvider(EnglishLangProvider::new);
        pack.addProvider(BlockModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ILBlockTagProvider::new);
        pack.addProvider(ILItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.BIOME, BiomeRegistry::bootstrap);
        builder.addRegistry(RegistryKeys.TEMPLATE_POOL, StructureRegistry::bootstrapStructurePools);
        builder.addRegistry(RegistryKeys.STRUCTURE, StructureRegistry::bootstrapStructures);
    }
}
