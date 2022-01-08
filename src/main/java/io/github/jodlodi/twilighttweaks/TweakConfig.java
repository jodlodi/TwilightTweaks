package io.github.jodlodi.twilighttweaks;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class TweakConfig {
    public static Configuration CONFIG;

    public static void init(File file) {
        CONFIG = new Configuration(file);
        String category;

        category = "TRANSFORMATIONS";
        CONFIG.addCustomCategoryComment(category, "Here you have the option to change which pairs of mobs are transformed by transformation powder." +
                "\nThe format is \"modID:mobName-modID:mobName\", the same way as you would type it in game if you were to use something like the /summon command, you also have to type a dash in the middle that links mobs." +
                "\nYou can link as many mobs as you like, let's say you wrote in \"minecraft:pig-minecraft:cow-minecraft:chicken\". A pig will transform into a cow, a cow into a chicken and a chicken back into a pig." +
                "\nYou will notice that there are two input slots for mobs, the loop input works as described above, the linear input however, would if provided with the example from above, make it so that the last mob in the link will not be able to transform back into the first mob in the link." +
                "\nThere are no linear transformations in the \"vanilla\" Twilight Forest mod, so the input is left empty, the loop input is setup to work as the original developers intended. If you want to undo any changes made to the config, delete this file.");
        loopCustom = CONFIG.getString("Transformations that loop: ", category, "twilightforest:minotaur-minecraft:zombie_pigman twilightforest:deer-minecraft:cow twilightforest:bighorn_sheep-minecraft:sheep twilightforest:wild_boar-minecraft:pig twilightforest:bunny-minecraft:rabbit twilightforest:tiny_bird-minecraft:parrot twilightforest:raven-minecraft:bat twilightforest:hostile_wolf-minecraft:wolf twilightforest:penguin-minecraft:chicken twilightforest:hedge_spider-minecraft:spider twilightforest:swarm_spider-minecraft:cave_spider twilightforest:wraith-minecraft:blaze twilightforest:redcap-minecraft:villager twilightforest:skeleton_druid-minecraft:witch", "");
        linearCustom = CONFIG.getString("Transformations that are linear: ", category, "", "");

        category = "FINAL BOSS SPAWNER";
        CONFIG.addCustomCategoryComment(category, "Here you can set what command gets executed when the final boss spawner is activated." +
                "\nType in the command you want to happen the exact same way as you would in game." +
                "\nFor example, typing \"summon minecraft:skeleton ~ ~ ~\" will summon a normal skeleton and typing \"summon zombie ~ ~ ~ {Invulnerable:1,HandItems:[{Count:1,id:wooden_sword},{}]}\" will summon an invincible zombie that is holding a wooden sword." +
                "\nBy default, the spawner runs \"function twilighttweaks:final_boss_example\", which is a mcfunction made for the mod that runs several commands in a row. Mcfunctions are a vanilla feature and you can google how to make one of your own." +
                "\nAny command can be ran once, after which the spawner will break itself.");
        commandCustom = CONFIG.getString("The command that the spawner should run: ", category, "summon minecraft:husk ~ ~-1 ~ {PersistenceRequired:1,CanBreakDoors:1b,HandItems:[{Count:1,id:\"twilightforest:knightmetal_sword\",tag:{ench:[{id:34,lvl:9}]}},{}],ArmorItems:[{Count:1,id:\"twilightforest:yeti_boots\",tag:{ench:[{id:2,lvl:9},{id:3,lvl:1},{id:8,lvl:3},{id:34,lvl:9},{id:71,lvl:1}]}},{Count:1,id:\"twilightforest:knightmetal_leggings\",tag:{ench:[{id:4,lvl:1},{id:34,lvl:9},{id:71,lvl:1}]}},{Count:1,id:\"twilightforest:knightmetal_chestplate\",tag:{ench:[{id:1,lvl:1},{id:7,lvl:1},{id:34,lvl:9},{id:71,lvl:1}]}},{id:skull,Damage:3,Count:1b,tag:{SkullOwner:{Id:\"fd6ba3aa-1348-42c6-9819-32e377bcfedf\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWFmYzlhOGFhMjhiNTBmZjM0MjlkMTUxZmJkNjhmNmY3NWVmNDlkNmQ0ODM5MDRhNDFhZDU3ODllMjA1M2Y3In19fQ==\"}]}}}}],CustomName:\"Elder Lich\",HandDropChances:[0.0f,0.0f],ArmorDropChances:[0.0f,0.0f,0.0f,0.0f],ActiveEffects:[{Id:1,Amplifier:2,Duration:999999},{Id:5,Amplifier:4,Duration:999999},{Id:12,Amplifier:1,Duration:999999},{Id:14,Amplifier:1,Duration:999999}],Attributes:[{Name:generic.maxHealth,Base:200},{Name:\"generic.movementSpeed\",Base:0.4f},{Name:\"generic.knockbackResistance\",Base:0.5f}],Health:200.0f}", "");

        CONFIG.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event) {
        TwilightTweaks.config = new File(event.getModConfigurationDirectory() + "/" + TwilightTweaks.MODID);
        TwilightTweaks.config.mkdirs();
        init(new File(TwilightTweaks.config.getPath(), TwilightTweaks.MODID + ".cfg"));
    }

    public static String loopCustom;
    public static String linearCustom;
    public static String commandCustom;
}