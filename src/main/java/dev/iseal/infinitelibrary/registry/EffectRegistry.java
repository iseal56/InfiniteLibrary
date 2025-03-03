package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.statusEffects.HubrisEffect;
import dev.iseal.infinitelibrary.statusEffects.KnowledgeEffect;
import dev.iseal.infinitelibrary.statusEffects.RecallEffect;
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

    public static final StatusEffect HUBRIS = register("hubris", new HubrisEffect());
    public static final StatusEffect KNOWLEDGE = register("knowledge", new KnowledgeEffect());
    public static final StatusEffect RECALL = register("recall", new RecallEffect());

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, IL.identifier(id), entry);
    }

    public void initialize() {
        // This method is empty.
    }

}
