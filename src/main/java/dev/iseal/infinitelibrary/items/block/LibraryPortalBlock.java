package dev.iseal.infinitelibrary.items.block;

import com.mojang.datafixers.util.Pair;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.DimensionRegistry;
import dev.iseal.infinitelibrary.registry.StructureRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.structure.Structure;

import java.util.Collections;

public class LibraryPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = Properties.HORIZONTAL_AXIS;
    private static final RegistryKey<World> LIBRARY_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, DimensionRegistry.LIBRARY_ID);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    private Structure coreRoomStructure;

    public LibraryPortalBlock() {
        super(AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "library_portal")))
                .noCollision()
                .ticksRandomly()
                .strength(-1.0F)
                .sounds(BlockSoundGroup.GLASS)
                .luminance(state -> 11)
                .pistonBehavior(PistonBehavior.BLOCK)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(AXIS)) {
            case Z:
                return Z_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !entity.canUsePortals(false)) {
            return;
        }
        if (world instanceof ServerWorld && !entity.hasVehicle() && !entity.hasPassengers()) {
            ServerWorld library = world.getServer().getWorld(LIBRARY_WORLD_KEY);
            if (coreRoomStructure == null) {
                coreRoomStructure = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).get(StructureRegistry.CORE_ROOM_ID);
            }
            // litterally just here to not crash the game
            library.getChunk(pos);
            Pair<BlockPos, RegistryEntry<Structure>> pair = library.getChunkManager()
                    .getChunkGenerator()
                    .locateStructure(library, RegistryEntryList.of(RegistryEntry.of(coreRoomStructure)), pos, 100, false);
            BlockPos target = pair.getFirst();
            int x = target.getX();
            int z = target.getZ();
            x+=8;
            z-=8;
            if (entity instanceof PlayerEntity plr)
                plr.teleport(world.getServer().getWorld(LIBRARY_WORLD_KEY), x, 41, z, Collections.emptySet(), ((PlayerEntity) entity).headYaw, entity.getPitch(), true);
            else
                entity.teleport(world.getServer().getWorld(LIBRARY_WORLD_KEY), x, 41, z, Collections.emptySet(), entity.getYaw(), entity.getPitch(), true);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(
                    (double)pos.getX() + 0.5,
                    (double)pos.getY() + 0.5,
                    (double)pos.getZ() + 0.5,
                    SoundEvents.BLOCK_PORTAL_AMBIENT,
                    SoundCategory.BLOCKS,
                    0.5F,
                    random.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }

        for (int i = 0; i < 4; i++) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();
            double g = ((double)random.nextFloat() - 0.5) * 0.5;
            double h = ((double)random.nextFloat() - 0.5) * 0.5;
            double j = ((double)random.nextFloat() - 0.5) * 0.5;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
                d = (double)pos.getX() + 0.5 + 0.25 * (double)k;
                g = random.nextFloat() * 2.0F * (float)k;
            } else {
                f = (double)pos.getZ() + 0.5 + 0.25 * (double)k;
                j = (random.nextFloat() * 2.0F * (float)k);
            }

            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
        }
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch (state.get(AXIS)) {
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }
}
