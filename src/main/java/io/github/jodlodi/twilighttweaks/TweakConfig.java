package io.github.jodlodi.twilighttweaks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = TwilightTweaks.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TweakConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue DISABLE_FLAG = BUILDER
            .comment("""
                            The very top of the final castle now generates a jigsaw structure from "final_castle/temp/gazebo". (Twilight Forest does this, not Twilight Tweaks).
                            For sake of your convenience this mod disables that feature via this config option.
                            This is done due to the fact that the default jigsaw structure is a giant unbreakable forcefield cube, which makes the final spawner kinda hard to get to.
                            If you wish to re-enable this feature, disable this config option!!! Simple as that.
                            However, if this is disabled, I also wont place the final boss spawner block manually, assuming that you'll do it yourself in your custom jigsaw structure. Don't be lazy.""")
            .define("The final castle gazebo is empty: ", true);

    private static final ModConfigSpec.ConfigValue<String> COMMAND_CUSTOM = BUILDER
            .comment("""
                            Here you can set what command gets executed when the final boss spawner is activated.
                            Type in the command you want to happen the exact same way as you would in game.
                            For example, typing "summon minecraft:skeleton ~ ~ ~" will summon a normal skeleton and typing "summon zombie ~ ~ ~ {Invulnerable:1,HandItems:[{Count:1,id:wooden_sword},{}]}" will summon an invincible zombie that is holding a wooden sword.
                            By default, the spawner runs "function twilighttweaks:final_boss_example", which is a mcfunction made for the mod that runs several commands in a row. Mcfunctions are a vanilla feature and you can google how to make one of your own.
                            Any command can be ran once, after which the spawner will break itself.""")
            .define("The command that the spawner should run: ", "function twilighttweaks:final_boss_example");

    private static final ModConfigSpec.BooleanValue REMNANT_FLAG = BUILDER
            .comment("""
                            With this mod installed, boss spawners now leave remnants behind (usually, after the actual boss has been killed).
                            Additionally, killed hostile mobs in the Twilight Forest dimension have a chance (0.5%) to drop Time powder.
                            Using Time powder on boss spawner remnants brings the spawner back, allowing the boss to be fought again.""")
            .define("The boss spawner feature is enabled: ", true);

    public static boolean disableFlag;
    public static String commandCustom;
    public static boolean remnantFlag;

    static final ModConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        disableFlag = DISABLE_FLAG.get();
        commandCustom = COMMAND_CUSTOM.get();
        remnantFlag = REMNANT_FLAG.get();
    }
}