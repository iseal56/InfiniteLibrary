package dev.iseal.infinitelibrary.statusEffects;

import dev.iseal.infinitelibrary.registry.EffectRegistry;
import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.block.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class RecallEffect extends StatusEffect implements Portal {

    public RecallEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x00FF00);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient)
            return;
        if (!(entity instanceof ServerPlayerEntity)) {
            entity.removeStatusEffect(RegistryEntry.of(EffectRegistry.RECALL));
        }
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
                .orElse(Integer.MAX_VALUE);
        if (minimumRemainingDuration == Integer.MAX_VALUE) {
            return false;
        }

        if (minimumRemainingDuration > 20) {
            //double exponentialValue = 150 * Math.exp(-0.1 * (minimumRemainingDuration - 20));

            double normalizedTime = 1 - (minimumRemainingDuration) / 100.0;
            double quadOutExponentialValue = 1 - Math.pow(1 - normalizedTime, 2);
            double exponentialValue = 150 * quadOutExponentialValue;

            System.out.println("minimumRemainingDuration = " + minimumRemainingDuration);
            System.out.println("expo = " + exponentialValue);
            // try to not make the server explode
            if (exponentialValue >= 500)
                return false;

            if (exponentialValue <= 0)
                exponentialValue = 1;

            Random random = entity.getWorld().getRandom();
            ArrayList<Vec3d> list = Utils.getSphereLocations(entity.getPos(), 2,
                    (int) exponentialValue,
                    Optional.of(random));
            list.forEach(pos ->
                    ((ServerWorld) entity.getWorld()).spawnParticles(
                            ParticleTypes.ENCHANT,
                            // pos
                            pos.getX(), pos.getY(), pos.getZ(),
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
                            entity.getPos().getX(), entity.getPos().getY(), entity.getPos().getZ(),
                            // count
                            500,
                            // offset
                            random.nextFloat(), random.nextFloat(), random.nextFloat(),
                            // speed
                            3.0d
                    );
            //entity.tryUsePortal(this, entity.getBlockPos());
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        return null;
    }

    //FIXME: darkness effect before leaving, the rest of the effect seems nice
    @Override
    public int getFadeTicks() {
        return 10;
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

            return new TeleportTarget(
                    targetLevel,
                    vec3,
                    Vec3d.ZERO,
                    facing,
                    0.0F,
                    set,
                    TeleportTarget.NO_OP
            );
        }
    }
}

