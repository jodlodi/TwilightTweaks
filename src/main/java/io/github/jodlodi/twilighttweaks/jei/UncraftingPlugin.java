package io.github.jodlodi.twilighttweaks.jei;

import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import twilightforest.block.TFBlocks;
import twilightforest.client.GuiTFGoblinCrafting;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class UncraftingPlugin implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new UncraftingCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(UncraftingRecipe.class, UncraftingWrapper::new, UncraftingCategory.id);
        registry.handleRecipes(ShapedOreRecipe.class, UncraftingWrapper::new, UncraftingCategory.id);
        registry.handleRecipes(ShapedRecipes.class, UncraftingWrapper::new, UncraftingCategory.id);
        registry.handleRecipes(ShapelessOreRecipe.class, UncraftingWrapper::new, UncraftingCategory.id);
        registry.handleRecipes(ShapelessRecipes.class, UncraftingWrapper::new, UncraftingCategory.id);

        List<IRecipe> craftingRecipes = new ArrayList<>();
        List<UncraftingRecipe> uncraftingRecipes = ModRecipeTypes.uncraftingRecipes; //Get one-way Uncrafting recipes
        List<Item> replacements = new ArrayList<>();
        uncraftingRecipes.stream().filter(UncraftingRecipe::getReplace).forEach(u -> replacements.add(u.getRecipeOutput().getItem())); //Get items from uncrafting recipes marked with replace
        for (IRecipe r : CraftingManager.REGISTRY) craftingRecipes.add(r); //Get vanilla recipes
        craftingRecipes.removeIf(r -> replacements.stream().anyMatch(s -> s.equals(r.getRecipeOutput().getItem()) || ModRecipeTypes.bannedUncraft.contains(r.getRecipeOutput().getItem()))); //Remove empty ones or ones that have a replacer as a result
        craftingRecipes.addAll(uncraftingRecipes);
        craftingRecipes.removeIf(r -> !(r.canFit(3, 3)) || r.getIngredients().isEmpty());

        registry.addRecipes(craftingRecipes, UncraftingCategory.id);

        registry.addRecipeCatalyst(new ItemStack(TFBlocks.uncrafting_table), UncraftingCategory.id);

        registry.addRecipeClickArea(GuiTFGoblinCrafting.class, 34, 33, 27, 20, UncraftingCategory.id);
        registry.addRecipeClickArea(GuiTFGoblinCrafting.class, 115, 33, 27, 20, VanillaRecipeCategoryUid.CRAFTING);
    }
}