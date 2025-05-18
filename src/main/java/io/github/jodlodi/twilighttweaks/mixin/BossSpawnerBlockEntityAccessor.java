package io.github.jodlodi.twilighttweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;

@Mixin(BossSpawnerBlockEntity.class)
public interface BossSpawnerBlockEntityAccessor {
    @Accessor(value = "spawnedBoss", remap = false)
    boolean getSpawnedBoss();

    @Accessor(value = "spawnedBoss", remap = false)
    void setSpawnedBoss(boolean value);
}
