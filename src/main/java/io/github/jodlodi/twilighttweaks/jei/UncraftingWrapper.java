package io.github.jodlodi.twilighttweaks.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UncraftingWrapper implements IRecipeWrapper {

    private final ItemStack input;
    private final List<List<ItemStack>> output;
    private final int recipeWidth;
    private final int recipeHeight;

    public UncraftingWrapper(IRecipe recipe) {
        input = recipe.getRecipeOutput();

        List<List<ItemStack>> outputBuilder = new ArrayList<>();
        recipe.getIngredients().forEach(i -> outputBuilder.add(Arrays.asList(i.getMatchingStacks())));
        for (int i = 0; i < outputBuilder.size(); i++) {
            List<ItemStack> newList = outputBuilder.get(i);
            outputBuilder.set(i, newList.stream().filter(j -> !(j.getItem().hasContainerItem(j))).collect(Collectors.toList()));
        }
        output = outputBuilder;

        if (recipe instanceof IShapedRecipe) {
            recipeWidth = ((IShapedRecipe)recipe).getRecipeWidth();
            recipeHeight = ((IShapedRecipe)recipe).getRecipeHeight();
        } else {
            recipeWidth = 3;
            recipeHeight = 3;
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setOutputLists(VanillaTypes.ITEM, output);
    }

    public int getWidth() {
        return this.recipeWidth;
    }

    public int getHeight() {
        return this.recipeHeight;
    }
}