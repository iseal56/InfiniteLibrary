package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.util.Identifier;

public class LootTableRegistry {
    private static LootTableRegistry instance;
    public static LootTableRegistry getInstance(){
        if(instance == null){
            instance = new LootTableRegistry();
        }
        return instance;
    }

    public static final Identifier LIBRARY_LOOT_KEY = new Identifier(IL.MOD_ID, "chests/library_loot");

    public void initialize() {

    }
}
