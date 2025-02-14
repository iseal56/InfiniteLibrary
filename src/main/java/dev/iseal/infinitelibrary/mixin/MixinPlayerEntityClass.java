package dev.iseal.infinitelibrary.mixin;

import dev.iseal.infinitelibrary.registry.EffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntityClass {

    @Unique
    private final float KNOWLEDGE_EXPERIENCE_MULTIPLIER = 1.5f;
    @Unique
    private final float HUBRIS_EXPERIENCE_MULTIPLIER = 0.5f;

    @ModifyVariable(
            method = "addExperience",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private int modifyAddExperience(int experience) {
        // Cast to the superclass - why is this necessary? sifhsdlguhsdasd
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        float experienceFloat = experience;
        if (livingEntity.hasStatusEffect(EffectRegistry.KNOWLEDGE))
            experienceFloat*=KNOWLEDGE_EXPERIENCE_MULTIPLIER;
        if (livingEntity.hasStatusEffect(EffectRegistry.HUBRIS))
            experienceFloat*=HUBRIS_EXPERIENCE_MULTIPLIER;

        return (int) experienceFloat;
    }


}
