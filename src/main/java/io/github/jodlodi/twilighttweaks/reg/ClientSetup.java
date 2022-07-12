package io.github.jodlodi.twilighttweaks.reg;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = TwilightTweaks.MOD_ID)
public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(TwilightTweaks.BOSS_SPAWNER_REMNANT.get(), RenderType.cutout());
    }
}
