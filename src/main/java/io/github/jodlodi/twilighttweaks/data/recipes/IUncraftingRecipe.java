package io.github.jodlodi.twilighttweaks.data.recipes;

import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface IUncraftingRecipe extends ICraftingRecipe {
    default IRecipeType<?> getType() {
        return ModRecipeTypes.UNCRAFTING_RECIPE;
    }
}