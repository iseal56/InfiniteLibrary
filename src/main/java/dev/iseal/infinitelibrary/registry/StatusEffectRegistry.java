package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.statusEffects.HubrisEffect;
import dev.iseal.infinitelibrary.statusEffects.KnowledgeEffect;
import dev.iseal.infinitelibrary.statusEffects.RecallEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class StatusEffectRegistry {

    private static StatusEffectRegistry INSTANCE;
    public static StatusEffectRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatusEffectRegistry();
        }
        return INSTANCE;
    }

    public static final RegistryEntry<StatusEffect> HUBRIS = register("hubris", new HubrisEffect());
    public static final RegistryEntry<StatusEffect> KNOWLEDGE = register("knowledge", new KnowledgeEffect());
    public static final RegistryEntry<StatusEffect> RECALL = register("recall", new RecallEffect(0x00FF00));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect entry) {
        return Registry.registerReference(Registries.STATUS_EFFECT, IL.identifier(id), entry);
    }

    public void initialize() {
        // This method is empty.
    }

}
