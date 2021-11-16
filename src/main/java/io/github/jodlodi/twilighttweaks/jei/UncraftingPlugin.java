package io.github.jodlodi.twilighttweaks.jei;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import io.github.jodlodi.twilighttweaks.data.recipes.IShapedUncraftingRecipe;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import twilightforest.block.TFBlocks;
import twilightforest.inventory.UncraftingContainer;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class UncraftingPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return TwilightTweaks.twilightResource("uncrafting_jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new UncraftingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), IShapedUncraftingRecipe.RESOURCE_LOCATION);
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), VanillaRecipeCategoryUid.CRAFTING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(UncraftingContainer.class, VanillaRecipeCategoryUid.CRAFTING, 2, 9, 12, 36);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) {
            List<UncraftingRecipe> recipes = Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(UncraftingRecipe.class::isInstance).map(UncraftingRecipe.class::cast).collect(Collectors.toList());
            registration.addRecipes(recipes, IShapedUncraftingRecipe.RESOURCE_LOCATION);
        }
    }
}
