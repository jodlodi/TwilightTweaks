package io.github.jodlodi.twilighttweaks.event;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import twilightforest.init.TFDimension;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@EventBusSubscriber(modid = TwilightTweaks.MOD_ID)
public class ServerEventSubscriber {

    @SubscribeEvent
    public static void livingDropsEventListener(LivingDropsEvent event) {
        if (event.getEntity() instanceof Mob mob && mob instanceof Enemy && mob.level() instanceof ServerLevel serverLevel && serverLevel.dimension().location().equals(TFDimension.DIMENSION)) {
            if (TweakConfig.remnantFlag && serverLevel.random.nextInt(200) == 0) mob.spawnAtLocation(TwilightTweaks.TIME_POWDER.get());
        }
    }
}
