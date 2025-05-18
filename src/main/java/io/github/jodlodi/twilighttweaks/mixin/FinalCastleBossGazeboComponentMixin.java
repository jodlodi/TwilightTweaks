package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.finalcastle.FinalCastleBossGazeboComponent;
import twilightforest.world.components.structures.finalcastle.StructureTFDecoratorCastle;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
@Mixin(FinalCastleBossGazeboComponent.class)
public abstract class FinalCastleBossGazeboComponentMixin extends TFStructureComponentOld {
    public FinalCastleBossGazeboComponentMixin(StructurePieceType piece, CompoundTag tag) {
        super(piece, tag);
    }

    @Inject(method = "addChildren", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void addChildrenMixin(StructurePiece parent, StructurePieceAccessor list, RandomSource rand, CallbackInfo ci) {
        if (TweakConfig.disableFlag) {
            ci.cancel();
            this.deco = new StructureTFDecoratorCastle();
            this.deco.blockState = TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get().defaultBlockState();
            this.deco.fenceState = TFBlocks.VIOLET_FORCE_FIELD.get().defaultBlockState();
        }
    }

    @Inject(method = "postProcess", at = @At(value = "HEAD"), remap = false)
    private void postProcessMixin(WorldGenLevel level, StructureManager manager, ChunkGenerator generator, RandomSource randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos, CallbackInfo ci) {
        if (TweakConfig.disableFlag) {
            this.placeBlock(level, TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get().defaultBlockState(), 10, 1, 10, sbb);
        }
    }
}
