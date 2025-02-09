package dev.iseal.infinitelibrary.items.block.old_bookshelves;

import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;

public class OldEmptyBookshelfBlock extends Block {

    public OldEmptyBookshelfBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.OAK_TAN)
                .instrument(Instrument.BASS)
                .strength(1F)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable());
    }
}
