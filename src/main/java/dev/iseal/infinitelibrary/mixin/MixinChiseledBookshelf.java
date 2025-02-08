package dev.iseal.infinitelibrary.mixin;

import dev.iseal.infinitelibrary.utils.PortalHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.dimension.NetherPortal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChiseledBookshelfBlock.class)
public class MixinChiseledBookshelf {

    @Inject(
            method = "tryAddBook",
            at = @At("TAIL")
    )
    private static void onBookAdded(
            World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, ItemStack stack, int slot, CallbackInfo ci
    ) {
        if (!world.isClient()) {
            new PortalHelper(world).checkPortalStructure(pos, true);
        }
    }

    @Inject(
            method = "tryRemoveBook",
            at = @At("TAIL")
    )
    private static void onBookTaken(
            World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, int slot, CallbackInfo ci
    ) {
        if (!world.isClient()) {
            new PortalHelper(world).checkPortalStructure(pos, true);
        }
    }

}
