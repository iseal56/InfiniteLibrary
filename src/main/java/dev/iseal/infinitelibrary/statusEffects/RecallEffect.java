package dev.iseal.infinitelibrary.statusEffects;

import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.block.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class RecallEffect extends StatusEffect implements Portal {

    public static final int FALLBACK_REMAINING_TIME = Integer.MAX_VALUE;
    public static final int DISPLAY_PARTICLES_WHEN_LESS_THAN = 100;
    public static final int MAX_PARTICLE_COUNT = 256;

    public RecallEffect(int color) {
        super(
                StatusEffectCategory.NEUTRAL,
                color,
                EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, ColorHelper.withAlpha(8, color))
        );
    }

    //FIXME: darkness effect before leaving, the rest of the effect seems nice
    @Override
    public int getFadeTicks() {
        return 10;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(world, entity, amplifier);
        // this runs only on server for SOME REASON, so I don't need to check if its client

        // Get the smallest remaining duration of this effect.
        int minimumRemainingDuration = entity.getStatusEffects()
                .stream()
                .filter(statusEffectInstance -> (statusEffectInstance.getEffectType().value() == this))
                .mapToInt(StatusEffectInstance::getDuration)
                .min()
                .orElse(FALLBACK_REMAINING_TIME);

        if (minimumRemainingDuration < DISPLAY_PARTICLES_WHEN_LESS_THAN) {

            double normalizedTime = (DISPLAY_PARTICLES_WHEN_LESS_THAN - minimumRemainingDuration) / ((float) DISPLAY_PARTICLES_WHEN_LESS_THAN);
            int particleCount = (int) (MAX_PARTICLE_COUNT * normalizedTime);

            System.out.println("minimumRemainingDuration = " + minimumRemainingDuration);
            System.out.println("expo = " + particleCount);
            // try to not make the server explode
            particleCount = Math.clamp(particleCount, 1, MAX_PARTICLE_COUNT);

            Random random = entity.getWorld().getRandom();
            ArrayList<Vec3d> list = Utils.getSphereLocations(entity.getPos(), 2, particleCount, Optional.of(random));
            list.forEach(pos -> ((ServerWorld) entity.getWorld()).spawnParticles(
                    ParticleTypes.ENCHANT,
                    // pos
                    pos.getX(), pos.getY() + entity.getStandingEyeHeight(), pos.getZ(),
                    // count
                    1,
                    // offset
                    random.nextFloat(), random.nextFloat(), random.nextFloat(),
                    // speed
                    3.0d
            ));
        }

        if (minimumRemainingDuration == 1) {
            Random random = entity.getWorld().getRandom();

            ((ServerWorld) entity.getWorld()).spawnParticles(
                    ParticleTypes.PORTAL,
                    // pos
                    entity.getPos().getX(), entity.getEyeY(), entity.getPos().getZ(),
                    // count
                    500,
                    // offset
                    random.nextFloat(), random.nextFloat(), random.nextFloat(),
                    // speed
                    3.0d
            );
            entity.tryUsePortal(this, entity.getBlockPos());
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public @Nullable TeleportTarget createTeleportTarget(ServerWorld world, Entity entity, BlockPos pos) {
        ServerWorld targetLevel = world.getServer().getWorld(World.OVERWORLD);
        if (targetLevel == null) {
            return null;
        } else {
            if (entity instanceof ServerPlayerEntity serverPlayer) {
                return serverPlayer.getRespawnTarget(false, TeleportTarget.NO_OP);
            }

            BlockPos blockpos = targetLevel.getSpawnPos();
            float facing = 0.0F;
            Set<PositionFlag> set = PositionFlag.combine(PositionFlag.DELTA, PositionFlag.ROT);
            Vec3d vec3 = entity.getWorldSpawnPos(targetLevel, blockpos).toBottomCenterPos();

            return new TeleportTarget(targetLevel, vec3, Vec3d.ZERO, facing, 0.0F, set, TeleportTarget.NO_OP);
        }
    }
}

