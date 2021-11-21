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

    public static String loopCustom;
    public static String linearCustom;
    public static String commandCustom;

    public static class Common {
        public final ConfigValue<String> finalLoopCustom;
        public final ConfigValue<String> finalLinearCustom;
        public final ConfigValue<String> finalCommandCustom;

        Common(ForgeConfigSpec.Builder builder) {
            builder.
                    comment("Here you have the option to change which pairs of mobs are transformed by transformation powder." +
                            "\nThe format is \"modID:mobName-modID:mobName\", the same way as you would type it in game if you were to use something like the /summon command, you also have to type a dash in the middle that links mobs." +
                            "\nYou can link as many mobs as you like, let's say you wrote in \"minecraft:pig-minecraft:cow-minecraft:chicken\". A pig will transform into a cow, a cow into a chicken and a chicken back into a pig." +
                            "\nYou will notice that there are two input slots for mobs, the loop input works as described above, the linear input however, would if provided with the example from above, make it so that the last mob in the link will not be able to transform back into the first mob in the link." +
                            "\nThere are no linear transformations in the \"vanilla\" Twilight Forest mod, so the input is left empty, the loop input is setup to work as the original developers intended. If you want to undo any changes made to the config, delete this file.").
                    push("TRANSFORMATIONS");
            finalLoopCustom = builder.define("Transformations that loop: ", "twilightforest:minotaur-minecraft:zombified_piglin twilightforest:deer-minecraft:cow twilightforest:bighorn_sheep-minecraft:sheep twilightforest:wild_boar-minecraft:pig twilightforest:bunny-minecraft:rabbit twilightforest:tiny_bird-minecraft:parrot twilightforest:raven-minecraft:bat twilightforest:hostile_wolf-minecraft:wolf twilightforest:penguin-minecraft:chicken twilightforest:hedge_spider-minecraft:spider twilightforest:swarm_spider-minecraft:cave_spider twilightforest:wraith-minecraft:blaze twilightforest:redcap-minecraft:villager twilightforest:skeleton_druid-minecraft:witch");
            finalLinearCustom = builder.define("Transformations that are linear: ", "");
            builder.pop();
            builder.
                    comment("Here you can set what command gets executed when the final boss spawner is activated." +
                            "\nType in the command you want to happen the exact same way as you would in game." +
                            "\nFor example, typing \"summon minecraft:skeleton ~ ~ ~\" will summon a normal skeleton and typing \"summon zombie ~ ~ ~ {Invulnerable:1,HandItems:[{Count:1,id:wooden_sword},{}]}\" will summon an invincible zombie that is holding a wooden sword." +
                            "\nBy default, the spawner runs \"function twilighttweaks:final_boss_example\", which is a mcfunction made for the mod that runs several commands in a row. Mcfunctions are a vanilla feature and you can google how to make one of your own." +
                            "\nAny command can be ran once, after which the spawner will break itself.").
                    push("FINAL BOSS SPAWNER");
            finalCommandCustom = builder.define("The command that the spawner should run: ", "function twilighttweaks:final_boss_example");
        }
    }

    public static void refresh() {
        loopCustom = COMMON_CONFIG.finalLoopCustom.get();
        linearCustom = COMMON_CONFIG.finalLinearCustom.get();
        commandCustom = COMMON_CONFIG.finalCommandCustom.get();
    }
}