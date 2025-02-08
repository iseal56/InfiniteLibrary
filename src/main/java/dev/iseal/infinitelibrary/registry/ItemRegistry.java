package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.item.PaleSwordItem;
import dev.iseal.infinitelibrary.items.materials.IvoryToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ItemRegistry {

    private static ItemRegistry INSTANCE;
    public static ItemRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ItemRegistry();
        }
        return INSTANCE;
    }

    public static final Item IVORY_INGOT = register(new Item(new Item.Settings()), RegistryKey.of(RegistryKeys.ITEM, new Identifier(IL.MOD_ID, "ivory_ingot")));
    public static final ToolMaterial IVORY_TOOL_MATERIAL = new IvoryToolMaterial();
    public static final Item PALE_SWORD = register(new PaleSwordItem(), RegistryKey.of(RegistryKeys.ITEM, new Identifier(IL.MOD_ID, "pale_sword")));

    private static Item register(Item item, RegistryKey<Item> key) {
        return Registry.register(Registries.ITEM, key, item);
    }

    public void initialize() {
        // This method is empty.
    }

}
