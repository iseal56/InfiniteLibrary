package dev.iseal.infinitelibrary.enchantmentEffects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.iseal.infinitelibrary.registry.ComponentTypeRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

public record ChargesEffect(EnchantmentValueEffect chargeAmount) {
    public static final Codec<ChargesEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    EnchantmentValueEffect.CODEC.fieldOf("chargeAmount").forGetter(ChargesEffect::chargeAmount)
            )
            .apply(instance, ChargesEffect::new));

    public static int getChargeAmount(LivingEntity entity) {
        MutableFloat chargeAmount = new MutableFloat(0);
        ItemStack stack = entity.getMainHandStack();

        EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
            ChargesEffect effect = enchantment.value().effects().get(ComponentTypeRegistry.CHARGES_EFFECT);
            if (effect != null) {
                chargeAmount.setValue(effect.chargeAmount().apply(level, entity.getRandom(), chargeAmount.floatValue()));
            }
        });
        return chargeAmount.intValue();
    }

}