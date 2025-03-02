package dev.iseal.infinitelibrary;

import dev.iseal.infinitelibrary.items.item_groups.InfiniteLibraryGroups;
import dev.iseal.infinitelibrary.listeners.AddCodesToLootTables;
import dev.iseal.infinitelibrary.listeners.RemoveExperienceListener;
import dev.iseal.infinitelibrary.listeners.SwordEnchantListener;
import dev.iseal.infinitelibrary.registry.*;
import dev.iseal.infinitelibrary.worldgen.dimensions.LibraryGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

public class IL implements ModInitializer {

    public static final String MOD_ID = "infinitelibrary";
    private static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION,
            Identifier.of(MOD_ID, "library")
    );
    private static RegistryKey<World> WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());
    public static MinecraftServer server;

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        Registry.register(Registries.CHUNK_GENERATOR, Identifier.of(MOD_ID, "library"), LibraryGenerator.CODEC);
        WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, Identifier.of(MOD_ID, "library"));
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            IL.server = server;
            StructureRegistry.getInstance().serverInitialize();
            DamageSourceRegistry.getInstance().initializeServer();
        });
        registerRegistries();
        registerListeners();
        InfiniteLibraryGroups.initialize();
    }

    private void registerRegistries() {
        EffectRegistry.getInstance().initialize();
        EnchantmentEffectRegistry.getInstance().initialize();
        BiomeRegistry.getInstance().initialize();
        StructureRegistry.getInstance().initialize();
        BlockRegistry.getInstance().initialize();
        DimensionRegistry.getInstance().initialize();
        DamageSourceRegistry.getInstance().initialize();
        LootTableRegistry.getInstance().initialize();
        ComponentTypeRegistry.getInstance().initialize();
        ItemRegistry.getInstance().initialize();
        ParticleRegistry.getInstance().serverInit();
    }

    private void registerListeners() {
        new RemoveExperienceListener().registerListener();
        new AddCodesToLootTables().initialize();
        UseItemCallback.EVENT.register(new SwordEnchantListener.ReleaseCharges());
        // TODO: rework this.
        //new BlockBreakListener().init();
    }
}
