package dev.iseal.infinitelibrary.registry.worldgen;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.util.Identifier;

public class DimensionRegistry {

    private static DimensionRegistry INSTANCE;
    public static DimensionRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DimensionRegistry();
        }
        return INSTANCE;
    }

    public static final Identifier LIBRARY_ID = Identifier.of(IL.MOD_ID, "library");

    public void initialize() {

    }

}
