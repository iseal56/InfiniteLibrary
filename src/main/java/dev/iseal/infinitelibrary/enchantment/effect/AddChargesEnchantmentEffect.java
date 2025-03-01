package dev.iseal.infinitelibrary.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.iseal.infinitelibrary.components.ChargesAmountComponent;
import dev.iseal.infinitelibrary.registry.ComponentTypeRegistry;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record AddChargesEnchantmentEffect(EnchantmentLevelBasedValue multiplier) implements EnchantmentEntityEffect {
    public static final MapCodec<AddChargesEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("multiplier").forGetter(AddChargesEnchantmentEffect::multiplier)
            ).apply(instance, AddChargesEnchantmentEffect::new)
    );

    private static final Float EXPERIENCE_MULTIPLIER = 0.2f;

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target instanceof LivingEntity entity)) {
            return;
        }
        if (context.owner() == null)
            return;

        if (context.slot() != EquipmentSlot.MAINHAND)
            return;

        // check if entity dies
        // if so, add charges to the item
        ItemStack item = context.stack();
        LivingEntity owner = context.owner();
        if (entity.getHealth() <= 0) {
            if (item.get(ComponentTypeRegistry.CHARGES_AMOUNT) == null) {
                item.set(ComponentTypeRegistry.CHARGES_AMOUNT, new ChargesAmountComponent(0));
            }
            float charges = entity.getExperienceToDrop(world, owner) * EXPERIENCE_MULTIPLIER * multiplier.getValue(level);
            System.out.println("Adding " + charges + " charges");
            item.set(ComponentTypeRegistry.CHARGES_AMOUNT, new ChargesAmountComponent(item.get(ComponentTypeRegistry.CHARGES_AMOUNT).amount() + charges));
            System.out.println("Charges now " + item.get(ComponentTypeRegistry.CHARGES_AMOUNT));
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}