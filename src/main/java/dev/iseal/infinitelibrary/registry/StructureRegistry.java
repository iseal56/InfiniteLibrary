package dev.iseal.infinitelibrary.registry;

import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.worldgen.structures.CoreRoomStructure;
import dev.iseal.infinitelibrary.worldgen.structures.PortalRoomStructure;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

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

    public final static Identifier CORE_ROOM_ID = IL.identifier("core_room");
    public final static StructureType<CoreRoomStructure> CORE_ROOM = register(CORE_ROOM_ID, CoreRoomStructure.CODEC);
    public final static StructureType<PortalRoomStructure> PORTAL_ROOM = register("portal_room", PortalRoomStructure.CODEC);
    public static StructurePool LIBRARY_STRUCTURES_POOL;
    private MinecraftServer server;

    public void initialize() {

    }

    private static <T extends Structure> StructureType<T> register(Identifier identifier, MapCodec<T> codec) {
        return Registry.register(Registries.STRUCTURE_TYPE, identifier, () -> codec);
    }

    private static <T extends Structure> StructureType<T> register(String identifier, MapCodec<T> codec) {
        return register(IL.identifier(identifier), codec);
    }


    public void serverInitialize() {
        this.server = IL.server;
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
