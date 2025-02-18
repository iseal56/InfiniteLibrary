package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.block.chiseled_ivory.GleamingChiseledIvoryBlock;
import dev.iseal.infinitelibrary.items.block.chiseled_ivory.DullChiseledIvoryBlock;
import dev.iseal.infinitelibrary.items.block.LibraryPortalBlock;
import dev.iseal.infinitelibrary.items.block.QuartzBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldEmptyBookshelfBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BlockRegistry {

    private static BlockRegistry INSTANCE;
    public static BlockRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BlockRegistry();
        }
        return INSTANCE;
    }


    public static final Block QUARTZ_BOOKSHELF = register(new QuartzBookshelfBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "quartz_bookshelf")), true);
    public static final Block DULL_CHISELED_IVORY = register(new DullChiseledIvoryBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "dull_chiseled_ivory")), true);
    public static final Block GLEAMING_CHISELED_IVORY = register(new GleamingChiseledIvoryBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gleaming_chiseled_ivory")), true);
    public static final Block OLD_EMPTY_BOOKSHELF = register(new OldEmptyBookshelfBlock(), 
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "old_empty_bookshelf")), true);
    public static final Block OLD_BOOKSHELF = register(new OldBookshelfBlock(), 
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "old_bookshelf")), true);

    // ivory blocks
    public static final Block CHISELED_IVORY = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "chiseled_ivory")), true);
    public static final Block IVORY_BRICKS = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "ivory_bricks")), true);
    public static final Block IVORY_PILLAR = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "ivory_pillar")), true);
    public static final Block POLISHED_IVORY = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "polished_ivory")), true);

    // gilded ivory blocks
    public static final Block GILDED_CHISELED_IVORY = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gilded_chiseled_ivory")), true);
    public static final Block GILDED_IVORY_BRICKS = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gilded_ivory_bricks")), true);
    public static final Block GILDED_IVORY_PILLAR = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gilded_ivory_pillar")), true);
    public static final Block GILDED_POLISHED_IVORY = register(AbstractBlock.Settings.copy(DULL_CHISELED_IVORY),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gilded_polished_ivory")), true);

    public static final Block LIBRARY_PORTAL = register(new LibraryPortalBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "library_portal")), false);

    private static Block register(Block block, RegistryKey<Block> key, boolean registerItem) {
        if (registerItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, key.getValue());
            BlockItem blockItem = new BlockItem(block,
                    new Item.Settings()
                            .registryKey(itemKey)
            );
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, key, block);
    }

    private static Block register(AbstractBlock.Settings settings, RegistryKey<Block> key, boolean registerItem) {
        Block block = new Block(settings.registryKey(key));
        return register(block, key, registerItem);
    }

    public static Item createItem(Block block) {
        return new BlockItem(block, new Item.Settings());
    }

    public void initialize() {

    }
}
