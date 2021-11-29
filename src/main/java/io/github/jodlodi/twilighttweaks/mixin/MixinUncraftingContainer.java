package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.inventory.ContainerTFUncrafting;

import java.util.ArrayList;
import java.util.List;

@Mixin(ContainerTFUncrafting.class)
public abstract class MixinUncraftingContainer {
    @Unique
    private static IRecipe currentRecipe;

    /**
     * @author jodlodi
     * @reason Needed to check if a vanilla recipe is being overriden
     */
    @Overwrite
    private static IRecipe[] getRecipesFor(ItemStack inputStack) {
        List<IRecipe> craftingRecipes = new ArrayList<>();
        if (!inputStack.isEmpty()) {
            List<UncraftingRecipe> uncraftingRecipes = ModRecipeTypes.uncraftingRecipes; //Get one-way Uncrafting recipes
            List<Item> replacements = new ArrayList<>();
            uncraftingRecipes.stream().filter(UncraftingRecipe::getReplace).forEach(u -> replacements.add(u.getRecipeOutput().getItem())); //Get items from uncrafting recipes marked with replace
            for (IRecipe r : CraftingManager.REGISTRY) craftingRecipes.add(r); //Get vanilla recipes
            craftingRecipes.removeIf(r -> replacements.stream().anyMatch(s -> s.equals(r.getRecipeOutput().getItem()) || ModRecipeTypes.bannedUncraft.contains(r.getRecipeOutput().getItem()))); //Remove empty ones or ones that have a replacer as a result
            craftingRecipes.addAll(uncraftingRecipes);
            craftingRecipes.removeIf(r -> !(r.canFit(3, 3)) ||
                    r.getIngredients().isEmpty() ||
                    !(inputStack.getItem() == r.getRecipeOutput().getItem()) ||
                    inputStack.getCount() < r.getRecipeOutput().getCount() ||
                    inputStack.getMetadata() != r.getRecipeOutput().getMetadata());
        }
        return craftingRecipes.toArray(new IRecipe[0]);
    }

    @Inject(method = "getIngredients", at = @At(value = "HEAD"), remap = false)
    private void setRecipe(IRecipe recipe, CallbackInfoReturnable<ItemStack[]> cir) {
        currentRecipe = recipe;
    }

    /**
     * @author jodlodi
     * @reason Check if recipe has a custom cost or not
     */
    @Overwrite
    private static int countDamageableParts(IInventory matrix) {
        boolean uncrafting = currentRecipe instanceof UncraftingRecipe;
        int c = 0;
        for (int i = 0; i < matrix.getSizeInventory(); i++) {
            if (!matrix.getStackInSlot(i).isEmpty() && matrix.getStackInSlot(i).getItem() != Items.STICK) c++;
        }
        if (uncrafting && c > 0) return ((UncraftingRecipe)currentRecipe).getCost();
        return c;
    }
}