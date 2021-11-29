package io.github.jodlodi.twilighttweaks.jei;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import twilightforest.block.TFBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class UncraftingCategory implements IRecipeCategory<UncraftingWrapper> {

    public static final String id = "jei_uncrafting";
    public static final int width = 116;
    public static final int height = 54;
    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public UncraftingCategory(IGuiHelper iGuiHelper) {
        ResourceLocation location = TwilightTweaks.twilightResource("textures/gui/uncraft.png");
        this.background = iGuiHelper.createDrawable(location, 0, 0, width, height);
        this.icon = iGuiHelper.createDrawableIngredient(new ItemStack(TFBlocks.uncrafting_table));
        this.localizedName = I18n.format("gui." + TwilightTweaks.MODID + ".category.uncraftingTable");
    }

    @Override
    public String getUid() {
        return id;
    }

    @Override
    public String getTitle() {
        return this.localizedName;
    }

    @Override
    public String getModName() {
        return TwilightTweaks.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, UncraftingWrapper recipeWrapper, IIngredients ingredients) {
        int i = 0;
        List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        for (int y = 0; y < recipeWrapper.getHeight(); y++) {
            for (int x = 0; x < recipeWrapper.getWidth(); x++) {
                if (i == outputs.size()) break;
                recipeLayout.getItemStacks().init(++i, true, x * 18 + 62, y * 18);
                recipeLayout.getItemStacks().set(i, outputs.get(i - 1));
            }
        }
        recipeLayout.getItemStacks().init(++i, false, 4, 18);
        recipeLayout.getItemStacks().set(i, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    }
}