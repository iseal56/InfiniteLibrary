package dev.iseal.infinitelibrary.items.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class SpellBookEntity extends MobEntity {
    public SpellBookEntity(EntityType entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {

    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        DefaultAttributeContainer.Builder builder = LivingEntity.createLivingAttributes();
        builder.add(EntityAttributes.GENERIC_ARMOR, 1f);
        return builder;
    }
}
