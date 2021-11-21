package io.github.jodlodi.twilighttweaks.data.recipes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import twilightforest.block.TFBlocks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

import static net.minecraft.util.registry.Registry.ITEM;
import static net.minecraftforge.common.crafting.CraftingHelper.getItemStack;

public class UncraftingRecipe implements IUncraftingRecipe, IShapedUncraftingRecipe<CraftingInventory> {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;

    private final ResourceLocation id;
    private final String group;
    private final Boolean replace;
    private final int cost;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack result;

    public UncraftingRecipe(ResourceLocation id, String group, Boolean replace, int cost, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
        this.id = id;
        this.group = group;
        this.replace = replace;
        this.cost = cost;
        this.width = width;
        this.height = height;
        this.recipeItems = recipeItems;
        this.result = result;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean getReplace() {
        return this.replace;
    }

    public int getCost() {
        return this.cost;
    }

    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.UNCRAFTING_SERIALIZER.get();
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        for(int i = 0; i <= craftingInventory.getWidth() - this.width; ++i) {
            for(int j = 0; j <= craftingInventory.getHeight() - this.height; ++j) {
                if (this.matches(craftingInventory, i, j, true)) return true;
                if (this.matches(craftingInventory, i, j, false)) return true;
            }
        }
        return false;
    }

    private boolean matches(CraftingInventory craftingInventory, int oldi, int oldj, boolean yesNoMaybe) {
        for(int i = 0; i < craftingInventory.getWidth(); ++i) {
            for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                int k = i - oldi;
                int l = j - oldj;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (yesNoMaybe) ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    else ingredient = this.recipeItems.get(k + l * this.width);
                }
                if (!ingredient.test(craftingInventory.getItem(i + j * craftingInventory.getWidth()))) return false;
            }
        }
        return true;
    }

    public ItemStack assemble(CraftingInventory craftingInventory) {
        //return this.getResultItem().copy();
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ItemStack getToastSymbol() {
        return TFBlocks.uncrafting_table.get().asItem().getDefaultInstance();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<UncraftingRecipe> {
        @Override
        public UncraftingRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = JSONUtils.getAsString(json, "group", "");
            boolean replace = JSONUtils.getAsBoolean(json, "replace");
            int cost = JSONUtils.getAsInt(json, "cost");
            Map<String, Ingredient> key = keyFromJson(JSONUtils.getAsJsonObject(json, "key"));
            String[] pattern = shrink(patternFromJson(JSONUtils.getAsJsonArray(json, "pattern")));
            int width = pattern[0].length();
            int height = pattern.length;
            NonNullList<Ingredient> ingredients = dissolvePattern(pattern, key, width, height);
            ItemStack result = itemFromJson(JSONUtils.getAsJsonObject(json, "ingredient"));
            return new UncraftingRecipe(id, group, replace, cost, width, height, ingredients, result);
        }

        private static Map<String, Ingredient> keyFromJson(JsonObject json) {
            Map<String, Ingredient> map = Maps.newHashMap();
            for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getKey().length() != 1)
                    throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                if (" ".equals(entry.getKey()))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
            }
            map.put(" ", Ingredient.EMPTY);
            return map;
        }

        static String[] shrink(String... prePattern) {
            int i = Integer.MAX_VALUE;
            int j = 0;
            int k = 0;
            int l = 0;

            for(int i1 = 0; i1 < prePattern.length; ++i1) {
                String s = prePattern[i1];
                i = Math.min(i, firstNonSpace(s));
                int j1 = lastNonSpace(s);
                j = Math.max(j, j1);
                if (j1 < 0) {
                    if (k == i1) ++k;
                    ++l;
                } else l = 0;
            }

            if (prePattern.length == l) return new String[0];
            else {
                String[] astring = new String[prePattern.length - l - k];
                for(int k1 = 0; k1 < astring.length; ++k1) astring[k1] = prePattern[k1 + k].substring(i, j + 1);
                return astring;
            }
        }

        private static int firstNonSpace(String first) {
            int i;
            i = 0;
            while (i < first.length() && first.charAt(i) == ' ') ++i;
            return i;
        }

        private static int lastNonSpace(String last) {
            int i;
            i = last.length() - 1;
            while (i >= 0 && last.charAt(i) == ' ') --i;
            return i;
        }

        private static String[] patternFromJson(JsonArray pattern) {
            String[] astring = new String[pattern.size()];
            if (astring.length > MAX_HEIGHT) {
                throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
            } else if (astring.length == 0) {
                throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
            } else {
                for(int i = 0; i < astring.length; ++i) {
                    String s = JSONUtils.convertToString(pattern.get(i), "pattern[" + i + "]");
                    if (s.length() > MAX_WIDTH)
                        throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                    if (i > 0 && astring[0].length() != s.length())
                        throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                    astring[i] = s;
                }
                return astring;
            }
        }

        private static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> key, int width, int height) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(width * height, Ingredient.EMPTY);
            Set<String> set = Sets.newHashSet(key.keySet());
            set.remove(" ");

            for(int i = 0; i < pattern.length; ++i) {
                for(int j = 0; j < pattern[i].length(); ++j) {
                    String s = pattern[i].substring(j, j + 1);
                    Ingredient ingredient = key.get(s);
                    if (ingredient == null)
                        throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                    set.remove(s);
                    nonnulllist.set(j + width * i, ingredient);
                }
            }

            if (!set.isEmpty()) throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
            else return nonnulllist;
        }

        public static ItemStack itemFromJson(JsonObject json) {
            String s = JSONUtils.getAsString(json, "item");
            ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + s + "'"));
            if (json.has("data")) throw new JsonParseException("Disallowed data tag found");
            else {
                JSONUtils.getAsInt(json, "count", 1);
                return getItemStack(json, true);
            }
        }

        @Nullable
        @Override
        public UncraftingRecipe fromNetwork(ResourceLocation id, PacketBuffer buffer) {
            String group = buffer.readUtf(32767);
            boolean replace = buffer.readBoolean();
            int cost = buffer.readVarInt();
            int width = buffer.readVarInt();
            int height = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
            for(int k = 0; k < ingredients.size(); ++k) ingredients.set(k, Ingredient.fromNetwork(buffer));
            ItemStack result = buffer.readItem();
            return new UncraftingRecipe(id, group, replace, cost, width, height, ingredients, result);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, UncraftingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeBoolean(recipe.replace);
            buffer.writeVarInt(recipe.cost);
            buffer.writeVarInt(recipe.width);
            buffer.writeVarInt(recipe.height);
            recipe.recipeItems.forEach(ingredient -> ingredient.toNetwork(buffer));
            buffer.writeItem(recipe.result);
        }
    }
}