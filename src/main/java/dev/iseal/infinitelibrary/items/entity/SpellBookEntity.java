package dev.iseal.infinitelibrary.items.entity;

import dev.iseal.infinitelibrary.registry.EntityRegistry;
import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class SpellBookEntity extends MobEntity {
    private static final TrackedData<Integer> DURABILITY = DataTracker.registerData(SpellBookEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public SpellBookEntity(World world) {
        super(EntityRegistry.SPELL_BOOK, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(DURABILITY, ItemRegistry.SPELL_BOOK.getMaxDamage());
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        DefaultAttributeContainer.Builder builder = LivingEntity.createLivingAttributes();
        builder.add(EntityAttributes.GENERIC_ARMOR, 1f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16f);
        return builder;
    }

    public int getDurability() {
        return dataTracker.get(DURABILITY);
    }

    public void setDurability(int durability) {
        dataTracker.set(DURABILITY, durability);
    }
}
