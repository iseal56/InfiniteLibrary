package dev.iseal.infinitelibrary.items.item_groups;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;

public class InfiniteLibraryGroups {

    public static final RegistryKey<ItemGroup> BLOCKS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(IL.MOD_ID, "blocks_group"));
    public static final RegistryKey<ItemGroup> ITEMS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(IL.MOD_ID, "items_group"));
    public static final ItemGroup BLOCKS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BlockRegistry.GLEAMING_CHISELED_IVORY))
            .displayName(Text.translatable("itemGroup.infinitelibrary.blocks_group"))
            .build();
    public static final ItemGroup ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemRegistry.IVORY_BRICK))
            .displayName(Text.translatable("itemGroup.infinitelibrary.items_group"))
            .build();

    private static final HashMap<RegistryKey<ItemGroup>, ArrayList<Item>> itemGroups = new HashMap<>();

    public static void initialize() {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, BLOCKS_GROUP_KEY, BLOCKS_GROUP);
        Registry.register(Registries.ITEM_GROUP, ITEMS_GROUP_KEY, ITEMS_GROUP);

        itemGroups.forEach(
                (key, items) -> ItemGroupEvents.modifyEntriesEvent(key).register(
                        (itemGroup) -> items.forEach(itemGroup::add)
                )
        );
    }

    public static void addItemToGroup(RegistryKey<ItemGroup> key, Item item) {
        if (!itemGroups.containsKey(key)) {
            itemGroups.put(key, new ArrayList<>());
        }
        itemGroups.get(key).add(item);
    }

}
