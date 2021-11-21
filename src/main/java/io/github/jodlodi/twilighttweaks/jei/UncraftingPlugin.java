package io.github.jodlodi.twilighttweaks.jei;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
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
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import twilightforest.block.TFBlocks;
import twilightforest.inventory.UncraftingContainer;

import java.util.ArrayList;
import java.util.List;

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
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table.get()), UncraftingCategory.id);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(UncraftingContainer.class, VanillaRecipeCategoryUid.CRAFTING, 11, 9, 20, 36);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) {
            List<UncraftingRecipe> uncraftingRecipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.UNCRAFTING_RECIPE); //Get one-way Uncrafting recipes
            List<ItemStack> replacements = new ArrayList<>();
            uncraftingRecipes.stream().filter(UncraftingRecipe::getReplace).forEach(u -> replacements.add(u.getResultItem())); //Get items from uncrafting recipes marked with replace
            List<ICraftingRecipe> craftingRecipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IRecipeType.CRAFTING); //Get vanilla recipes
            craftingRecipes.removeIf(r -> r.getResultItem().isEmpty() || replacements.stream().anyMatch(s -> s.sameItem(r.getResultItem()))); //Remove empty ones or ones that have a replacer as a result
            craftingRecipes.addAll(uncraftingRecipes);
            registration.addRecipes(craftingRecipes, UncraftingCategory.id);
        }
    }
}