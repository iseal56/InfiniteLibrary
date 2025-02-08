package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.loot_functions.ObfuscateBookLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

public class LootFunctionTypeRegistry {

    public static final LootFunctionType OBFUSCATE_BOOK = register("obfuscate_book", new ObfuscateBookLootFunction.Serializer());


    private static LootFunctionType register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {
        return Registry.register(Registries.LOOT_FUNCTION_TYPE, new Identifier(IL.MOD_ID, id), new LootFunctionType(jsonSerializer));
    }

    public void initialize() {

    }

}
