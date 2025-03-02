package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.utils.Utils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Optional;

public class TomeOfReturnItem extends Item {
    public TomeOfReturnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Random random = context.getWorld().getRandom();

        ArrayList<Vec3d> list = Utils.getCylinderLocations(context.getPlayer().getPos(), 2,
                15, 0.25,
                Optional.of(random));
        list.forEach(pos ->
                context.getWorld().addParticle(ParticleTypes.ENCHANT, pos.getX(), pos.getY(), pos.getZ(), random.nextFloat(), random.nextFloat(), random.nextFloat())
        );

        // and remove the item
        context.getPlayer().setStackInHand(context.getHand(), Items.AIR.getDefaultStack());

        return ActionResult.SUCCESS;
    }
}
