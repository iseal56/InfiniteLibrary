package dev.iseal.infinitelibrary.client.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;

public class RecipeProvider extends FabricRecipeProvider{

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
                createShapeless(RecipeCategory.TRANSPORTATION, ItemRegistry.TOME_OF_RETURN)
                        .input(BlockRegistry.GLEAMING_CHISELED_IVORY.asItem())
                        .input(Items.BOOK)
                        .input(Items.ENDER_PEARL)

                        .criterion(hasItem(BlockRegistry.GLEAMING_CHISELED_IVORY), conditionsFromItem(BlockRegistry.GLEAMING_CHISELED_IVORY))
                        .offerTo(exporter);

                // generate slabs, stairs, and walls
                generateFamily(BlockModelProvider.IVORY_BRICK_FAMILY,
                        FeatureSet.of(FeatureFlags.VANILLA));
                generateFamily(BlockModelProvider.POLISHED_IVORY_FAMILY,
                        FeatureSet.of(FeatureFlags.VANILLA));
                generateFamily(BlockModelProvider.GILDED_IVORY_BRICK_FAMILY,
                        FeatureSet.of(FeatureFlags.VANILLA));
                generateFamily(BlockModelProvider.GILDED_POLISHED_IVORY_FAMILY,
                        FeatureSet.of(FeatureFlags.VANILLA));
            }
        };
    }

    @Override
    public String getName() {
        return "ILRecipeProvider";
    }

}
