package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.entities.LibrarianEntity;
import dev.iseal.infinitelibrary.entities.projectiles.ExperienceOrbProjectileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class EntityRegistry {

    private static EntityRegistry INSTANCE;
    public static EntityRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EntityRegistry();
        }
        return INSTANCE;
    }

    public static final EntityType<LibrarianEntity> LIBRARIAN = register(
            IL.identifier("librarian"),
            EntityType.Builder.create(LibrarianEntity::new, SpawnGroup.MONSTER)
                    .eyeHeight(1.7f)
                    .dimensions(1.2f, 5f)
                    .maxTrackingRange(8),
            LibrarianEntity.createMobAttributes());

    public static final EntityType<ExperienceOrbProjectileEntity> EXPERIENCE_ORB_PROJECTILE = registerProjectile(
            IL.identifier("experience_orb_projectile"),
            EntityType.Builder.<ExperienceOrbProjectileEntity>create(ExperienceOrbProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .maxTrackingRange(8)
    );

    @SuppressWarnings("unchecked")
    public static <T extends Entity> EntityType<T> register(Identifier id, EntityType.Builder<T> type, DefaultAttributeContainer.Builder attributes) {
        EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) Registry.register(
                Registries.ENTITY_TYPE, id,
                type.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, id))
        );
        FabricDefaultAttributeRegistry.register(entityType, attributes);
        return (EntityType<T>) entityType;
    }

    public static <T extends ProjectileEntity> EntityType<T> registerProjectile(Identifier id, EntityType.Builder<T> type) {
        return Registry.register(
                Registries.ENTITY_TYPE, id,
                type.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, id))
        );
    }

    public void initialize() {

    }

}
