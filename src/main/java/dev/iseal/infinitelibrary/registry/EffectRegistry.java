package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.effects.HubrisEffect;
import dev.iseal.infinitelibrary.effects.KnowledgeEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.SplashPotionItem;
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

    public static final StatusEffect KNOWLEDGE = register("knowledge", new KnowledgeEffect());
    public static final StatusEffect HUBRIS = register("hubris", new HubrisEffect());

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(IL.MOD_ID, id), entry);
    }

    public void initialize() {
        // This method is empty.
    }

}
