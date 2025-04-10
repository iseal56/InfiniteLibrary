package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class TagRegistry {

    public static final TagKey<Item> IVORY_REPAIR_MATERIALS = TagKey.of(RegistryKeys.ITEM, IL.identifier("ivory_repair_materials"));

}
