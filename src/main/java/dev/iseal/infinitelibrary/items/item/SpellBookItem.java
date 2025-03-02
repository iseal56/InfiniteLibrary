package dev.iseal.infinitelibrary.items.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class SpellBookItem extends Item {

    public SpellBookItem(Item.Settings settings) {
        // set material to limit durability
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
