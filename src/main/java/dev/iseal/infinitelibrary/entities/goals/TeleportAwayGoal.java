package dev.iseal.infinitelibrary.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class TeleportAwayGoal extends Goal {

    private final PathAwareEntity mob;
    private final int minDistance;
    private final int maxDistance;
    private final Random random;
    private long nextTeleportTime;

    public TeleportAwayGoal(PathAwareEntity mob, int minDistance, int maxDistance) {
        this.mob = mob;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.random = mob.getRandom();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public TeleportAwayGoal(PathAwareEntity mob) {
        this(mob, 8, 16); // default min distance of 8 blocks, max of 16
    }

    @Override
    public boolean canStart() {
        return nextTeleportTime <= System.currentTimeMillis();
    }

    @Override
    public void start() {
        boolean hasTarget = mob.getTarget() != null;

        if (teleportToSafety()) {
            nextTeleportTime = System.currentTimeMillis() + (hasTarget ? 5000 : 10000);
        } else {
            // last tp failed, so wait less
            nextTeleportTime = System.currentTimeMillis() + (hasTarget ? 500 : 1000);
        }
    }

    public boolean teleportToSafety() {
        World world = mob.getWorld();
        BlockPos currentPos = mob.getBlockPos();
        List<BlockPos> validPositions = new ArrayList<>();

        // search square area around the mob
        for (int x = -maxDistance; x <= maxDistance; x++) {
            for (int z = -maxDistance; z <= maxDistance; z++) {
                // skip positions that are too close
                double distanceSq = x*x + z*z;
                if (distanceSq < minDistance*minDistance || distanceSq > maxDistance*maxDistance) {
                    continue;
                }

                BlockPos targetPos = currentPos.add(x, 0, z);
                BlockPos safePos = findSafeYPosition(world, targetPos);

                if (safePos != null) {
                    validPositions.add(safePos);
                }
            }
        }

        if (validPositions.isEmpty()) {
            System.out.println("No valid teleport positions found");
            return false;
        }

        // sort positions by distance (farthest first)
        validPositions.sort(Comparator.comparingDouble(pos ->
                -pos.getSquaredDistance(currentPos)));

        // take the top 25% of positions (the farthest ones)
        int topCount = Math.max(1, validPositions.size() / 4);
        List<BlockPos> preferredPositions = validPositions.subList(0, topCount);

        // choose a random position from the preferred ones
        BlockPos teleportPos = preferredPositions.get(random.nextInt(preferredPositions.size()));

        return teleportMob(teleportPos);
    }

    private boolean teleportMob(BlockPos pos) {
        double x = pos.getX() + 0.5;
        double y = pos.getY();
        double z = pos.getZ() + 0.5;

        mob.teleport(x,y,z,true);
        mob.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);

        return true;
    }

    private BlockPos findSafeYPosition(World world, BlockPos pos) {
        int startY = mob.getBlockY() + 3;
        BlockPos.Mutable mutablePos = new BlockPos.Mutable(pos.getX(), startY, pos.getZ());

        // check downward for solid ground with space above
        for (int i = 0; i < 10; i++) {
            BlockPos groundPos = mutablePos.down();

            if (isSolidGround(world, groundPos) &&
                    world.isAir(mutablePos) &&
                    world.isAir(mutablePos.up())) {
                return mutablePos.toImmutable();
            }

            mutablePos.move(0, -1, 0);
            if (mutablePos.getY() <= world.getBottomY()) {
                break;
            }
        }

        // no pos was found
        return null;
    }

    private boolean isSolidGround(World world, BlockPos pos) {
        return world.getBlockState(pos).isSolidBlock(world, pos) &&
                !world.getBlockState(pos).isLiquid();
    }
}