package io.github.jodlodi.twilighttweaks.data.recipes;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TwilightTweaks.MOD_ID);

    public static final RegistryObject<UncraftingRecipe.Serializer> UNCRAFTING_SERIALIZER = RECIPE_SERIALIZER.register("uncrafting", UncraftingRecipe.Serializer::new);

    public static final IRecipeType<UncraftingRecipe> UNCRAFTING_RECIPE = IRecipeType.register("twilighttweaks:uncrafting");

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, UncraftingRecipe.RESOURCE_LOCATION, UNCRAFTING_RECIPE);
    }
}
