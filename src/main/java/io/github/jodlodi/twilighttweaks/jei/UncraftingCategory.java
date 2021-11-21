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
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import twilightforest.block.TFBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        inputBuilder.add(recipe.getResultItem());

        List<List<ItemStack>> outputBuilder = new ArrayList<>();
        recipe.getIngredients().forEach(i -> outputBuilder.add(Arrays.asList(i.getItems())));
        for (int i = 0; i < outputBuilder.size(); i++) {
            List<ItemStack> newList = outputBuilder.get(i);
            outputBuilder.set(i, newList.stream().filter(j -> !(j.getItem().hasContainerItem(j))).collect(Collectors.toList()));
        }

        if (!(outputBuilder.stream().allMatch(j -> j.stream().allMatch(ItemStack::isEmpty)))) {
            ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(inputBuilder.build()));
            ingredients.setOutputLists(VanillaTypes.ITEM, outputBuilder);
        }
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ICraftingRecipe recipe, IIngredients ingredients) {
        int i = 0;
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int j = 0, k = 0; j - k < outputs.size() && j < 9; j++) {
            int x = j % 3, y = j / 3;
            if ((recipe.canCraftInDimensions(x, 3) | recipe.canCraftInDimensions(3, y)) && !(recipe instanceof ShapelessRecipe)) {
                k++;
                continue;
            }
            recipeLayout.getItemStacks().init(++i, true, x * 18 + 62, y * 18);
            recipeLayout.getItemStacks().set(i, outputs.get(j - k));
        }
        recipeLayout.getItemStacks().init(++i, false, 4, 18);
        recipeLayout.getItemStacks().set(i, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    }
}