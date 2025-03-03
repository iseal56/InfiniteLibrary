package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.registry.BlockRegistry;
import dev.iseal.infinitelibrary.registry.EffectRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class TomeOfReturnItem extends Item {
    public TomeOfReturnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // check if the block is an altar
        if (!context.getWorld().getBlockState(context.getBlockPos()).isOf(BlockRegistry.IVORY_ALTAR))
            return ActionResult.PASS;

        // check so intellij stops screaming at me
        if (context.getPlayer() == null)
            return ActionResult.PASS;

        if (context.getWorld().getRegistryKey() != IL.WORLD_KEY)
            return ActionResult.PASS;

        if (context.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        // remove the item
        context.getPlayer().setStackInHand(context.getHand(), Items.AIR.getDefaultStack());

        // add the status effect that will teleport the player later - 5 seconds
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) context.getPlayer();
        serverPlayerEntity.addStatusEffect(new StatusEffectInstance(RegistryEntry.of(EffectRegistry.RECALL), 5*20));
        return ActionResult.SUCCESS;
    }
}
