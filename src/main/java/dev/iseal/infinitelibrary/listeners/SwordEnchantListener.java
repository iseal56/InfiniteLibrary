package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.components.ChargesAmountComponent;
import dev.iseal.infinitelibrary.enchantment.effect.AddChargesEnchantmentEffect;
import dev.iseal.infinitelibrary.registry.ComponentTypeRegistry;
import dev.iseal.infinitelibrary.utils.Utils;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.Component;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.TargetedEnchantmentEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SwordEnchantListener {

    public static class ReleaseCharges implements UseItemCallback {

        @Override
        public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand) {

            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

            ItemStack stack = playerEntity.getMainHandStack();
            System.out.println("Interacting with item " + stack.getItem().getTranslationKey());

            if (playerEntity.getItemCooldownManager().isCoolingDown(stack)) {
                return ActionResult.PASS;
            }
            boolean hasEffect;

            hasEffect = Utils.hasEnchantmentEffect(stack, AddChargesEnchantmentEffect.class, world.getRegistryManager());

            if (!hasEffect) {
                return ActionResult.PASS;
            }

            if (stack.get(ComponentTypeRegistry.CHARGES_AMOUNT) == null) {
                stack.set(ComponentTypeRegistry.CHARGES_AMOUNT, new ChargesAmountComponent(0));
            }

            float charges = stack.get(ComponentTypeRegistry.CHARGES_AMOUNT).amount();
            System.out.println("Releasing " + charges + " charges");
            stack.set(ComponentTypeRegistry.CHARGES_AMOUNT, new ChargesAmountComponent(0));

            return ActionResult.SUCCESS;
        }
    }

}
