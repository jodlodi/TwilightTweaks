package io.github.jodlodi.twilighttweaks.jei;

import com.google.common.collect.ImmutableList;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import io.github.jodlodi.twilighttweaks.data.recipes.IUncraftingRecipe;
import io.github.jodlodi.twilighttweaks.data.recipes.UncraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import twilightforest.block.TFBlocks;

import java.util.Arrays;
import java.util.List;

public class UncraftingCategory implements IRecipeCategory<IUncraftingRecipe> {
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
        return UncraftingRecipe.RESOURCE_LOCATION;
    }

    @Override
    public Class<? extends IUncraftingRecipe> getRecipeClass() {
        return UncraftingRecipe.class;
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
    public void setIngredients(IUncraftingRecipe recipe, IIngredients ingredients) {
        ImmutableList.Builder<ItemStack> inputBuilder = ImmutableList.builder();
        ImmutableList.Builder<List<ItemStack>> outputBuilder = ImmutableList.builder();

        ItemStack uncraftable = recipe.getResultItem();
        if (!uncraftable.isEmpty()) inputBuilder.add(uncraftable);

        for (Ingredient i : recipe.getIngredients()) outputBuilder.add(Arrays.asList(i.getItems()));

        ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(inputBuilder.build()));
        ingredients.setOutputLists(VanillaTypes.ITEM, outputBuilder.build());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IUncraftingRecipe recipe, IIngredients ingredients) {
        /*List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
        List<List<ItemStack>> outputs  = ingredients.getOutputs(VanillaTypes.ITEM);
        IFocus<?> focus = recipeLayout.getFocus();

        recipeLayout.getItemStacks().init(0, true, 10, 35);
        recipeLayout.getItemStacks().set(0, getItemMatchingFocus(focus, IFocus.Mode.OUTPUT, outputs.get(0), inputs.get(0)));

        int index = 1, posX = 76 - (inputs.size() * 9);
        for (int i = 1; i < inputs.size(); i++) {
            List<ItemStack> o = inputs.get(i);
            recipeLayout.getItemStacks().init(index, true, posX, 0);
            recipeLayout.getItemStacks().set(index, o);
            index++;
            posX += 18;
        }

        recipeLayout.getItemStacks().init(7, false, 58, 35);
        recipeLayout.getItemStacks().set(7, getItemMatchingFocus(focus, IFocus.Mode.INPUT, inputs.get(0), outputs.get(0)));*/




        int index = 0;


        for (List<ItemStack> o : ingredients.getOutputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index + 1, true, (index % 3) * 18 + 62, (index / 3) * 18);
            recipeLayout.getItemStacks().set(++index, o);
        }

        recipeLayout.getItemStacks().init(++index, false, 4, 18);
        recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));
    }


    /*private List<ItemStack> getItemMatchingFocus(IFocus<?> focus, IFocus.Mode mode, List<ItemStack> focused, List<ItemStack> other) {
        if (focus != null && focus.getMode() == mode) {
            ItemStack focusStack = (ItemStack) focus.getValue();
            for (int i = 0; i < focused.size(); i++) {
                if (focusStack.equals(focused.get(i))) {
                    return Collections.singletonList(other.get(i));
                }
            }
        }
        return other;
    }*/
}
