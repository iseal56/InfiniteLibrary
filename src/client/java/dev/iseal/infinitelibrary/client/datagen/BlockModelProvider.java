package dev.iseal.infinitelibrary.client.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.client.datagen.datagenUtils.ModelUtils;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.client.data.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.io.IOException;
import java.nio.file.Path;

public class BlockModelProvider extends FabricModelProvider {

    private final FabricDataOutput output;

    public BlockModelProvider(FabricDataOutput output) {
        super(output);
        this.output = output;
    }

    private final Block[] simpleCubes = new Block[]{
            BlockRegistry.QUARTZ_BOOKSHELF,
            BlockRegistry.GLEAMING_CHISELED_IVORY,
            BlockRegistry.DULL_CHISELED_IVORY,

            // ivory blocks
            BlockRegistry.CHISELED_IVORY,
            BlockRegistry.IVORY_BRICKS,
            BlockRegistry.POLISHED_IVORY,

            // gilded ivory blocks
            BlockRegistry.GILDED_CHISELED_IVORY,
            BlockRegistry.GILDED_IVORY_BRICKS,
            BlockRegistry.GILDED_POLISHED_IVORY
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
