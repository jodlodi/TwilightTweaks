package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import twilightforest.inventory.UncraftingContainer;

import java.util.ArrayList;
import java.util.List;

@Mixin(UncraftingContainer.class)
public abstract class MixinUncraftingContainer {
    @Unique
    private static ICraftingRecipe currentRecipe;

    /**
     * @author jodlodi
     * @reason Needed to check if a vanilla recipe is being overriden
     */
    @Overwrite
    private static ICraftingRecipe[] getRecipesFor(ItemStack inputStack, World world) {
        List<ICraftingRecipe> craftingRecipes = new ArrayList<>();
        if (!inputStack.isEmpty()) {
            List<UncraftingRecipe> uncraftingRecipes = world.getRecipeManager().getAllRecipesFor(ModRecipeTypes.UNCRAFTING_RECIPE); //Get one-way Uncrafting recipes
            List<ItemStack> replacements = new ArrayList<>();
            uncraftingRecipes.stream().filter(UncraftingRecipe::getReplace).forEach(u -> replacements.add(u.getResultItem())); //Get items from uncrafting recipes marked with replace
            craftingRecipes = world.getRecipeManager().getAllRecipesFor(IRecipeType.CRAFTING); //Get vanilla recipes
            craftingRecipes.removeIf(r -> replacements.stream().anyMatch(s -> s.sameItem(r.getResultItem()))); //Remove empty ones or ones that have a replacer as a result
            craftingRecipes.addAll(uncraftingRecipes);
            craftingRecipes.removeIf(r -> !(r.canCraftInDimensions(3, 3)) || r.getIngredients().isEmpty() || !(inputStack.getItem() == r.getResultItem().getItem() && inputStack.getCount() >= r.getResultItem().getCount()));
        }
        return craftingRecipes.toArray(new ICraftingRecipe[0]);
    }

    @Inject(method = "getIngredients", at = @At(value = "HEAD"), remap = false)
    private void setRecipe(ICraftingRecipe recipe, CallbackInfoReturnable<ItemStack[]> cir) {
        currentRecipe = recipe;
    }

    /**
     * @author jodlodi
     * @reason Made it check for any wooden rod item and so it can return a custom recipe cost
     */
    @Overwrite
    private static int countDamageableParts(IInventory inventory) {
        boolean uncrafting = currentRecipe instanceof UncraftingRecipe;
        int c = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty() && !inventory.getItem(i).getItem().is(Tags.Items.RODS_WOODEN)) c++;
        }
        if (uncrafting && c > 0) return ((UncraftingRecipe) currentRecipe).getCost();
        return c;
    }
}