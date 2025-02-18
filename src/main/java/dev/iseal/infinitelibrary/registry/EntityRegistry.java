package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
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


    public static EntityType<?> register(Identifier id, EntityType<?> type, DefaultAttributeContainer.Builder attributes) {
        EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) Registry.register(Registries.ENTITY_TYPE, id, type);
        FabricDefaultAttributeRegistry.register(entityType, attributes);
        return entityType;

    }

    public void initialize() {
        // This method is empty.
    }

}
