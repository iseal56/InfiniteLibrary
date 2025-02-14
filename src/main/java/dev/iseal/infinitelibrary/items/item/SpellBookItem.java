package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.items.entity.SpellBookEntity;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;

public class SpellBookItem extends Item {

    public SpellBookItem() {
        // set material to limit durability
        super(
                new Item.Settings()
                .rarity(Rarity.EPIC)
                .maxCount(1)
                .maxDamage(100)
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient)
            return ActionResult.SUCCESS;

        // create the book entity
        context.getWorld().spawnEntity(new SpellBookEntity(context.getWorld()));

        // and remove the item
        context.getStack().decrement(1);

        return ActionResult.PASS;
    }
}
