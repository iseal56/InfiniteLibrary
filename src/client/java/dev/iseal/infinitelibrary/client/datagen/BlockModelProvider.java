package dev.iseal.infinitelibrary.client.datagen;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.client.datagen.datagenUtils.ModelUtils;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class BlockModelProvider extends FabricModelProvider {

    public BlockModelProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(BlockRegistry.QUARTZ_BOOKSHELF);
        blockStateModelGenerator.registerSingleton(BlockRegistry.ACTIVATED_CHIESELED_QUARTZ_BLOCK, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        blockStateModelGenerator.registerSingleton(BlockRegistry.INACTIVE_CHIESELED_QUARTZ_BLOCK, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        blockStateModelGenerator.registerSingleton(BlockRegistry.OLD_BOOKSHELF, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        blockStateModelGenerator.registerSingleton(BlockRegistry.OLD_EMPTY_BOOKSHELF, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
        // register cool thing
        /*
        Block[] blocks = new Block[]{BlockRegistry.OLD_BOOKSHELF, BlockRegistry.OLD_EMPTY_BOOKSHELF};
        for (Block block : blocks) {
            registerOrientableBlock(
                    blockStateModelGenerator,
                    block,
                    getRotatableBlockTextureMap(block, "block/chiseled_bookshelf"),
                    ModelUtils.blockVanilla("orientable", TextureKey.TOP, TextureKey.SIDE, TextureKey.FRONT)
            );
        }
         */
    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.PALE_SWORD, ModelUtils.itemVanilla("handheld", TextureKey.LAYER0));
    }

    @Override
    public String getName() {
        return "Infinite Library Block Model Provider";
    }

    public static TextureMap getRotatableBlockTextureMap(Block block, String sideTexture) {
        return new TextureMap()
                .put(TextureKey.TOP, new Identifier(IL.MOD_ID, sideTexture+"_top"))
                .put(TextureKey.SIDE, new Identifier(IL.MOD_ID, sideTexture))
                .put(TextureKey.FRONT, ModelIds.getBlockModelId(block));
    }

    private static BlockStateSupplier createRotSupplier(Block rotatableBlock, Identifier rotatableBlockId) {
        VariantSetting<Boolean> uvlock = VariantSettings.UVLOCK;
        VariantSetting<VariantSettings.Rotation> yRot = VariantSettings.Y;
        return VariantsBlockStateSupplier.create(rotatableBlock).coordinate(BlockStateVariantMap.create(HorizontalFacingBlock.FACING)
                .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, rotatableBlockId).put(uvlock, true))
                .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, rotatableBlockId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R90))
                .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, rotatableBlockId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R180))
                .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, rotatableBlockId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R270))
        );
    }

    public static void registerOrientableBlock(BlockStateModelGenerator generator, Block vertSlabBlock, TextureMap textures, Model model) {
        Identifier slabModel = model.upload(vertSlabBlock, textures, generator.modelCollector);
        generator.blockStateCollector.accept(createRotSupplier(vertSlabBlock, slabModel));
        generator.registerParentedItemModel(vertSlabBlock, slabModel);
    }

}
