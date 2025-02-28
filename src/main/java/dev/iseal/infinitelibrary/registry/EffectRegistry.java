package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.statusEffects.HubrisEffect;
import dev.iseal.infinitelibrary.statusEffects.KnowledgeEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EffectRegistry {

    private static EffectRegistry INSTANCE;
    public static EffectRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EffectRegistry();
        }
        return INSTANCE;
    }

    public static final StatusEffect HUBRIS;
    public static final StatusEffect KNOWLEDGE;

    static {
        HUBRIS = Registry.register(Registries.STATUS_EFFECT, Identifier.of(IL.MOD_ID, "hubris"), new HubrisEffect());
        KNOWLEDGE = Registry.register(Registries.STATUS_EFFECT, Identifier.of(IL.MOD_ID, "knowledge"), new KnowledgeEffect());
    }

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(IL.MOD_ID, id), entry);
    }

    public void initialize() {
        // This method is empty.
    }

}
