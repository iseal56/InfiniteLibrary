package dev.iseal.infinitelibrary.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.worldgen.structures.CoreRoomStructure;
import dev.iseal.infinitelibrary.worldgen.structures.PortalRoomStructure;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.heightprovider.ConstantHeightProvider;
import net.minecraft.world.gen.structure.*;

import java.util.ArrayList;
import java.util.List;

public class StructureRegistry {

    private static StructureRegistry INSTANCE;
    public static StructureRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new StructureRegistry();
        }
        return INSTANCE;
    }

    private StructureRegistry(){
    }

    public static final Identifier CORE_ROOM_ID = IL.identifier("core_room");
    private static final RegistryKey<Structure> CORE_ROOM_KEY = of(CORE_ROOM_ID, RegistryKeys.STRUCTURE);
    public static final StructureType<CoreRoomStructure> CORE_ROOM = register(CORE_ROOM_ID, CoreRoomStructure.CODEC);
    public static final StructureType<PortalRoomStructure> PORTAL_ROOM = register("portal_room", PortalRoomStructure.CODEC);
    public static StructurePool LIBRARY_STRUCTURES_POOL;
    public static final RegistryKey<StructurePool> LIBRARY_STRUCTURES_POOL_KEY = of("library_structures", RegistryKeys.TEMPLATE_POOL);

    public void initialize() {

    }

    public static void bootstrapStructures(Registerable<Structure> structureRegisterable) {
        RegistryEntryLookup<Biome> biomeRegistryEntryLookup = structureRegisterable.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> structurePoolRegistryEntryLookup = structureRegisterable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        structureRegisterable.register(
                CORE_ROOM_KEY,
                new JigsawStructure(
                        new Structure.Config.Builder(RegistryEntryList.of(biomeRegistryEntryLookup.getOrThrow(RegistryKey.of(RegistryKeys.BIOME, IL.identifier("library")))))
                                .terrainAdaptation(StructureTerrainAdaptation.NONE)
                                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                                .build(),
                        structurePoolRegistryEntryLookup.getOrThrow(LIBRARY_STRUCTURES_POOL_KEY),
                        1,
                        ConstantHeightProvider.create(YOffset.fixed(41)),
                        false,
                        Heightmap.Type.WORLD_SURFACE_WG
                )
        );
    }

    public static void bootstrapStructurePools(Registerable<StructurePool> structurePoolRegisterable) {
        RegistryEntryLookup<StructurePool> structurePoolRegistryEntryLookup = structurePoolRegisterable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        structurePoolRegisterable.register(
                LIBRARY_STRUCTURES_POOL_KEY,
                new StructurePool(
                        structurePoolRegistryEntryLookup.getOrThrow(RegistryKey.of(RegistryKeys.TEMPLATE_POOL, IL.identifier("library_structures"))),
                        List.of(
                                Pair.of(StructurePoolElement.ofSingle("core_room").apply(StructurePool.Projection.RIGID), 5),
                                Pair.of(StructurePoolElement.ofSingle("portal_room").apply(StructurePool.Projection.RIGID), 1))
                )
        );
    }

    private static <T extends Structure> StructureType<T> register(Identifier identifier, MapCodec<T> codec) {
        return Registry.register(Registries.STRUCTURE_TYPE, identifier, () -> codec);
    }

    private static <T extends Structure> StructureType<T> register(String identifier, MapCodec<T> codec) {
        return register(IL.identifier(identifier), codec);
    }

    private static <T> RegistryKey<T> of(String path, RegistryKey<? extends Registry<T>> key) {
        return RegistryKey.of(key, IL.identifier(path));
    }
    private static <T> RegistryKey<T> of(Identifier identifier, RegistryKey<? extends Registry<T>> key) {
        return RegistryKey.of(key, identifier);
    }


    public void serverInitialize() {
        MinecraftServer server = IL.server;
        var poolGetter = server.getRegistryManager()
                .getOrThrow(RegistryKeys.TEMPLATE_POOL)
                .getOptionalValue(Identifier.of(IL.MOD_ID, "library_structures"));
        if (poolGetter.isEmpty()) {
            System.out.println("Pool getter is empty!!");
            throw new CrashException(new CrashReport("Pool getter is empty!!", new IllegalArgumentException()));
        }
        LIBRARY_STRUCTURES_POOL = poolGetter.get();
    }

}
