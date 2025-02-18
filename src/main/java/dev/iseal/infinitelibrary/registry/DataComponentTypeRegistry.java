package dev.iseal.infinitelibrary.registry;

import dev.iseal.infinitelibrary.IL;
import dev.iseal.infinitelibrary.items.item.PaleSwordItem;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class DataComponentTypeRegistry {

    private static DataComponentTypeRegistry INSTANCE;
    public static DataComponentTypeRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataComponentTypeRegistry();
        }
        return INSTANCE;
    }

    public static ComponentType<Integer> INCREASE_DAMAGE = register(
            "increased_damage", builder -> builder.codec(Codecs.rangedInt(0, PaleSwordItem.DAMAGE_CAP)).packetCodec(PacketCodecs.VAR_INT)
    );
    public static ComponentType<Integer> TIME_HELD = register(
            "time_held", builder -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT)
    );

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(IL.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public void initialize() {
        // This method is empty.
    }

}
