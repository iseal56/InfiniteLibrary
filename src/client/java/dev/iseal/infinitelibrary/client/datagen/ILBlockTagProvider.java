package dev.iseal.infinitelibrary.client.datagen;

import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ILBlockTagProvider extends FabricTagProvider<Block> {

    public static final TagKey<Block> WALLS_TAG = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("walls"));

    public ILBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(WALLS_TAG)
                .add(BlockRegistry.IVORY_BRICK_WALL)
                .add(BlockRegistry.GILDED_IVORY_BRICK_WALL)
                .add(BlockRegistry.POLISHED_IVORY_WALL)
                .add(BlockRegistry.GILDED_POLISHED_IVORY_WALL);
    }
}
