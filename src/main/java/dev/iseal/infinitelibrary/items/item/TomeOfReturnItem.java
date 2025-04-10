package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.StatusEffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TomeOfReturnItem extends Item {
    public TomeOfReturnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // check if the block is an altar
        Vec3d hitPos = context.getHitPos();
        if (!context.getWorld().getBlockState(new BlockPos((int) hitPos.x, (int) hitPos.y, (int) hitPos.z)).isOf(BlockRegistry.IVORY_ALTAR))
            return ActionResult.PASS;

        // check so intellij stops screaming at me
        if (context.getPlayer() == null)
            return ActionResult.PASS;

        if (context.getWorld().getRegistryKey() != IL.WORLD_KEY)
            return ActionResult.PASS;

        if (context.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        if (!(context.getPlayer() instanceof ServerPlayerEntity serverPlayerEntity))
            return ActionResult.PASS;


        return ActionResult.SUCCESS;
    }
}
