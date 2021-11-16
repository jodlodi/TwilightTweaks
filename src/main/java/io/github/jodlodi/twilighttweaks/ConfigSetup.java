package io.github.jodlodi.twilighttweaks;

import net.minecraft.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class ConfigSetup {
    public static final Map<EntityType<?>, EntityType<?>> customTransformMap = new HashMap();

    public static void addCustomInitTransformations(String[] inputStrings) {
        //Example: minecraft:pig - minecraft:pig - minecraft:pig - minecraft:pig - minecraft:pig
        for (int b = 0; b < inputStrings.length; b++) {
            String[] splitString = inputStrings[b].split(" ");
            for (String s : splitString) {
                String[] splitSplit = s.split("-");
                if (splitSplit.length > 1) {
                    for (int o = 0; o < splitSplit.length - 1; o++) {
                        addCustomTransformation(splitSplit[o], splitSplit[o + 1]);
                    }
                    if (b == 0) {
                        addCustomTransformation(splitSplit[splitSplit.length - 1], splitSplit[0]);
                    }
                }
            }
        }
    }

    private static void addCustomTransformation(String from, String to){
        customTransformMap.put(EntityType.byString(from).get(), EntityType.byString(to).get());
    }
}
