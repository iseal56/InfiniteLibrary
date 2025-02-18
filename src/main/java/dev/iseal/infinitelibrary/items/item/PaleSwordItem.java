package dev.iseal.infinitelibrary.items.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.DamageSourceRegistry;
import dev.iseal.infinitelibrary.registry.DataComponentTypeRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Supplier;

public class PaleSwordItem extends SwordItem {
    public static final int TICKS_PER_UNIT = 20;
    public static final Supplier<StatusEffectInstance> WITHER = () -> new StatusEffectInstance(
            StatusEffects.WITHER,
            60,
            1,
            true,
            false
    );
    private static final float DRAIN_FACTOR = 10;
    private static final float DRAIN_CONSTANT = 1f;
    public static final int DAMAGE_CAP = 50;
    private static final int DAMAGE_PER_UNIT = 1;
    private static final Identifier DAMAGE_MODIFIER_ID = Identifier.of(IL.MOD_ID, "pale_sword_damage_bonus");

    public PaleSwordItem() {
        super(
                ItemRegistry.IVORY_TOOL_MATERIAL,
                10,
                -2.4F,
                new Item.Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(IL.MOD_ID, "pale_sword")))
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, World level, Entity entity, int slot, boolean isHeld) {
        super.inventoryTick(stack, level, entity, slot, isHeld);
        // Get the length of time the item has been held.
        int damage_increase = stack.getOrDefault(DataComponentTypeRegistry.INCREASE_DAMAGE, 0);
        int timeHeld = stack.getOrDefault(DataComponentTypeRegistry.TIME_HELD, 0);

        //System.out.println("Held for: " + timeHeld + " and is holding: " + isHeld);

        // Problem with this approach:
        // 1. Putting the sword into a block inventory from when it was held directly.
        // 2. Taking it out directly to the hand.
        // Think about this later.
        // - StarTraveler

        // Fixing issue 1 fixes the second one as well
        // A possible solution is to store in the NBT the last time the sword was held, and then check in getAttributeModifiers
        // if it has been more than 1 second AND timeHeld is greater than 0. If so, set time_held and damage_bonus to 0.
        // - ISeal

        // If it is not held, zero out the time-held component.
        if (!isHeld) {
            timeHeld = 0;
        } else {
            timeHeld++;
        }

        float unitsHeld = timeHeld / ((float) TICKS_PER_UNIT);

        // Set the time held value.
        stack.set(DataComponentTypeRegistry.TIME_HELD, timeHeld);


        // Now, drain xp and/or health.
        if (unitsHeld > 0) {
            // Calculate damage bonus and store in NBT
            int damageBonus = Math.min((int) (unitsHeld * DAMAGE_PER_UNIT), DAMAGE_CAP);
            stack.set(DataComponentTypeRegistry.INCREASE_DAMAGE, damageBonus);

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
                if (xpLacking > 0 && level instanceof ServerWorld serverWorld && entity.age % TICKS_PER_UNIT == 0 && entity instanceof LivingEntity livingEntity) {
                    // The entity did not have sufficient investiture to sustain the sword.
                    // Begin to feed!
                    livingEntity.damage(serverWorld, DamageSourceRegistry.ABSORB_KNOWLEDGE, (int) (1 + Math.sqrt(xpLacking)));
                    livingEntity.addStatusEffect(WITHER.get());
                }
            }
        } else {
            stack.set(DataComponentTypeRegistry.INCREASE_DAMAGE, 0);
        }

    }


    public static class PaleSwordSettings extends Item.Settings {
        @Override
        public Item.Settings attributeModifiers(AttributeModifiersComponent attributeModifiers) {

            int damageBonus = 0;

            attributeModifiers.with(
                    EntityAttributes.ATTACK_DAMAGE,
                    new EntityAttributeModifier(DAMAGE_MODIFIER_ID, damageBonus, EntityAttributeModifier.Operation.ADD_VALUE),
                    AttributeModifierSlot.MAINHAND
            );

            return super.attributeModifiers(attributeModifiers);
        }
    }
}
