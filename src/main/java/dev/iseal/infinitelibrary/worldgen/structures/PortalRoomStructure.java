package dev.iseal.infinitelibrary.worldgen.structures;

import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.registry.worldgen.StructureRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.alias.StructurePoolAliasLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.List;
import java.util.Optional;

public class PortalRoomStructure extends Structure {
    public static final MapCodec<PortalRoomStructure> CODEC = PortalRoomStructure.createCodec(PortalRoomStructure::new);

    public PortalRoomStructure(Config config) {
        super(config);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getStartX(), 40, chunkPos.getStartZ()-1);
        return StructurePoolBasedGenerator.generate(
                context, RegistryEntry.of(StructureRegistry.LIBRARY_STRUCTURES_POOL),
                Optional.of(StructureRegistry.PORTAL_ROOM_ID), 1,
                blockPos, false,
                Optional.empty(), 16,
                StructurePoolAliasLookup.create(List.of(), blockPos, context.seed()),
                DimensionPadding.NONE,
                StructureLiquidSettings.IGNORE_WATERLOGGING
        );
    }

    @Override
    public StructureType<?> getType() {
        return StructureRegistry.PORTAL_ROOM;
        //return null;
    }
}
