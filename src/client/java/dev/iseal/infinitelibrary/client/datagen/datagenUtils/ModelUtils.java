package dev.iseal.infinitelibrary.client.datagen.datagenUtils;

import dev.iseal.infinitelibrary.IL;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModelUtils {
    //helper method for creating Models
    public static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(IL.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    //helper method for creating Models with vanilla tag
    public static Model blockVanilla(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Identifier.DEFAULT_NAMESPACE, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    //helper method for creating Models with variants
    public static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(IL.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    public static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(IL.MOD_ID, "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    public static Model itemVanilla(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Identifier.DEFAULT_NAMESPACE, "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    public static Model item(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(IL.MOD_ID, "item/" + parent)), Optional.of(variant), requiredTextureKeys);
    }
}
