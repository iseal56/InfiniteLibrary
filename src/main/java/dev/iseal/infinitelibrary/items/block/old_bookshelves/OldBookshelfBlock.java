package dev.iseal.infinitelibrary.items.block.old_bookshelves;

import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

public class OldBookshelfBlock extends Block {

    public OldBookshelfBlock() {
        super(AbstractBlock.Settings.create()
                .instrument(Instrument.BASS)
                .strength(1F)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable());

    }
}

