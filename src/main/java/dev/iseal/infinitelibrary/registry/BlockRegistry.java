package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.block.chiseled_quartz.ActivatedChiseledQuartzBlock;
import dev.iseal.infinitelibrary.items.block.chiseled_quartz.InactiveChiseledQuartzBlock;
import dev.iseal.infinitelibrary.items.block.LibraryPortalBlock;
import dev.iseal.infinitelibrary.items.block.QuartzBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldBookshelfBlock;
import dev.iseal.infinitelibrary.items.block.old_bookshelves.OldEmptyBookshelfBlock;
import net.minecraft.block.Block;
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
            RegistryKey.of(RegistryKeys.BLOCK, new Identifier(IL.MOD_ID, "quartz_bookshelf")),
            true
    );
    public static final Block INACTIVE_CHIESELED_QUARTZ_BLOCK = register(new InactiveChiseledQuartzBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, new Identifier(IL.MOD_ID, "inactive_chiseled_quartz")), true);
    public static final Block ACTIVATED_CHIESELED_QUARTZ_BLOCK = register(new ActivatedChiseledQuartzBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, new Identifier(IL.MOD_ID, "activated_chiseled_quartz")), true);
    public static final Block OLD_EMPTY_BOOKSHELF = register(new OldEmptyBookshelfBlock(), 
            RegistryKey.of(RegistryKeys.BLOCK, new Identifier(IL.MOD_ID, "old_empty_bookshelf")), true);
    public static final Block OLD_BOOKSHELF = register(new OldBookshelfBlock(), 
            RegistryKey.of(RegistryKeys.BLOCK, new Identifier(IL.MOD_ID, "old_bookshelf")), true);
    
    
    public static final Block LIBRARY_PORTAL = register(new LibraryPortalBlock(),
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "library_portal")), false);

    private static Block register(Block block, RegistryKey<Block> key, boolean registerItem) {
        if (registerItem) {
            // Items need to be registered with a different type of registry key, but the ID can be the same.
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, key.getValue());
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, key, block);
    }

    public static Item createItem(Block block) {
        return new BlockItem(block, new Item.Settings());
    }

    public void initialize() {

    }
}
