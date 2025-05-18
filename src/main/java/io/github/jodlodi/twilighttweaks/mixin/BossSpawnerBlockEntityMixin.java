package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;
import twilightforest.entity.EnforcedHomePoint;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mixin(BossSpawnerBlockEntity.class)
public abstract class BossSpawnerBlockEntityMixin<T extends Mob & EnforcedHomePoint> extends BlockEntity {
    public BossSpawnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @SuppressWarnings("DiscouragedShift")
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;destroyBlock(Lnet/minecraft/core/BlockPos;Z)Z", shift = At.Shift.BEFORE), cancellable = true, remap = false)
    private static void tickMixin(Level level, BlockPos pos, BlockState state, BossSpawnerBlockEntity<?> te, CallbackInfo ci) {
        if (TweakConfig.remnantFlag) {
            ci.cancel();
            ((BossSpawnerBlockEntityAccessor)te).setSpawnedBoss(true);
            level.setBlock(pos, TwilightTweaks.BOSS_SPAWNER_REMNANT.get().defaultBlockState(), Block.UPDATE_ALL);
            if (level.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant) {
                spawnerRemnant.setSpawnerLocation(Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(state.getBlock())).toString());
            }
        }
    }
}
