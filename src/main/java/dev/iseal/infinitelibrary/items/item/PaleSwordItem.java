package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class PaleSwordItem extends SwordItem {
    public static final int TICKS_PER_UNIT = 20;
    public static final Supplier<StatusEffectInstance> WITHER = () -> new StatusEffectInstance(
            StatusEffects.WITHER,
            60,
            1
    );
    private static final float DRAIN_FACTOR = 10;
    private static final float DRAIN_CONSTANT = 1f;
    private static final int STRENGTH_CAP = 50;
    private static final int STRENGTH_PER_UNIT = 1;
    private static final String TIME_KEY = "time_held";

    public PaleSwordItem() {
        super(ItemRegistry.IVORY_TOOL_MATERIAL, 10, -2.4F, new Item.Settings());
    }

    @Override
    public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean isHeld) {
        super.inventoryTick(stack, level, entity, slot, isHeld);

        // Get the length of time the item has been held.

        int timeHeld = stack.getOrCreateNbt().getInt(TIME_KEY);
        System.out.println("Held for: " + timeHeld + " and is holding: " + isHeld);
        // Potential problems:
        // Putting the sword into a block inventory from when it was held directly.
        // Taking it out directly to the hand.
        // Think about this later.

        // If it is not held, zero out the time-held component.
        if (!isHeld) {
            timeHeld = 0;
        } else {
            timeHeld++;
        }

        float unitsHeld = timeHeld / ((float) TICKS_PER_UNIT);

        // Set the time held value.
        stack.getOrCreateNbt().putInt(TIME_KEY, timeHeld);


        // Now, drain xp and/or health.
        if (unitsHeld > 0) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.STRENGTH,
                        Math.min((int) (unitsHeld * STRENGTH_PER_UNIT), STRENGTH_CAP),
                        21
                ));
            }
            // Drain experience.
            float xpToDrainRaw = (DRAIN_CONSTANT + unitsHeld / DRAIN_FACTOR);
            int xpToDrain = ((int) xpToDrainRaw) + ((level.random.nextFloat() < (xpToDrainRaw - (int) xpToDrainRaw)) ? 1 : 0);
            if (xpToDrain > 0) {
                int xpDrained = 0;
                if (entity instanceof PlayerEntity player) {
                    xpDrained = Math.min(player.totalExperience, xpToDrain);
                    System.out.println("Total experience = " + player.totalExperience + "; taking = " + xpDrained);
                    player.addExperience(-xpDrained);
                    System.out.println("Total experience is now " + player.totalExperience);
                }

                int xpLacking = xpToDrain - xpDrained;
                if (xpLacking > 0 && level instanceof ServerWorld && entity.age % TICKS_PER_UNIT == 0 && entity instanceof LivingEntity livingEntity) {
                    // The entity did not have sufficient investiture to sustain the sword.
                    // Begin to feed!
                    livingEntity.damage(livingEntity.getDamageSources().magic(), (int) (1 + Math.sqrt(xpLacking)));
                    livingEntity.addStatusEffect(WITHER.get());
                }
            }
        }

    }
}
