package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.item.component.TimeHeldComponent;
import dev.iseal.infinitelibrary.registry.ComponentTypeRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class PaleSwordItem extends SwordItem {

    public static final Identifier TIME_STRENGTH_NAME = IL.identifier("strength_from_time_held");
    public static final int TICKS_PER_UNIT = 20;
    public static final Supplier<StatusEffectInstance> WITHER = () -> new StatusEffectInstance(
            StatusEffects.WITHER,
            60,
            1
    );
    private static final float DRAIN_FACTOR = 20;
    private static final float DRAIN_CONSTANT = 1f;
    private static final int DAMAGE_CAP = 100;
    private static final int DAMAGE_PER_UNIT = 5;


    public PaleSwordItem(ToolMaterial toolMaterial, float attackDamageBonus, float attackCooldownBonus, Settings properties) {
        super(toolMaterial, attackDamageBonus, attackCooldownBonus, properties);
    }


    @Override
    public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean isHeld) {
        super.inventoryTick(stack, level, entity, slot, isHeld);

        // Get the length of time the item has been held.
        TimeHeldComponent timeHeldComponent = stack.get(ComponentTypeRegistry.TIME_HELD);
        int timeHeld = timeHeldComponent == null ? 0 : timeHeldComponent.time();

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

        // Get the item attributes, and update its strength attribute.
        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        if (modifiers == null) {
            modifiers = AttributeModifiersComponent.builder().build();
        }
        modifiers = modifiers.with(
                EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(
                        TIME_STRENGTH_NAME,
                        Math.min((int) (unitsHeld * DAMAGE_PER_UNIT), DAMAGE_CAP),
                        EntityAttributeModifier.Operation.ADD_VALUE
                ), AttributeModifierSlot.MAINHAND
        );
        if (isHeld) {
            modifiers = modifiers.with(
                    EntityAttributes.GRAVITY,
                    new EntityAttributeModifier(
                            TIME_STRENGTH_NAME,
                            -0.2,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    AttributeModifierSlot.MAINHAND
            );
        }

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers);
        // Update the time held modifier.
        stack.set(ComponentTypeRegistry.TIME_HELD, new TimeHeldComponent(timeHeld));


        // Now, drain xp and/or health.
        if (unitsHeld > 0) {

            // Drain experience.
            float xpToDrainRaw = (DRAIN_CONSTANT + unitsHeld / DRAIN_FACTOR);
            int xpToDrain = ((int) xpToDrainRaw) + ((level.random.nextFloat() < (xpToDrainRaw - (int) xpToDrainRaw)) ? 1 : 0);
            if (xpToDrain > 0) {
                int xpDrained = 0;
                if (entity instanceof PlayerEntity player) {
                    xpDrained = Math.min(player.totalExperience, xpToDrain);
                    // System.out.println("Total experience = " + player.totalExperience + "; taking = " + xpDrained);
                    player.addExperience(-xpDrained);
                    // System.out.println("Total experience is now " + player.totalExperience);
                }

                int xpLacking = xpToDrain - xpDrained;
                if (xpLacking > 0 && level instanceof ServerWorld serverLevel && entity.age % TICKS_PER_UNIT == 0 && entity instanceof LivingEntity livingEntity) {
                    // The entity did not have sufficient investiture to sustain the sword.
                    // Begin to feed!
                    livingEntity.damage(
                            serverLevel,
                            livingEntity.getDamageSources().magic(),
                            (int) (1 + Math.sqrt(xpLacking))
                    );
                    livingEntity.addStatusEffect(WITHER.get());
                }
            }
        }

    }
}
