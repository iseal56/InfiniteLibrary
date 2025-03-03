package dev.iseal.infinitelibrary.worldgen.dimensions;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BiomeRegistry;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.utils.DistributedRandomNumberGenerator;
import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryGenerator extends ChunkGenerator {
    public static final MapCodec<LibraryGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(LibraryGenerator::new)));

    private static final int MAX_ATTEMPTS = 10;

    private static final ThreadLocal<Boolean> activatedChiseledQuartzPlaced = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Integer> activatedChiseledQuartzAttempts = ThreadLocal.withInitial(() -> 0);

    private final Random random = new Random();
    private DistributedRandomNumberGenerator zeroMapDRNG = new DistributedRandomNumberGenerator();
    private DistributedRandomNumberGenerator lightDRNG = new DistributedRandomNumberGenerator();
    private DistributedRandomNumberGenerator activatedChiseledQuartzDRNG = new DistributedRandomNumberGenerator();
    private DistributedRandomNumberGenerator oldBookshelfDRNG = new DistributedRandomNumberGenerator();
    private final int[] firstZeroMap = {2, 7, 12};

    private boolean isSeedSet = false;
    private long seed;

    public LibraryGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new FixedBiomeSource(biomeRegistry.getOrThrow(BiomeRegistry.BIOME_KEY)));
        initializeRandomGenerator();
    }

    private void initializeRandomGenerator() {
        if (isSeedSet) {
            zeroMapDRNG = new DistributedRandomNumberGenerator(seed);
            lightDRNG = new DistributedRandomNumberGenerator(seed);
            activatedChiseledQuartzDRNG = new DistributedRandomNumberGenerator(seed);
            oldBookshelfDRNG = new DistributedRandomNumberGenerator(seed);
        } else if (zeroMapDRNG != null) {
            return;
        }

        zeroMapDRNG.addNumber(1, 0.4d);
        zeroMapDRNG.addNumber(2, 0.5d);
        zeroMapDRNG.addNumber(3, 0.1d);

        lightDRNG.addNumber(1, 0.1d);
        lightDRNG.addNumber(2, 0.9d);

        activatedChiseledQuartzDRNG.addNumber(1, 0.1d);
        activatedChiseledQuartzDRNG.addNumber(2, 1d - 0.1d);

        oldBookshelfDRNG.addNumber(1, 5d);
        oldBookshelfDRNG.addNumber(2, 5d);
        oldBookshelfDRNG.addNumber(3, 90d);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk) {
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structureAccessor, NoiseConfig noiseConfig, Chunk chunk) {
        if (!isSeedSet) {
            seed = IL.server.getWorld(region.toServerWorld().getRegistryKey()).getSeed();
            isSeedSet = true;
            initializeRandomGenerator();
        }
        if (chunk.hasStructureReferences()) {
            return;
        }

        activatedChiseledQuartzPlaced.set(false); // Reset the flag for each chunk
        activatedChiseledQuartzAttempts.set(0); // Reset the attempts for each chunk

        int[] firstZeroAccessPoints = generateAccessPoints(zeroMapDRNG, firstZeroMap);
        int[] secondZeroAccessPoints = generateAccessPoints(zeroMapDRNG, firstZeroMap);

        generatePath(chunk, firstZeroAccessPoints, true);
        generatePath(chunk, secondZeroAccessPoints, false);

        generateLibraries(chunk, firstZeroAccessPoints, true);
        generateLibraries(chunk, secondZeroAccessPoints, false);
    }

    private void generatePath(Chunk chunk, int[] accessPoints, boolean isFirstZeroMap) {
        for (int i = 0; i < 16; i++) {
            for (int j : accessPoints) {
                BlockPos pos1 = isFirstZeroMap ? new BlockPos(i, 40, j) : new BlockPos(j, 40, i);
                BlockPos pos2 = isFirstZeroMap ? new BlockPos(i, 40, j + 1) : new BlockPos(j + 1, 40, i);
                chunk.setBlockState(pos1, BlockRegistry.IVORY_BRICKS.getDefaultState(), false);
                chunk.setBlockState(pos2, BlockRegistry.IVORY_BRICKS.getDefaultState(), false);
            }
        }
    }

    private void generateLibraries(Chunk chunk, int[] accessPoints, boolean isFirstZeroMap) {
        for (int i = 0; i < 16; i++) {
            for (int j : accessPoints) {
                BlockPos leftBookshelf = isFirstZeroMap ? new BlockPos(i, 40, j - 2) : new BlockPos(j - 2, 40, i);
                BlockPos rightBookshelf = isFirstZeroMap ? new BlockPos(i, 40, j + 3) : new BlockPos(j + 3, 40, i);

                placeBookshelfIfAir(chunk, leftBookshelf);
                placeBookshelfIfAir(chunk, rightBookshelf);
            }
        }
    }

    private void placeBookshelfIfAir(Chunk chunk, BlockPos pos) {
        if (chunk.getBlockState(pos).isAir() && chunk.getBlockState(pos.down()).isAir()) {
            boolean light = lightDRNG.getDistributedRandomNumber() == 1;
            AtomicBoolean doPillar = new AtomicBoolean(false);

            checkForPillar(chunk, pos, doPillar);

            pos = pos.down(60);
            if (doPillar.get()) {
                buildPillar(chunk, pos, 120);
            } else {
                pos = buildPillar(chunk, pos, 41);
                pos = placeChestOrBlock(chunk, pos, light);
                pos = buildBookshelfColumn(chunk, pos, 120);
            }
        }
    }

    private void checkForPillar(Chunk chunk, BlockPos pos, AtomicBoolean doPillar) {
        Utils.executeForEveryConnectedBlock(pos, (blockPos) -> {
            if (chunk.getBlockState(blockPos).getBlock().equals(BlockRegistry.IVORY_BRICKS)) {
                doPillar.set(true);
                return false;
            }
            return true;
        });
    }

    private BlockPos buildPillar(Chunk chunk, BlockPos pos, int heightLimit) {
        while (pos.getY() < heightLimit) {
            chunk.setBlockState(pos, BlockRegistry.IVORY_PILLAR.getDefaultState(), false);
            pos = pos.up();
        }
        pos.up();
        return pos;
    }

    private BlockPos placeChestOrBlock(Chunk chunk, BlockPos pos, boolean light) {
        //  && activatedChiseledQuartzDRNG.getDistributedRandomNumber() == 1
        if (!activatedChiseledQuartzPlaced.get() && activatedChiseledQuartzAttempts.get() <= MAX_ATTEMPTS && activatedChiseledQuartzDRNG.getDistributedRandomNumber() == 1) {
            chunk.setBlockState(pos, BlockRegistry.GLEAMING_CHISELED_IVORY.getDefaultState(), false);
            activatedChiseledQuartzPlaced.set(true); // Set the flag to true after placing the activated chiseled quartz
        } else {
            BlockState blockState = light && lightDRNG.getDistributedRandomNumber() == 1
                    ? Blocks.LANTERN.getDefaultState()
                    : BlockRegistry.CHISELED_IVORY.getDefaultState();
            chunk.setBlockState(pos, blockState, false);
        }
        activatedChiseledQuartzAttempts.set(activatedChiseledQuartzAttempts.get() + 1);
        pos = pos.up();
        return pos;
    }

    private BlockPos buildBookshelfColumn(Chunk chunk, BlockPos pos, int heightLimit) {
        while (pos.getY() < heightLimit) {
            switch (oldBookshelfDRNG.getDistributedRandomNumber()) {
                case 1:
                    chunk.setBlockState(pos, BlockRegistry.OLD_EMPTY_BOOKSHELF.getDefaultState(), false);
                    break;
                case 2:
                    chunk.setBlockState(pos, BlockRegistry.OLD_BOOKSHELF.getDefaultState(), false);
                    break;
                case 3:
                    chunk.setBlockState(pos, Blocks.BOOKSHELF.getDefaultState(), false);
                    break;
            }
            pos = pos.up();
        }
        return pos;
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        // No entities to populate
    }

    @Override
    public int getWorldHeight() {
        return 0;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return -64;
    }

    @Override
    public int getMinimumY() {
        return -64;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType, HeightLimitView heightLimitView, NoiseConfig noiseConfig) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView heightLimitView, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public void appendDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
        text.add("Access Points: "+Arrays.toString(generateAccessPoints(zeroMapDRNG, firstZeroMap)));
    }

    private int[] generateAccessPoints(DistributedRandomNumberGenerator randomGenerator, int[] zeroMap) {
        int lines = randomGenerator.getDistributedRandomNumber();
        ArrayList<Integer> accessPoints = new ArrayList<>(lines);
        for (int i = 0; i < lines; i++) {
            int item = zeroMap[random.nextInt(zeroMap.length)];
            if (!accessPoints.contains(item)) {
                accessPoints.add(item);
            }
        }
        return accessPoints.stream().mapToInt(Integer::intValue).toArray();
    }
}