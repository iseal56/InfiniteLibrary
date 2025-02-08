package dev.iseal.infinitelibrary.items.block.chiseled_ivory;

import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DullChiseledIvoryBlock extends Block {

    public DullChiseledIvoryBlock() {
        super(AbstractBlock.Settings.create()
                .mapColor(MapColor.OAK_TAN)
                .instrument(Instrument.BASS)
                .strength(1F)
                .hardness(1f)
                .resistance(6.0f)
                .sounds(BlockSoundGroup.CHISELED_BOOKSHELF)
                .burnable()
        );
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.PASS;

        if (player.isSneaking()
                && player.totalExperience >= 10
                && player.getMainHandStack().isEmpty()
                && hand == Hand.MAIN_HAND
        ) {
            player.addExperience(-10);
            world.setBlockState(pos, BlockRegistry.GLEAMING_CHISELED_IVORY.getDefaultState());
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }
}
