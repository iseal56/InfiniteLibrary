package dev.iseal.infinitelibrary.items.block.old_bookshelves;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class OldBookshelfBlock extends Block {

    public OldBookshelfBlock() {
        super(Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "old_bookshelf")))
                .instrument(NoteBlockInstrument.BASS)
                .strength(1F)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable());

    }
}

