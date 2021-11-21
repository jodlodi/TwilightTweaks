package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.ConfigSetup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.item.TransformPowderItem;

import java.util.Map;

@Mixin(TransformPowderItem.class)
public abstract class MixinTransformPowderItem extends Item {
    //I know this can be OP, but it's meant for modpack creators to be able to set up exactly what turns into what
    public MixinTransformPowderItem(Properties properties) {
        super(properties);
    }

    @Redirect(method = "interactLivingEntity", at = @At(value = "FIELD" , target = "Ltwilightforest/item/TransformPowderItem;transformMap:Ljava/util/Map;"))
    private Map<EntityType<?>, EntityType<?>> injected(TransformPowderItem transformPowderItem) {
        return ConfigSetup.customTransformMap;
    }
}