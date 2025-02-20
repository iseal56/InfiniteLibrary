package dev.iseal.infinitelibrary.client.datagen;

import com.google.gson.JsonObject;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;

public class BlockModelProvider extends FabricModelProvider {

    private final FabricDataOutput output;

    public BlockModelProvider(FabricDataOutput output) {
        super(output);
        this.output = output;
    }

    private final Block[] simpleCubes = new Block[]{
            BlockRegistry.IVORY_BOOKSHELF,
            BlockRegistry.GLEAMING_CHISELED_IVORY,
            BlockRegistry.DULL_CHISELED_IVORY,

            // ivory blocks
            BlockRegistry.CHISELED_IVORY,

            // gilded ivory blocks
            BlockRegistry.GILDED_CHISELED_IVORY,
    };

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (Block block : simpleCubes) {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        }

        blockStateModelGenerator.registerSingleton(BlockRegistry.IVORY_PILLAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        blockStateModelGenerator.registerSingleton(BlockRegistry.GILDED_IVORY_PILLAR, TexturedModel.END_FOR_TOP_CUBE_COLUMN);

        blockStateModelGenerator.registerSingleton(BlockRegistry.OLD_BOOKSHELF, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        blockStateModelGenerator.registerSingleton(BlockRegistry.OLD_EMPTY_BOOKSHELF, TexturedModel.END_FOR_TOP_CUBE_COLUMN);


        BlockStateModelGenerator.BlockTexturePool ivoryBrickPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockRegistry.IVORY_BRICKS);
        ivoryBrickPool.stairs(BlockRegistry.IVORY_BRICK_STAIRS);
        ivoryBrickPool.slab(BlockRegistry.IVORY_BRICK_SLAB);
        ivoryBrickPool.wall(BlockRegistry.IVORY_BRICK_WALL);
        // ivoryBrickPool.wall(ModBlocks.IVORY_BRICK_WALL);

        BlockStateModelGenerator.BlockTexturePool polishedIvoryPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockRegistry.POLISHED_IVORY);
        polishedIvoryPool.stairs(BlockRegistry.POLISHED_IVORY_STAIRS);
        polishedIvoryPool.slab(BlockRegistry.POLISHED_IVORY_SLAB);
        polishedIvoryPool.wall(BlockRegistry.POLISHED_IVORY_WALL);

        BlockStateModelGenerator.BlockTexturePool gildedIvoryBrickPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockRegistry.GILDED_IVORY_BRICKS);
        gildedIvoryBrickPool.stairs(BlockRegistry.GILDED_IVORY_BRICK_STAIRS);
        gildedIvoryBrickPool.slab(BlockRegistry.GILDED_IVORY_BRICK_SLAB);
        gildedIvoryBrickPool.wall(BlockRegistry.GILDED_IVORY_BRICK_WALL);

        BlockStateModelGenerator.BlockTexturePool gildedPolishedIvoryPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockRegistry.GILDED_POLISHED_IVORY);
        gildedPolishedIvoryPool.stairs(BlockRegistry.GILDED_POLISHED_IVORY_STAIRS);
        gildedPolishedIvoryPool.slab(BlockRegistry.GILDED_POLISHED_IVORY_SLAB);
        gildedPolishedIvoryPool.wall(BlockRegistry.GILDED_POLISHED_IVORY_WALL);


    }


    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        // Existing registrations
        generator.register(ItemRegistry.IVORY_BRICK, Models.GENERATED);
        /*
        Models.GENERATED.upload(
                Identifier.of(IL.MOD_ID, "item/spell_book_2d"),
                TextureMap.layer0(Identifier.of(IL.MOD_ID, "item/spell_book_item")),
                generator.writer
        );

         */
        // Spell book registration
        //registerSpellBookModels(generator);
    }

    /*
    private void registerSpellBookModels(ItemModelGenerator generator) {
        Identifier mainModelId = ModelIds.getItemModelId(ItemRegistry.SPELL_BOOK);
        System.out.println(mainModelId.toString());

        // 1. Create main model with overrides
        JsonObject mainModel = new JsonObject();
        mainModel.addProperty("parent", "item/handheld"); // 3D parent
        JsonArray overrides = new JsonArray();
        overrides.add(createOverride("gui", 1.0f, mainModelId.withSuffixedPath("_2d")));
        overrides.add(createOverride("ground", 1.0f, mainModelId.withSuffixedPath("_2d")));
        mainModel.add("overrides", overrides);

        // Write to models/item directory
        DataOutput.PathResolver itemResolver = output.getResolver(
                DataOutput.OutputType.RESOURCE_PACK,
                "models/item" // Correct directory
        );

        DataProvider.writeToPath(
                DataWriter.UNCACHED,
                mainModel,
                itemResolver.resolve(mainModelId, ".json")
        );

        // 2. Create 2D variant in models/item
        Models.GENERATED.upload(
                mainModelId.withSuffixedPath("_2d"),
                TextureMap.layer0(Identifier.of(IL.MOD_ID, "item/spell_book_item")),
                generator.writer
        );
    }

     */

    private JsonObject createOverride(String predicate, float value, Identifier model) {
        JsonObject override = new JsonObject();
        JsonObject predicateObj = new JsonObject();
        predicateObj.addProperty(predicate, value);
        override.add("predicate", predicateObj);
        override.addProperty("model", model.toString());
        return override;
    }

}
