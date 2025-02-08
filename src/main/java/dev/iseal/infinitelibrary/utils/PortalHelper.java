package dev.iseal.infinitelibrary.utils;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.structure.VillageGenerator;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structures;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PortalHelper {
    private final int PORTAL_WIDTH = 4;
    private final int PORTAL_HEIGHT = 5;
    private final int MAX_PORTAL_SIZE = PORTAL_WIDTH*2 + (PORTAL_HEIGHT-2)*2;
    private final World world;
    private final long seedHash;


    private final ArrayList<BlockPos> portalBlocks = new ArrayList<>();
    private List<BlockPos> interiorBlocks;
    private Direction.Axis axis;

    public PortalHelper(World world) {
        this.world = world;
        this.seedHash = Long.hashCode(IL.server.getWorld(world.getRegistryKey()).getSeed());
    }

    public boolean checkPortalStructure(BlockPos pos, boolean makePortal) {
        HashMap<BlockPos, Direction> adjacentBlocks = getAdjacentBlocks(pos);
        if (adjacentBlocks == null) return false;
        adjacentBlocks.keySet()
                        .stream()
                        .filter(blockPos -> !portalBlocks.contains(blockPos))
                        .forEach(this::runIterativeSearch);
        printDuplicateBlockPositions();
        System.out.println("finished iterating... size: "+portalBlocks.size());
        if (portalBlocks.size() != MAX_PORTAL_SIZE) return false;
        if (!makePortal) return true;
        calculateExtraData(true);
        return true;
    }

    public void removePortal(BlockPos pos, Entity ent) {
        boolean check = true;
        if (portalBlocks.isEmpty())
            check = checkPortalStructure(pos, false);
        if (!check) return;
        calculateExtraData(false);
        if (ent != null)
            interiorBlocks.forEach(blockPos -> world.breakBlock(blockPos, false, ent));
        else
            interiorBlocks.forEach(blockPos -> world.breakBlock(blockPos, false));
    }

    private void runIterativeSearch(BlockPos pos) {
        if (portalBlocks.size() > MAX_PORTAL_SIZE) return;
        if (portalBlocks.contains(pos)) return;
        HashMap<BlockPos, Direction> adjacentBlocks = getAdjacentBlocks(pos);
        if (adjacentBlocks == null) return;
        portalBlocks.add(pos);
        adjacentBlocks.keySet()
                .stream()
                .filter(blockPos -> !portalBlocks.contains(blockPos))
                .forEach(this::runIterativeSearch);
    }

    private HashMap<BlockPos, Direction> getAdjacentBlocks(BlockPos pos) {
        HashMap<BlockPos, Direction> map = new HashMap<>();
        int found = 0;
        for (Direction direction : Direction.values()) {
            if (found > 2) return null;
            BlockPos newPos = pos.offset(direction);
            if (isValidBookshelf(newPos)) {map.put(newPos, direction); found++;}
        }
        return map;
    }

    private boolean isValidBookshelf(BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block == Blocks.CHISELED_BOOKSHELF;
    }

    private void calculateExtraData(boolean buildPortal) {
        axis = getAxis();
        int minY, maxY, minOther, maxOther;
        int[] minMaxCoords = getMinMaxCoords(axis);
        minY = minMaxCoords[0];
        maxY = minMaxCoords[1];
        minOther = minMaxCoords[2];
        maxOther = minMaxCoords[3];
        BlockPos[][] portalGrid = generatePortalGrid(portalBlocks, axis, minY, maxY, minOther, maxOther);
        interiorBlocks = getInternalBlocks(axis, minY, maxY, minOther, maxOther);
        AtomicBoolean isValid = new AtomicBoolean(true);
        portalBlocks.stream()
                .filter(blockPos -> !isValidBookshelfComplete(blockPos, portalGrid))
                .findFirst()
                .ifPresent((blockPos) -> {
                    isValid.set(false);
                });
        if (buildPortal)
            makePortal(isValid.get());
    }

    private void makePortal(boolean isValid) {
        if (isValid) {
            System.out.println("Portal is valid!");
            interiorBlocks.forEach(blockPos -> world.setBlockState(blockPos, BlockRegistry.LIBRARY_PORTAL.getDefaultState()
                    .rotate((axis == Direction.Axis.X) ? BlockRotation.CLOCKWISE_90 : BlockRotation.NONE)));
        } else {
            System.out.println("Portal is invalid!");
            interiorBlocks.forEach(blockPos -> world.setBlockState(blockPos, Blocks.AIR.getDefaultState()));
        }
    }

    private int[] getMinMaxCoords(Direction.Axis axis) {
        int minY = portalBlocks.stream().mapToInt(BlockPos::getY).min().orElseThrow();
        int maxY = portalBlocks.stream().mapToInt(BlockPos::getY).max().orElseThrow();

        int minOther, maxOther;
        if (axis == Direction.Axis.Z) {
            // Width is along X-axis
            minOther = portalBlocks.stream().mapToInt(BlockPos::getX).min().orElseThrow();
            maxOther = portalBlocks.stream().mapToInt(BlockPos::getX).max().orElseThrow();
        } else {
            // Width is along Z-axis
            minOther = portalBlocks.stream().mapToInt(BlockPos::getZ).min().orElseThrow();
            maxOther = portalBlocks.stream().mapToInt(BlockPos::getZ).max().orElseThrow();
        }
        return new int[]{minY, maxY, minOther, maxOther};
    }

    private List<BlockPos> getInternalBlocks(Direction.Axis axis, int minY, int maxY, int minOther, int maxOther) {
        List<BlockPos> interiorBlocks = new ArrayList<>();

        if (axis == Direction.Axis.Z) {
            int z = portalBlocks.get(0).getZ();
            for (int x = minOther + 1; x <= maxOther - 1; x++) {
                for (int y = minY + 1; y <= maxY - 1; y++) {
                    interiorBlocks.add(new BlockPos(x, y, z));
                }
            }
        } else { // Axis.X
            int x = portalBlocks.get(0).getX();
            for (int z = minOther + 1; z <= maxOther - 1; z++) {
                for (int y = minY + 1; y <= maxY - 1; y++) {
                    interiorBlocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return interiorBlocks;
    }

    private Direction.Axis getAxis(){
        // Check if all blocks have the same X coordinate
        BlockPos firstBlock = portalBlocks.get(0);
        boolean sameX = portalBlocks.stream().allMatch(pos -> pos.getX() == firstBlock.getX());

        // Check if all blocks have the same Z coordinate
        boolean sameZ = portalBlocks.stream().allMatch(pos -> pos.getZ() == firstBlock.getZ());

        if (sameX) {
            return Direction.Axis.X;
        } else if (sameZ) {
            return Direction.Axis.Z;
        } else {
            throw new IllegalArgumentException("Invalid portal frame: not aligned to X or Z axis");
        }
    }

    private boolean hasRequiredBooks(ChiseledBookshelfBlockEntity be, int x, int y) {
        // Example: Require books in specific slots based on position
        boolean[] requiredSlots = getRequiredSlots(x, y);
        if (requiredSlots.length != 6) throw new IllegalArgumentException("Required slots must be of length 6!!");
        for (int i = 0; i < 6; i++) {

            // TEMP: set requiredSlots to all true
            if (requiredSlots[i])
                be.setStack(i, Items.BOOK.getDefaultStack());
            else
                be.removeStack(i);


            if (be.getStack(i).getItem() != (requiredSlots[i] ? Items.BOOK : Items.AIR)) return false;
        }
        return true;
    }

    public boolean[] getRequiredSlots(int x, int y) {
        Random random = new Random(seedHash + x * 31L + y * 31L);
        boolean[] requiredSlots = new boolean[6];
        for (int i = 0; i < random.nextInt(3,5); i++) {
            // bound is exclusive, so 7 will give us 0-6
            requiredSlots[random.nextInt(6)] = true;
        }
        return requiredSlots;
    }

    private boolean isValidBookshelfComplete(BlockPos pos, BlockPos[][] grid) {
        int[] coords = Utils.findBlockPosCoordinates(grid, pos);
        if (coords == null) {
            System.out.println("coords is null");
            return false;
        }
        return isValidBookshelf(pos) && hasRequiredBooks((ChiseledBookshelfBlockEntity) world.getBlockEntity(pos), coords[0], coords[1]);
    }

    public void printDuplicateBlockPositions() {
        HashMap<BlockPos, Integer> blockPosCount = new HashMap<>();

        // Count occurrences of each BlockPos
        for (BlockPos pos : portalBlocks) {
            blockPosCount.put(pos, blockPosCount.getOrDefault(pos, 0) + 1);
        }

        // Print duplicates
        for (Map.Entry<BlockPos, Integer> entry : blockPosCount.entrySet()) {
            if (entry.getValue() > 1) {
                System.out.println("Duplicate BlockPos: " + entry.getKey() + " Count: " + entry.getValue());
            }
        }
    }

    public BlockPos[][] generatePortalGrid(List<BlockPos> frameBlocks, Direction.Axis axis, int minY, int maxY, int minOther, int maxOther) {
        // Get fixed coordinate (X for Z-axis portals, Z for X-axis portals)
        int fixedX = axis == Direction.Axis.X ? frameBlocks.get(0).getX() : -1;
        int fixedZ = axis == Direction.Axis.Z ? frameBlocks.get(0).getZ() : -1;

        // Create a set for quick lookups
        Set<BlockPos> frameSet = new HashSet<>(frameBlocks);

        // Calculate grid dimensions
        int height = maxY - minY + 1; // 5 for standard portals
        int width = maxOther - minOther + 1;  // 4 for standard portals
        BlockPos[][] portalGrid = new BlockPos[width][height];

        // shitty hack. WHY DO I NEED TO ADD 1?????
        int maxYMinusHeight = maxY - height+1;
        int maxOtherMinusWidth = maxOther - width+1;
        // Populate the grid (top to bottom, left to right)
        for (int i = 0; i < height; i++) {
            int currentY = maxYMinusHeight +i; // Start from bottom Y
            for (int j = 0; j < width; j++) {
                int currentH = (axis == Direction.Axis.Z ? maxOther - j : maxOtherMinusWidth + j); // Horizontal coordinate (X or Z)
                // Check if this position is part of the frame
                boolean isEdge =
                        currentY == minY || currentY == maxY ||
                                currentH == minOther || currentH == maxOther;

                BlockPos pos = null;
                if (isEdge) {
                    if (axis == Direction.Axis.Z) {
                        // Horizontal axis is X (fixed Z)
                        pos = new BlockPos(currentH, currentY, fixedZ);
                    } else {
                        // Horizontal axis is Z (fixed X)
                        pos = new BlockPos(fixedX, currentY, currentH);
                    }
                    // Only include positions that exist in the frame
                    if (!frameSet.contains(pos)) pos = null;
                }

                portalGrid[j][i] = pos;
                if (pos != null) {
                    ArmorStandEntity entity = new ArmorStandEntity(EntityType.ARMOR_STAND, world);
                    entity.setCustomNameVisible(true);
                    if (axis == Direction.Axis.Z) {
                        entity.setPos(pos.getX() + 0.5, pos.getY() - 1.8, pos.getZ() - 0.4);
                    } else {
                        entity.setPos(pos.getX() - 0.5, pos.getY() - 1.8, pos.getZ() + 0.5);
                    }
                    entity.setCustomName(Text.literal(j+","+i));
                    entity.setInvisible(true);
                    entity.setNoGravity(true);
                    entity.setInvulnerable(true);
                    //world.spawnEntity(entity);
                }
            }
        }

        return portalGrid;
    }
}