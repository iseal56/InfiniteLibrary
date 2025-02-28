package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.registry.ComponentTypeRegistry;
import dev.iseal.infinitelibrary.registry.EnchantmentEffectRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SwordEnchantListener {

    public static class ReleaseCharges implements UseItemCallback {

        @Override
        public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand) {
            ItemStack stack = playerEntity.getMainHandStack();

            if (!playerEntity.getItemCooldownManager().isCoolingDown(stack) && EnchantmentHelper.hasAnyEnchantmentsWith(stack, ComponentTypeRegistry.CHARGES_AMOUNT)) {
                System.out.println("HELLO ENCHANTMENT");
                return ActionResult.SUCCESS;
            }
            System.out.println("HELLO ENCHANTMENT 2");

            return ActionResult.PASS;
        }
    }

}
