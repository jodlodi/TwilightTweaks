package io.github.jodlodi.twilighttweaks.event;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = TwilightTweaks.MOD_ID)
public class ClientEventSubscriber {

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(TwilightTweaks.BOSS_SPAWNER_REMNANT.get(), RenderType.cutout());
    }
}
