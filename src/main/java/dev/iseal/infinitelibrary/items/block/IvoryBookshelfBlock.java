package dev.iseal.infinitelibrary.items.block;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class IvoryBookshelfBlock extends Block {
    public IvoryBookshelfBlock() {
        super(AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "ivory_bookshelf")))
                .emissiveLighting((state, world, pos) -> true)
                .luminance((state) -> 15)
                .dropsNothing()
                .mapColor(MapColor.OAK_TAN)
                .instrument(NoteBlockInstrument.BASS)
                .strength(1F)
                .hardness(1f)
                .resistance(6.0f)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable()
        );
    }

}
