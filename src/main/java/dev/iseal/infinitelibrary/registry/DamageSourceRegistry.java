package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DamageSourceRegistry {

    private static DamageSourceRegistry INSTANCE;
    public static DamageSourceRegistry getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DamageSourceRegistry();
        }
        return INSTANCE;
    }

    public static final RegistryKey<DamageType> ABSORB_KNOWLEDGE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(IL.MOD_ID, "absorb_knowledge"));
    public static DamageSource ABSORB_KNOWLEDGE = null;

    public void initialize() {

    }

    public void initializeServer() {
        ABSORB_KNOWLEDGE = new DamageSource(IL.server.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getEntry(ABSORB_KNOWLEDGE_KEY.getValue()).get());
    }

}
