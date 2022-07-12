package io.github.jodlodi.twilighttweaks.event;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.world.registration.TFGenerationSettings;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(modid = TwilightTweaks.MOD_ID)
public class ServerEventSubscriber {

    @SubscribeEvent
    public static void livingDropsEventListener(LivingDropsEvent event) {
        if (event.getEntity() instanceof Mob mob && mob instanceof Enemy && mob.level instanceof ServerLevel serverLevel && serverLevel.dimension().location().equals(TFGenerationSettings.DIMENSION)) {
            if (serverLevel.random.nextInt(200) == 0) mob.spawnAtLocation(TwilightTweaks.TIME_POWDER.get());
        }
    }
}
