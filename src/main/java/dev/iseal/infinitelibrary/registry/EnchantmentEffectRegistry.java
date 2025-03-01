package dev.iseal.infinitelibrary.registry;

import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.enchantment.effect.AddChargesEnchantmentEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;
import java.util.function.UnaryOperator;

public class EnchantmentEffectRegistry {

    private static EnchantmentEffectRegistry INSTANCE;
    public static EnchantmentEffectRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnchantmentEffectRegistry();
        }
        return INSTANCE;
    }

    public static MapCodec<AddChargesEnchantmentEffect> CHARGES_EFFECT = register("charges_effect", AddChargesEnchantmentEffect.CODEC);
    public static ComponentType<AddChargesEnchantmentEffect> CHARGES_COMPONENT = registerType("charges_component", builder -> builder.codec(AddChargesEnchantmentEffect.CODEC.codec()));
    //TODO: please for the love of anything find a better name
    public static final RegistryKey<Enchantment> SWORD_ENCHANT = of("sword_enchant");

    private static RegistryKey<Enchantment> of(String path) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, IL.identifier(path));
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, IL.identifier(id), codec);
    }

    private static <T> ComponentType<T> registerType(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, IL.identifier(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public void initialize() {
        // This method is empty.
    }
}
