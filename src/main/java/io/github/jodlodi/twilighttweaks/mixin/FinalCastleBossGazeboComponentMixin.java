package io.github.jodlodi.twilighttweaks.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.block.TFBlocks;
import twilightforest.world.components.structures.TFStructureComponentOld;
import twilightforest.world.components.structures.finalcastle.FinalCastleBossGazeboComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@Mixin(FinalCastleBossGazeboComponent.class)
public abstract class FinalCastleBossGazeboComponentMixin extends TFStructureComponentOld {
    public FinalCastleBossGazeboComponentMixin(StructurePieceType piece, CompoundTag nbt) {
        super(piece, nbt);
    }

    /**
     * @author jodlodi
     * @reason Would be impossible to get to the final boss with the barrier still there
    */
    @Overwrite
    public void postProcess(WorldGenLevel level, StructureFeatureManager manager, ChunkGenerator generator, Random randomIn, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
        this.setInvisibleTextEntity(level, 10, 0, 10, sbb, "You win!", true, 2.0F);
        this.setInvisibleTextEntity(level, 10, 0, 10, sbb, "You can join the Twilight Forest Discord server to follow", true, 1.3F);
        this.setInvisibleTextEntity(level, 10, 0, 10, sbb, "the latest updates on this castle and other content at:", true, 1.0F);
        this.setInvisibleTextEntity(level, 10, 0, 10, sbb, "discord.experiment115.com", true, 0.7F);
        this.setInvisibleTextEntity(level, 10, 0, 10, sbb, "And be nice!", true, 0.4F);

        this.placeBlock(level, TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get().defaultBlockState(), 10, 1, 10, sbb);
    }
}