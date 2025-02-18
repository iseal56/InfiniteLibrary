package dev.iseal.infinitelibrary.registry;

import com.mojang.serialization.MapCodec;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.loot_functions.ObfuscateBookLootFunction;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LootFunctionTypeRegistry {

    public static final LootFunctionType<ObfuscateBookLootFunction> OBFUSCATE_BOOK = register("obfuscate_book", ObfuscateBookLootFunction.CODEC);

    private static <T extends LootFunction> LootFunctionType<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.LOOT_FUNCTION_TYPE, Identifier.of(IL.MOD_ID, id), new LootFunctionType<>(codec));
    }

    public void initialize() {

    }

}
