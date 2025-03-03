package dev.iseal.infinitelibrary.statusEffects;

import dev.iseal.infinitelibrary.registry.EffectRegistry;
import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class RecallEffect extends StatusEffect {
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
        if (!(entity instanceof ServerPlayerEntity serverPlayerEntity))
            return false;

        Random random = entity.getWorld().getRandom();
        ArrayList<Vec3d> list = Utils.getCylinderLocations(entity.getPos(), 2,
                15, 0.25,
                Optional.of(random));
        list.forEach(pos ->
                ((ServerWorld)entity.getWorld()).spawnParticles(ParticleTypes.ENCHANT, pos.getX(), pos.getY(), pos.getZ(), 1, random.nextFloat(), random.nextFloat(), random.nextFloat(), 3.0d)
        );

        if (entity.getStatusEffect(RegistryEntry.of(EffectRegistry.RECALL)).getDuration() > 1)
            return true;

        RegistryKey<World> spawnDimension = serverPlayerEntity.getSpawnPointDimension();
        ServerWorld teleportToWorld = world.getServer().getWorld(spawnDimension);
        BlockPos spawnLocation = (serverPlayerEntity.getSpawnPointPosition() == null) ? teleportToWorld.getSpawnPos() : serverPlayerEntity.getSpawnPointPosition();
        serverPlayerEntity.teleport(teleportToWorld, spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), Set.of(), serverPlayerEntity.getYaw(), serverPlayerEntity.getPitch(), false);
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        return ParticleTypes.ENCHANT;
    }
}
