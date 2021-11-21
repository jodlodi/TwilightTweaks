package io.github.jodlodi.twilighttweaks;

import io.github.jodlodi.twilighttweaks.data.recipes.ModRecipeTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TwilightTweaks.MOD_ID)
@Mod.EventBusSubscriber(modid = TwilightTweaks.MOD_ID)
public class TwilightTweaks
{
    public static final String MOD_ID = "twilighttweaks";

    public TwilightTweaks() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(this::configSetup);
        ModRecipeTypes.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TweakConfig.COMMON_SPEC);
    }

    private void configSetup(ModConfig.ModConfigEvent event) {
        TweakConfig.refresh();
        String[] input = {TweakConfig.loopCustom, TweakConfig.linearCustom};
        ConfigSetup.addCustomInitTransformations(input);
    }

    public static ResourceLocation twilightResource(String key) {
        return new ResourceLocation(TwilightTweaks.MOD_ID, key);
    }
}