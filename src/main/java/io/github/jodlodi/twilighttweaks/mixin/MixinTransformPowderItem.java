package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.ConfigSetup;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.item.ItemTFTransformPowder;

import java.util.Map;

@Mixin(value = ItemTFTransformPowder.class)
public abstract class MixinTransformPowderItem extends Item {
    @Redirect(method = "itemInteractionForEntity", at = @At(value = "FIELD" , target = "Ltwilightforest/item/ItemTFTransformPowder;transformMap:Ljava/util/Map;"))
    private Map<ResourceLocation, ResourceLocation> injected(ItemTFTransformPowder transformPowderItem) {
        return ConfigSetup.customTransformMap;
    }
}