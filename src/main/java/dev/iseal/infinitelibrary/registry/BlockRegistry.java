package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.block.LibraryPortalBlock;
import dev.iseal.infinitelibrary.items.block.IvoryBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.chiseled_ivory.DullChiseledIvoryBlock;
import dev.iseal.infinitelibrary.items.block.chiseled_ivory.GleamingChiseledIvoryBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldEmptyBookshelfBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class BlockRegistry {

    public static final Block IVORY_BOOKSHELF = register(
            new IvoryBookshelfBlock(),
            blockKey("ivory_bookshelf"),
            true
    );
    public static final Block DULL_CHISELED_IVORY = register(
            new DullChiseledIvoryBlock(),
            blockKey("dull_chiseled_ivory"),
            true
    );
    public static final Block GLEAMING_CHISELED_IVORY = register(
            new GleamingChiseledIvoryBlock(),
            blockKey("gleaming_chiseled_ivory"),
            true
    );
    public static final Block OLD_EMPTY_BOOKSHELF = register(
            new OldEmptyBookshelfBlock(),
            blockKey("old_empty_bookshelf"),
            true
    );
    public static final Block OLD_BOOKSHELF = register(new OldBookshelfBlock(), blockKey("old_bookshelf"), true);
    // ivory blocks
    public static final Block CHISELED_IVORY = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("chiseled_ivory"),
            true
    );
    public static final Block IVORY_BRICKS = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("ivory_bricks"),
            true
    );
    public static final Block IVORY_BRICK_STAIRS = register(
            (settings) -> new StairsBlock(IVORY_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copy(IVORY_BRICKS),
            blockKey("ivory_brick_stairs"),
            true
    );
    public static final Block IVORY_BRICK_SLAB = register(
            SlabBlock::new,
            AbstractBlock.Settings.copy(IVORY_BRICKS),
            blockKey("ivory_brick_slab"),
            true
    );
    public static final Block IVORY_BRICK_WALL = register(
            WallBlock::new,
            AbstractBlock.Settings.copy(IVORY_BRICKS),
            blockKey("ivory_brick_wall"),
            true
    );
    public static final Block IVORY_PILLAR = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("ivory_pillar"),
            true
    );
    public static final Block POLISHED_IVORY = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("polished_ivory"),
            true
    );
    public static final Block POLISHED_IVORY_STAIRS = register(
            settings -> new StairsBlock(POLISHED_IVORY.getDefaultState(), settings),
            AbstractBlock.Settings.copy(POLISHED_IVORY),
            blockKey("polished_ivory_stairs"),
            true
    );
    public static final Block POLISHED_IVORY_SLAB = register(
            SlabBlock::new,
            AbstractBlock.Settings.copy(POLISHED_IVORY),
            blockKey("polished_ivory_slab"),
            true
    );
    public static final Block POLISHED_IVORY_WALL = register(
            WallBlock::new,
            AbstractBlock.Settings.copy(POLISHED_IVORY),
            blockKey("polished_ivory_wall"),
            true
    );
    // gilded ivory blocks
    public static final Block GILDED_CHISELED_IVORY = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("gilded_chiseled_ivory"),
            true
    );
    public static final Block GILDED_IVORY_BRICKS = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("gilded_ivory_bricks"),
            true
    );
    public static final Block GILDED_IVORY_BRICK_STAIRS = register(
            settings -> new StairsBlock(GILDED_IVORY_BRICKS.getDefaultState(), settings),
            AbstractBlock.Settings.copy(GILDED_IVORY_BRICKS),
            blockKey("gilded_ivory_brick_stairs"),
            true
    );
    public static final Block GILDED_IVORY_BRICK_SLAB = register(
            SlabBlock::new,
            AbstractBlock.Settings.copy(GILDED_IVORY_BRICKS),
            blockKey("gilded_ivory_brick_slab"),
            true
    );
    public static final Block GILDED_IVORY_BRICK_WALL = register(
            WallBlock::new,
            AbstractBlock.Settings.copy(GILDED_IVORY_BRICKS),
            blockKey("gilded_ivory_brick_wall"),
            true
    );
    public static final Block GILDED_IVORY_PILLAR = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("gilded_ivory_pillar"),
            true
    );
    public static final Block GILDED_POLISHED_IVORY = register(
            AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            blockKey("gilded_polished_ivory"),
            true
    );
    public static final Block GILDED_POLISHED_IVORY_STAIRS = register(
            settings -> new StairsBlock(GILDED_POLISHED_IVORY.getDefaultState(), settings),
            AbstractBlock.Settings.copy(GILDED_POLISHED_IVORY),
            blockKey("gilded_polished_ivory_stairs"),
            true
    );
    public static final Block GILDED_POLISHED_IVORY_SLAB = register(
            SlabBlock::new,
            AbstractBlock.Settings.copy(GILDED_POLISHED_IVORY),
            blockKey("gilded_polished_ivory_slab"),
            true
    );
    public static final Block GILDED_POLISHED_IVORY_WALL = register(
            WallBlock::new,
            AbstractBlock.Settings.copy(GILDED_POLISHED_IVORY),
            blockKey("gilded_polished_ivory_wall"),
            true
    );
    public static final Block LIBRARY_PORTAL = register(new LibraryPortalBlock(), blockKey("library_portal"), false);
    private static BlockRegistry INSTANCE;

    public static BlockRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BlockRegistry();
        }
        return INSTANCE;
    }

    private static Block register(Block block, RegistryKey<Block> key, boolean registerItem) {
        if (registerItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, key.getValue());
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block register(Function<AbstractBlock.Settings, Block> block, AbstractBlock.Settings baseSettings, RegistryKey<Block> key, boolean registerItem) {
        return register(block.apply(baseSettings.registryKey(key)), key, registerItem);
    }

    private static Block register(AbstractBlock.Settings settings, RegistryKey<Block> key, boolean registerItem) {
        Block block = new Block(settings.registryKey(key));
        return register(block, key, registerItem);
    }

    public static RegistryKey<Block> blockKey(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, IL.identifier(name));
    }


    public void initialize() {

    }
}
