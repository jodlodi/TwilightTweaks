package io.github.jodlodi.twilighttweaks.data.recipes;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public interface IShapedUncraftingRecipe<T extends IInventory> extends IRecipe<T> {
    ResourceLocation RESOURCE_LOCATION = TwilightTweaks.twilightResource("uncrafting");
}