package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.registry.worldgen.DimensionRegistry;
import dev.iseal.infinitelibrary.utils.PortalHelper;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.util.ActionResult;

public class BlockBreakListener {

    public void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            if (world.getRegistryKey().getValue().equals(DimensionRegistry.LIBRARY_ID)) {
                return false;
            }
            if (world.getBlockState(pos).getBlock().equals(Blocks.CHISELED_BOOKSHELF)) {
                PortalHelper helper = new PortalHelper(world);
                boolean isPortal = helper.checkPortalStructure(pos, false);
                if (isPortal)
                    helper.removePortal(pos, player);
            }
            return true;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getRegistryKey().getValue().equals(DimensionRegistry.LIBRARY_ID) && !player.getMainHandStack().isEmpty()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }

}
