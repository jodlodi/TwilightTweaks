package io.github.jodlodi.twilighttweaks.data.recipes;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import twilightforest.block.TFBlocks;
import twilightforest.item.TFItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = TwilightTweaks.MODID)
public class ModRecipeTypes {
    public static ResourceLocation UNCRAFTING;
    public static final List<UncraftingRecipe> uncraftingRecipes = new ArrayList<>();
    public static List<Item> bannedUncraft = Arrays.asList(TFItems.shield_scepter, TFItems.lifedrain_scepter, TFItems.twilight_scepter, TFItems.zombie_scepter);

    public static UncraftingRecipe regRecipes(Boolean replace, int cost, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        UncraftingRecipe recipe = new UncraftingRecipe(replace, cost, width, height, recipeItems, result);
        uncraftingRecipes.add(recipe);
        return recipe;
    }

    public static void init() {
        UNCRAFTING = TFBlocks.uncrafting_table.getRegistryName();
    }
}