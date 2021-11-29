package io.github.jodlodi.twilighttweaks.crt;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.github.jodlodi.twilighttweaks.data.recipes.DefaultUncrafts;
import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenClass("mods.twilighttweaks.uncrafting")
public class UncraftingZen {
    @ZenMethod
    public static void addRecipe(boolean replace, int cost, IIngredient[][] output, IIngredient input) {
        int width = output[0].length;
        int height = output.length;

        ItemStack newInput = ItemStack.EMPTY;
        if (input != null) { newInput = CraftTweakerMC.getItemStack(input); }

        List<Ingredient> outputsAsList = new ArrayList<>();
        for(IIngredient[] i : output) for (IIngredient j : i) outputsAsList.add(CraftTweakerMC.getIngredient(j));
        NonNullList<Ingredient> newOutput = NonNullList.from(outputsAsList.get(0), outputsAsList.toArray(new Ingredient[0]));

        if (newInput != null && output[0][0] != null) CraftTweakerAPI.apply(new AddUncraft(new UncraftingRecipe(replace, cost, width, height, newOutput, newInput)));
    }

    protected static class AddUncraft implements IAction {
        private final UncraftingRecipe uncraftingRecipe;

        public AddUncraft(UncraftingRecipe uncraftingRecipe) {
            this.uncraftingRecipe = uncraftingRecipe;
        }

        @Override
        public void apply() {
            ModRecipeTypes.uncraftingRecipes.add(this.uncraftingRecipe);
        }

        @Override
        public String describe() {
            return "Added an uncrafting recipe for the ingredient " + this.uncraftingRecipe.getRecipeOutput() + "! ";
        }
    }

    @ZenMethod
    public static void removeDefaultRecipes(boolean[] toKeep) {
        CraftTweakerAPI.apply(new RemoveUncraft(toKeep));
    }

    protected static class RemoveUncraft implements IAction {
        private final boolean[] key;

        public RemoveUncraft(boolean[] toKeep) {
            this.key = toKeep;
        }

        @Override
        public void apply() {
            DefaultUncrafts.bannedDefaultRecipes = this.key;
        }

        @Override
        public String describe() {
            String s0 = "";
            String s1 = "";
            String s2 = "";
            String s3 = "";
            String s4 = "";

            if (!key[0]) s0 = "\nRemoved the Slightly Damaged Anvil uncrafting recipe!";
            if (!key[1]) s1 = "\nRemoved the Very Damaged Anvil uncrafting recipe!";
            if (!key[2]) s2 = "\nRemoved the Knightmetal Ingot uncrafting recipe!";
            if (!key[3]) s3 = "\nRemoved the Tipped Arrow uncrafting recipe!";
            if (!key[4]) s4 = "\nRemoved the Written Book uncrafting recipe!";

            return "The following default uncrafting recipes have been removed: " + s0 + s1 + s2 + s3 + s4;
        }
    }

    @ZenMethod
    public static void banUncraft(IItemStack input) {
        CraftTweakerAPI.apply(new BanUncraft(CraftTweakerMC.getItemStack(input)));
    }

    protected static class BanUncraft implements IAction {
        private final ItemStack toBan;

        public BanUncraft(ItemStack toBan) {
            this.toBan = toBan;
        }

        @Override
        public void apply() {
            ModRecipeTypes.uncraftingRecipes.add(new UncraftingRecipe(true, 0, 4, 4, null, toBan));
        }

        @Override
        public String describe() {
            return "The item " + toBan + " has been banned from uncrafting!";
        }
    }
}