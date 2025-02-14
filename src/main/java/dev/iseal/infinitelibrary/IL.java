package dev.iseal.infinitelibrary;

import dev.iseal.infinitelibrary.mixin.ItemRendererAccessor;
import dev.iseal.infinitelibrary.worldgen.dimensions.LibraryGenerator;
import dev.iseal.infinitelibrary.listeners.RemoveExperienceListener;
import dev.iseal.infinitelibrary.items.item_groups.InfiniteLibraryGroup;
import dev.iseal.infinitelibrary.listeners.AddCodesToLootTables;
import dev.iseal.infinitelibrary.registry.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class IL implements ModInitializer {

    public static final String MOD_ID = "infinitelibrary";
    private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(MOD_ID, "library"));
    private static RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());
    public static final RegistryKey<Biome> BIOME_KEY = RegistryKey.of(RegistryKeys.BIOME, new Identifier(MOD_ID, "library"));
    public static MinecraftServer server;

    @Override
    public void onInitialize() {
        Registry.register(Registries.CHUNK_GENERATOR, new Identifier(MOD_ID, "library"), LibraryGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(MOD_ID, "library"));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            IL.server = server;
            StructureRegistry.getInstance().serverRegister();
            DamageSourceRegistry.getInstance().initializeServer();
        });
        StructureRegistry.getInstance().register();
        BlockRegistry.getInstance().initialize();
        DimensionRegistry.getInstance().initialize();
        DamageSourceRegistry.getInstance().initialize();
        LootTableRegistry.getInstance().initialize();
        ItemRegistry.getInstance().initialize();
        InfiniteLibraryGroup.initialize();
        new RemoveExperienceListener().registerListener();
        new AddCodesToLootTables().initialize();
        //new BlockBreakListener().init();
    }
}
