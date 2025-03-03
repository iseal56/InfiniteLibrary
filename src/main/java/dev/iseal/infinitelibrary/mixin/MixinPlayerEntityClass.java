package dev.iseal.infinitelibrary.mixin;

import dev.iseal.infinitelibrary.registry.StatusEffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntityClass {

    // level 1 = 1.25, level 2 = 1.5, level 3 = 1.75, etc.
    @Unique
    private final float KNOWLEDGE_EXPERIENCE_MULTIPLIER_PER_LEVEL = .25f;

    // level 1 = 0.5, level 2 = 0.25, level 3 = 0.166, etc.
    @Unique
    private final float HUBRIS_EXPERIENCE_MULTIPLIER_PER_LEVEL = 0.5f;

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
        if (livingEntity.hasStatusEffect(StatusEffectRegistry.KNOWLEDGE))
            experienceFloat *= KNOWLEDGE_EXPERIENCE_MULTIPLIER_PER_LEVEL
                    * (livingEntity.getStatusEffect(StatusEffectRegistry.KNOWLEDGE).getAmplifier()+1)
                    + 1f;
        if (livingEntity.hasStatusEffect(StatusEffectRegistry.HUBRIS))
            experienceFloat *= HUBRIS_EXPERIENCE_MULTIPLIER_PER_LEVEL
                    / (livingEntity.getStatusEffect(StatusEffectRegistry.HUBRIS).getAmplifier()+1);

        return (int) experienceFloat;
    }


}
