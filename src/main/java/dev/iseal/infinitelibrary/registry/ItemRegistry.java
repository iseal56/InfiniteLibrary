package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.item.PaleSwordItem;
import dev.iseal.infinitelibrary.items.item.SpellBookItem;
import dev.iseal.infinitelibrary.items.item_groups.InfiniteLibraryGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ItemRegistry {

    public static final Item IVORY_BRICK = register(
            new Item.Settings().maxCount(64).rarity(Rarity.RARE),
            Identifier.of(IL.MOD_ID, "ivory_brick"),
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
            new Item.Settings().registryKey(key("pale_sword")),
            IL.identifier("pale_sword"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item SPELL_BOOK = register(
            SpellBookItem::new,
            new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(IL.MOD_ID, "spell_book")))
                    .rarity(Rarity.EPIC)
                    .maxCount(1)
                    .maxDamage(100),
            IL.identifier("spell_book"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item SCRAPS_OF_WISDOM = register(
            new Item.Settings().maxCount(64).rarity(Rarity.COMMON),
            Identifier.of(IL.MOD_ID, "scraps_of_wisdom"),
            true,
            InfiniteLibraryGroups.ITEMS_GROUP_KEY
    );

    public static final Item TOME_OF_RETURN = register(
            new Item.Settings().maxCount(1).rarity(Rarity.RARE),
            Identifier.of(IL.MOD_ID, "tome_of_return"),
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
        return register(Item::new, settings, id, addToGroup, groupKey);
    }

    public static RegistryKey<Item> key(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, IL.identifier(name));
    }

    public void initialize() {
        // This method is empty.
    }

}
