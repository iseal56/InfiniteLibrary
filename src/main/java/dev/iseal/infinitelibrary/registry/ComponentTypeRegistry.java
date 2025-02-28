package dev.iseal.infinitelibrary.registry;

import com.mojang.serialization.Codec;
import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.enchantment.effect.ChargesEffect;
import dev.iseal.infinitelibrary.components.TimeHeldComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ComponentTypeRegistry {

    public static ComponentType<TimeHeldComponent> TIME_HELD = register(TimeHeldComponent.CODEC, "time_held");

    private static ComponentTypeRegistry INSTANCE;

    public static <T> ComponentType<T> register(Codec<T> codec, String name) {
        // Return the registered item!
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                IL.identifier(name),
                ComponentType.<T>builder().codec(codec).build()
        );
    }

    public static ComponentTypeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ComponentTypeRegistry();
        }
        return INSTANCE;
    }

    public void initialize() {
        // This method is empty.
    }
}
