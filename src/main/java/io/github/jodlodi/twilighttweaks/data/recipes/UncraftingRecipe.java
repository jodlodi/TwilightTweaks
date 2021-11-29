package io.github.jodlodi.twilighttweaks.data.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry.Impl;

public class UncraftingRecipe extends Impl<IRecipe> implements IShapedRecipe {
    private final Boolean replace;
    private final int cost;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack result;

    public UncraftingRecipe(Boolean replace, int cost, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        this.replace = replace;
        this.cost = cost;
        this.width = width;
        this.height = height;
        this.recipeItems = recipeItems;
        this.result = result;
    }

    public Boolean getReplace() {
        return this.replace;
    }

    public int getCost() {
        return this.cost;
    }

    public int getRecipeWidth() {
        return this.width;
    }

    public int getRecipeHeight() {
        return this.height;
    }

    public ItemStack getRecipeOutput() {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public boolean canFit(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public boolean matches(InventoryCrafting inv, World worldIn) {
        {
            for (int i = 0; i <= inv.getWidth() - this.width; ++i) {
                for (int j = 0; j <= inv.getHeight() - this.height; ++j) {
                    if (this.matches(inv, i, j, true)) return true;
                    if (this.matches(inv, i, j, false)) return true;
                }
            }
            return false;
        }
    }

    private boolean matches(InventoryCrafting craftingInventory, int oldi, int oldj, boolean yesNoMaybe) {
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - oldi;
                int l = j - oldj;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (yesNoMaybe) ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    else ingredient = this.recipeItems.get(k + l * this.width);
                }
                if (!ingredient.apply(craftingInventory.getStackInRowAndColumn(i, j))) return false;
            }
        }
        return true;
    }

    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return ItemStack.EMPTY;
    }
}