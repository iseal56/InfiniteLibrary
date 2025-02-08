package dev.iseal.infinitelibrary.items.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;

public class QuartzBookshelfBlock extends Block {
    public QuartzBookshelfBlock() {
        super(AbstractBlock.Settings.create()
                .emissiveLighting((state, world, pos) -> true)
                .luminance((state) -> 15)
                .dropsNothing()
                .mapColor(MapColor.OAK_TAN)
                .instrument(Instrument.BASS)
                .strength(1F)
                .hardness(1f)
                .resistance(6.0f)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable()
        );
    }

}
