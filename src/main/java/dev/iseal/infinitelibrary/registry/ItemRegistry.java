package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.item.PaleSwordItem;
import dev.iseal.infinitelibrary.items.item.SpellBookItem;
import dev.iseal.infinitelibrary.items.item.TomeOfReturnItem;
import dev.iseal.infinitelibrary.items.item_groups.InfiniteLibraryGroups;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;
import java.util.function.Function;

public class ItemRegistry {

    public static final Item IVORY_BRICK = register(
            new Item.Settings().maxCount(64).rarity(Rarity.RARE),
            IL.identifier("ivory_brick"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );
    // TODO: Create own tags and TagBuilder in datagen - using ivory brick to repair later
    public static final ToolMaterial IVORY_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            1024,
            4.0f,
            4,
            1,
            ItemTags.GOLD_TOOL_MATERIALS
    );

    public static final Item PALE_SWORD = register(
            settings -> new PaleSwordItem(ItemRegistry.IVORY_TOOL_MATERIAL, 2, -2.4F, settings),
            new Item.Settings(),
            IL.identifier("pale_sword"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item SPELL_BOOK = register(
            SpellBookItem::new,
            new Item.Settings()
                    .rarity(Rarity.EPIC)
                    .maxCount(1)
                    .maxDamage(100),
            IL.identifier("spell_book"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item SCRAPS_OF_WISDOM = register(
            new Item.Settings().maxCount(64).rarity(Rarity.COMMON),
            IL.identifier("scraps_of_wisdom"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item ARCHIVIST_APPLE = register(
            new Item.Settings().rarity(Rarity.RARE).food(
                    new FoodComponent.Builder()
                            .nutrition(4)
                            .alwaysEdible()
                            .saturationModifier(4.0f)
                            .build(),
                    ConsumableComponent.builder()
                            .consumeSeconds(1.6F)
                            .useAction(UseAction.EAT)
                            .sound(SoundEvents.ENTITY_GENERIC_EAT)
                            .consumeParticles(true)
                            .consumeEffect(
                                    new ApplyEffectsConsumeEffect(
                                            List.of(
                                                    new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1),
                                                    new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0),
                                                    new StatusEffectInstance(StatusEffectRegistry.KNOWLEDGE, 2400, 0)
                                            )
                                    )
                            )
                            .build()
            ),
            IL.identifier("archivist_apple"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item TOME_OF_RETURN = register(
            TomeOfReturnItem::new,
            new Item.Settings().maxCount(1).rarity(Rarity.RARE),
            IL.identifier("tome_of_return"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    private static ItemRegistry INSTANCE;

    public static ItemRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemRegistry();
        }
        return INSTANCE;
    }

    private static Item register(Item item, RegistryKey<Item> key, boolean addToGroup, RegistryKey<ItemGroup> groupKey) {
        if (addToGroup)
            InfiniteLibraryGroups.addItemToGroup(groupKey, item);

        return Registry.register(Registries.ITEM, key, item);
    }

    private static Item register(Function<Item.Settings, Item> item, Item.Settings settings, Identifier id, boolean addToGroup, RegistryKey<ItemGroup> groupKey) {

        return register(
                item.apply(settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id))),
                RegistryKey.of(RegistryKeys.ITEM, id),
                addToGroup,
                groupKey
        );
    }

    private static Item register(Item.Settings settings, Identifier id, boolean addToGroup, RegistryKey<ItemGroup> groupKey) {
        return register(Item::new, settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id)), id, addToGroup, groupKey);
    }

    public static RegistryKey<Item> key(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, IL.identifier(name));
    }

    public void initialize() {
        // This method is empty.
    }

}
