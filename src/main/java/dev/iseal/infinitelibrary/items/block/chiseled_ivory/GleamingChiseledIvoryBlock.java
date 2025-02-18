package dev.iseal.infinitelibrary.items.block.chiseled_ivory;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GleamingChiseledIvoryBlock extends Block {

    public GleamingChiseledIvoryBlock() {
        super(AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(IL.MOD_ID, "gleaming_chiseled_ivory")))
                .luminance((state) -> 7)
                .mapColor(MapColor.WHITE)
                .instrument(NoteBlockInstrument.XYLOPHONE)
                .requiresTool()
                .strength(20F)
                .hardness(2f)
                .resistance(6.0f)
                .sounds(BlockSoundGroup.BONE)
        );
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient)
            return ActionResult.SUCCESS;

        if (       player.isSneaking()
                && player.getMainHandStack().isEmpty()
                && player.getOffHandStack().isEmpty()
        ) {
            player.addExperience(10);
            world.setBlockState(pos, BlockRegistry.DULL_CHISELED_IVORY.getDefaultState());
            return ActionResult.SUCCESS_SERVER;
        } else {
            return ActionResult.CONSUME;
        }
    }
}
