package dev.iseal.infinitelibrary.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.registry.StructureRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.alias.StructurePoolAliasLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class CoreRoomStructure extends Structure {
    public static final MapCodec<CoreRoomStructure> CODEC = CoreRoomStructure.createCodec(CoreRoomStructure::new);

    protected CoreRoomStructure(Config config) {
        super(config);
    }

    @Override
    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getStartX(), 41, chunkPos.getStartZ()-1);
        return StructurePoolBasedGenerator.generate(
                context, RegistryEntry.of(StructureRegistry.LIBRARY_STRUCTURES_POOL),
                Optional.empty(), 1,
                blockPos, false,
                Optional.empty(), 16,
                StructurePoolAliasLookup.EMPTY, DimensionPadding.NONE,
                StructureLiquidSettings.IGNORE_WATERLOGGING

        );

        // StructurePoolAliasLookup aliasLookup,
        //    DimensionPadding dimensionPadding,
        //    StructureLiquidSettings liquidSettings
    }

    @Override
    public StructureType<?> getType() {
        return StructureRegistry.CORE_ROOM;
    }
}
