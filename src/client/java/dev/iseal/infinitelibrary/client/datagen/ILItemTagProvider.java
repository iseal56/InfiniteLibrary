package dev.iseal.infinitelibrary.client.datagen;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import dev.iseal.infinitelibrary.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ILItemTagProvider extends FabricTagProvider<Item> {

    public ILItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TagRegistry.IVORY_REPAIR_MATERIALS)
                .add(ItemRegistry.IVORY_BRICK);
    }
}
