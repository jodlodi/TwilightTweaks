package io.github.jodlodi.twilighttweaks.jei;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.util.ResourceLocation;
import twilightforest.block.TFBlocks;
import twilightforest.client.UncraftingGui;
import twilightforest.inventory.UncraftingContainer;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class UncraftingPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return TwilightTweaks.twilightResource("uncrafting_jei");
    }//TODO:FIX REG "twilighttweaks:uncrafting"

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new UncraftingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), UncraftingCategory.id);
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(UncraftingContainer.class, VanillaRecipeCategoryUid.CRAFTING, 11, 9, 20, 36);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<ICraftingRecipe> recipes = null;
        if (Minecraft.getInstance().level != null) {
            recipes = Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(ICraftingRecipe.class::isInstance).map(ICraftingRecipe.class::cast).collect(Collectors.toList());
            registration.addRecipes(recipes, UncraftingCategory.id);
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(UncraftingGui.class, 34, 33, 27, 20, UncraftingCategory.id);
        registration.addRecipeClickArea(UncraftingGui.class, 118, 33, 27, 20, VanillaRecipeCategoryUid.CRAFTING);
    }
}
