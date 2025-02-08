package dev.iseal.infinitelibrary.items.materials;

import dev.iseal.infinitelibrary.registry.ItemRegistry;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class IvoryToolMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 1024;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 9;
    }

    @Override
    public float getAttackDamage() {
        return 4;
    }

    @Override
    public int getMiningLevel() {
        return MiningLevels.NETHERITE;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ItemRegistry.IVORY_BRICK);
    }
}
