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
        generator.register(ItemRegistry.IVORY_BRICK, Models.GENERATED);
        generator.register(ItemRegistry.SCRAPS_OF_WISDOM, Models.GENERATED);
        generator.register(ItemRegistry.TOME_OF_RETURN, Models.GENERATED);
    }

    private JsonObject createOverride(String predicate, float value, Identifier model) {
        JsonObject override = new JsonObject();
        JsonObject predicateObj = new JsonObject();
        predicateObj.addProperty(predicate, value);
        override.add("predicate", predicateObj);
        override.addProperty("model", model.toString());
        return override;
    }

}
