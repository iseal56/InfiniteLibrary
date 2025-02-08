package dev.iseal.infinitelibrary.items.item;

import dev.iseal.infinitelibrary.registry.DamageSourceRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class PaleSwordItem extends SwordItem {

    private final HashMap<UUID, Integer> playerHasPaleSword = new HashMap<>();
    private final int reloadTime = 20;
    private final int reloadTimeQuarters = reloadTime/4;
    private int time = 0;

    public PaleSwordItem() {
        super(
                ItemRegistry.IVORY_TOOL_MATERIAL,
                10,
                -2.4F,
                new Item.Settings()
        );
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        //TODO: optimize this to run on client and send the ticks to the server.
        if (world.isClient)
            return;
        if (!(entity instanceof PlayerEntity player))
            return;

        if (!selected) {
            playerHasPaleSword.remove(player.getUuid());
            time = 0;
            return;
        }
        int timeWithSword = playerHasPaleSword.getOrDefault(player.getUuid(), 0)+1;
        time++;
        playerHasPaleSword.put(player.getUuid(), timeWithSword);

        if (time != reloadTimeQuarters) {
            return;
        }
        time = 0;



        if (player.totalExperience < timeWithSword / 5) {
            player.damage(DamageSourceRegistry.ABSORB_KNOWLEDGE, (float) timeWithSword / 20 + 1);
        } else {
            player.addExperience(-(timeWithSword/6 + 1));
            System.out.println("Removing experience from player: " + player.getName() + " (exp removed: " + (timeWithSword/6 + 1) + ")");
        }
    }
}
