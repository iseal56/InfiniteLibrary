package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class EnchantmentRegistry {

    private static EnchantmentRegistry instance;
    public static EnchantmentRegistry getInstance() {
        if (instance == null) {
            instance = new EnchantmentRegistry();
        }
        return instance;
    }

    public static final RegistryKey<Enchantment> SWORD_ENCHANTMENT = key("enchantment");

    private static RegistryKey<Enchantment> key(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, IL.identifier(id));
    }

    public void initialize() {

    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

    public static void bootstrap(Registerable<Enchantment> registerable) {
        // lookup
        RegistryEntryLookup<DamageType> damageTypeLookup = registerable.getRegistryLookup(RegistryKeys.DAMAGE_TYPE);
        RegistryEntryLookup<Enchantment> enchantmentLookup = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        RegistryEntryLookup<EntityType<?>> entityTypeLookup = registerable.getRegistryLookup(RegistryKeys.ENTITY_TYPE);
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerable.register(SWORD_ENCHANTMENT, create(
                SWORD_ENCHANTMENT.getValue(),
                itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                2,
                AttributeModifierSlot.MAINHAND,
                builder -> {
                }
        ));
    }

    public static Enchantment create(Identifier id, RegistryEntryList<Item> supportedItems, int maxLevel, AttributeModifierSlot slot, EffectsAdder effectsAdder) {
        Enchantment.Builder builder = Enchantment.builder(Enchantment.definition(supportedItems, 5, maxLevel, Enchantment.leveledCost(5, 6), Enchantment.leveledCost(20, 6), 2, slot));
        effectsAdder.addEffects(builder);
        return builder.build(id);
    }

    public interface EffectsAdder {
        void addEffects(Enchantment.Builder builder);
    }
}
