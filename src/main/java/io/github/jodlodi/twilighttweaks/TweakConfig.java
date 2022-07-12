package io.github.jodlodi.twilighttweaks;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.tuple.Pair;

@EventBusSubscriber(modid = TwilightTweaks.MOD_ID)
public class TweakConfig {
    public static final Common COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<Common, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = pair.getRight();
        COMMON_CONFIG = pair.getLeft();
    }

    public static String commandCustom;
    public static boolean remnantFlag;

    public static class Common {
        public final ConfigValue<String> finalCommandCustom;
        public final ConfigValue<Boolean> finalRemnantFlag;

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("""
                            Here you can set what command gets executed when the final boss spawner is activated.
                            Type in the command you want to happen the exact same way as you would in game.
                            For example, typing "summon minecraft:skeleton ~ ~ ~" will summon a normal skeleton and typing "summon zombie ~ ~ ~ {Invulnerable:1,HandItems:[{Count:1,id:wooden_sword},{}]}" will summon an invincible zombie that is holding a wooden sword.
                            By default, the spawner runs "function twilighttweaks:final_boss_example", which is a mcfunction made for the mod that runs several commands in a row. Mcfunctions are a vanilla feature and you can google how to make one of your own.
                            Any command can be ran once, after which the spawner will break itself.""").push("FINAL BOSS SPAWNER");
            finalCommandCustom = builder.define("The command that the spawner should run: ", "function twilighttweaks:final_boss_example");
            builder.pop();
            builder.comment("""
                            With this mod installed, boss spawners now leave remnants behind (usually, after the actual boss has been killed).
                            Additionally, killed hostile mobs in the Twilight Forest dimension have a chance (0.5%) to drop Time powder.
                            Using Time powder on boss spawner remnants brings the spawner back, allowing the boss to be fought again.""").push("BOSS SPAWNER REMNANTS");
            finalRemnantFlag = builder.define("The boss spawner feature is enabled: ", true);
        }
    }

    public static void refresh() {
        commandCustom = COMMON_CONFIG.finalCommandCustom.get();
        remnantFlag = COMMON_CONFIG.finalRemnantFlag.get();
    }
}