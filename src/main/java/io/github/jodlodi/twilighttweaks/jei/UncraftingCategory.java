package io.github.jodlodi.twilighttweaks.jei;

import com.google.common.collect.ImmutableList;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import twilightforest.block.TFBlocks;

import java.util.Arrays;
import java.util.List;

public class UncraftingCategory implements IRecipeCategory<ICraftingRecipe> {
    public static final ResourceLocation id = TwilightTweaks.twilightResource("jei_uncrafting");
    public static final int width = 116;
    public static final int height = 54;
    private final IDrawable background;
    private final IDrawable icon;
    private final ITextComponent localizedName;

    public UncraftingCategory(IGuiHelper iGuiHelper) {
        ResourceLocation location = TwilightTweaks.twilightResource("textures/gui/uncraft.png");
        this.background = iGuiHelper.createDrawable(location, 0, 0, width, height);
        this.icon = iGuiHelper.createDrawableIngredient(new ItemStack(TFBlocks.uncrafting_table.get()));
        this.localizedName = new TranslationTextComponent("gui." + TwilightTweaks.MOD_ID + ".category.uncraftingTable");
    }

    @Override
    public ResourceLocation getUid() {
        return id;
    }

    @Override
    public Class<? extends ICraftingRecipe> getRecipeClass() {
        return ICraftingRecipe.class;
    }

    @Override
    public String getTitle() {
        return this.localizedName.getString();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(ICraftingRecipe recipe, IIngredients ingredients) {
        ImmutableList.Builder<ItemStack> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<List<ItemStack>> outputBuilder = ImmutableList.builder();

        ItemStack uncraftable = recipe.getResultItem();
        if (!uncraftable.isEmpty()) inputBuilder.add(uncraftable);

        for (Ingredient ingredient : recipe.getIngredients()) outputBuilder.add(Arrays.asList(ingredient.getItems()));

        ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(inputBuilder.build()));
        ingredients.setOutputLists(VanillaTypes.ITEM, outputBuilder.build());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ICraftingRecipe recipe, IIngredients ingredients) {
        int i = 0;
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int j = 0, k = 0; j < outputs.size(); j++) {
            int x = i % 3, y = i / 3;
            recipeLayout.getItemStacks().init(i + 1, true, x * 18 + 62, y * 18);
            List<ItemStack> draw = outputs.get(j - k);
            if (recipe.canCraftInDimensions(y, 3) | recipe.canCraftInDimensions(3, x)) {
                draw = null;
                k++;
            }
            recipeLayout.getItemStacks().set(++i, draw);
        }
        recipeLayout.getItemStacks().init(++i, false, 4, 18);
        recipeLayout.getItemStacks().set(i, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    }
}
