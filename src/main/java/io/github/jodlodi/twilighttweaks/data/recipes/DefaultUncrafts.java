package io.github.jodlodi.twilighttweaks.data.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import twilightforest.item.TFItems;

public final class DefaultUncrafts {
    public static UncraftingRecipe chipped_anvil_uncraft;
    public static UncraftingRecipe damaged_anvil_uncraft;
    public static UncraftingRecipe tipped_arrow_uncraft;
    public static UncraftingRecipe knightmetal_ingot_uncraft;
    public static UncraftingRecipe written_book_uncraft;

    public static boolean[] bannedDefaultRecipes = new boolean[] {true, true, true, true, true};

    public static void init() {
        Ingredient empty = Ingredient.fromItem(Items.AIR);
        Ingredient ironB = Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK));
        Ingredient ironI = Ingredient.fromItem(Items.IRON_INGOT);
        Ingredient shard = Ingredient.fromItem(TFItems.armor_shard);
        Ingredient arrow = Ingredient.fromItem(Items.ARROW);
        Ingredient book = Ingredient.fromItem(Items.BOOK);
        Ingredient inksac = Ingredient.fromStacks(new ItemStack(Items.DYE, 1, 0));

        NonNullList<Ingredient> chipped_anvil = NonNullList.from(ironI,
                ironB, ironI, ironI,
                empty, ironI, empty,
                ironI, ironI, ironI);
        if (bannedDefaultRecipes[0]) chipped_anvil_uncraft = ModRecipeTypes.regRecipes(false, 7, 3, 3, chipped_anvil, new ItemStack(Blocks.ANVIL, 1, 1));

        NonNullList<Ingredient> damaged_anvil = NonNullList.from(ironI,
                ironI, empty, empty,
                empty, ironI, empty,
                ironI, ironI, ironI);
        if (bannedDefaultRecipes[1]) damaged_anvil_uncraft = ModRecipeTypes.regRecipes(false, 6, 3, 3, damaged_anvil, new ItemStack(Blocks.ANVIL, 1, 2));

        NonNullList<Ingredient> knightmetal_ingot = NonNullList.from(shard,
                shard, shard, shard,
                shard, shard, shard,
                shard, shard, shard);
        if (bannedDefaultRecipes[2]) knightmetal_ingot_uncraft = ModRecipeTypes.regRecipes(false, 1, 3, 3, knightmetal_ingot, new ItemStack(TFItems.knightmetal_ingot, 1));

        NonNullList<Ingredient> tipped_arrow = NonNullList.from(arrow,
                arrow, arrow, arrow,
                arrow, empty, arrow,
                arrow, arrow, arrow);
        if (bannedDefaultRecipes[3]) tipped_arrow_uncraft = ModRecipeTypes.regRecipes(false, 4, 3, 3, tipped_arrow, new ItemStack(Items.TIPPED_ARROW, 8));

        NonNullList<Ingredient> written_book = NonNullList.from(book,
                book, inksac);
        if (bannedDefaultRecipes[4]) written_book_uncraft = ModRecipeTypes.regRecipes(false, 2, 2, 1, written_book, new ItemStack(Items.WRITTEN_BOOK, 1));
    }
}