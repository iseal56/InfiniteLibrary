package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class SpellBookItem extends Item {

    public SpellBookItem() {
        // set material to limit durability
        super(
                new Item.Settings()
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(IL.MOD_ID, "spell_book")))
                    .rarity(Rarity.EPIC)
                    .maxCount(1)
                    .maxDamage(100)
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient)
            return ActionResult.SUCCESS;

        // and remove the item
        context.getStack().decrement(1);

        return ActionResult.PASS;
    }
}
