package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.registry.EnchantmentEffectRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SwordEnchantListener {

    public static class ReleaseCharges implements UseItemCallback {

        @Override
        public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand) {
            ItemStack stack = playerEntity.getMainHandStack();

            if (!playerEntity.getItemCooldownManager().isCoolingDown(stack) && EnchantmentHelper.hasAnyEnchantmentsWith(stack, EnchantmentEffectRegistry.CHARGES_COMPONENT)) {
                System.out.println("HELLO ENCHANTMENT");
                return ActionResult.SUCCESS;
            }
            System.out.println("HELLO ENCHANTMENT 2");

            return ActionResult.PASS;
        }
    }

}
