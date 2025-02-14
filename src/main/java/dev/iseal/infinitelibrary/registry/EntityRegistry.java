package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.entity.SpellBookEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EntityRegistry {

    private static EntityRegistry INSTANCE;
    public static EntityRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EntityRegistry();
        }
        return INSTANCE;
    }

    public static final EntityType<SpellBookEntity> SPELL_BOOK = (EntityType<SpellBookEntity>) register(
            new Identifier(IL.MOD_ID, "spell_book"),
            EntityType.Builder.create(SpellBookEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.3f, 0.3f)
                    .build("spell_book"),
            SpellBookEntity.createMobAttributes()
    );

    public static EntityType<? extends LivingEntity> register(Identifier id, EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder attributes) {
        EntityType<? extends LivingEntity> entityType = Registry.register(Registries.ENTITY_TYPE, id, type);
        FabricDefaultAttributeRegistry.register(entityType, attributes);
        return entityType;
    }

    public void initialize() {
        // This method is empty.
    }

}
