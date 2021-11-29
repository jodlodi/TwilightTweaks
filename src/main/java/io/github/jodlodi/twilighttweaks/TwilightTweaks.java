package io.github.jodlodi.twilighttweaks;

import io.github.jodlodi.twilighttweaks.data.recipes.DefaultUncrafts;
import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@EventBusSubscriber
@Mod(modid = TwilightTweaks.MODID, name = TwilightTweaks.NAME, version = TwilightTweaks.VERSION, dependencies = "required-after:twilightforest")
public class TwilightTweaks
{
    public static File config;

    public static final String MODID = "twilighttweaks";
    public static final String NAME = "Twilight Tweaks";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TweakConfig.registerConfig(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        String[] input = {TweakConfig.loopCustom, TweakConfig.linearCustom};
        ConfigSetup.addCustomInitTransformations(input);
        DefaultUncrafts.init();
        ModRecipeTypes.init();
    }

    public static ResourceLocation twilightResource(String key) {
        return new ResourceLocation(TwilightTweaks.MODID, key);
    }
}