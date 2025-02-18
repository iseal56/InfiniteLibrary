package dev.iseal.infinitelibrary.listeners;

import dev.iseal.infinitelibrary.loot_functions.ObfuscateBookLootFunction;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.*;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class AddCodesToLootTables {

    private static final Identifier CARTOGRAPHER_CHEST = Identifier.of(Identifier.DEFAULT_NAMESPACE, "chests/village/village_cartographer");

    public void initialize() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
            // We also check that the loot table ID is equal to the ID we want.
            if (source.isBuiltin() && CARTOGRAPHER_CHEST.equals(key.getValue())) {
                LootPool poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(Items.WRITTEN_BOOK).build())
                        .apply(ObfuscateBookLootFunction.getInstance())
                        .build();

                tableBuilder.pool(poolBuilder);
                // Our code will go here
            }
        });
    }

}
