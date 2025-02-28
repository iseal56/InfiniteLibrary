package dev.iseal.infinitelibrary.registry;

import com.mojang.serialization.MapCodec;
import com.nimbusds.oauth2.sdk.id.Identifier;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.enchantment.effect.ChargesEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class EnchantmentEffectRegistry {
    public static final RegistryKey<Enchantment> THUNDERING = of("thundering");
    public static MapCodec<ChargesEffect> CHARGES_EFFECT = register("charges_effect", ChargesEffect.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, IL.identifier(path));
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, IL.identifier(id), codec);
    }
}
