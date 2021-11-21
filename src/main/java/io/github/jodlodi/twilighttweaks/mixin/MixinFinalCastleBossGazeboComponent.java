package io.github.jodlodi.twilighttweaks.mixin;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.finalcastle.FinalCastleBossGazeboComponent;

import java.util.Random;

@Mixin(FinalCastleBossGazeboComponent.class)
public class MixinFinalCastleBossGazeboComponent extends TFStructureComponentOld {
    public MixinFinalCastleBossGazeboComponent(IStructurePieceType piece, CompoundNBT nbt) {
        super(piece, nbt);
    }

    /**
     * @author jodlodi
     * @reason Would be impossible to get to the final boss with the barrier still there
     */
    @Overwrite
    public boolean postProcess(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "You win!", true, 2.0F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "You can join the Twilight Forest Discord server to follow", true, 1.3F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "the latest updates on this castle and other content at:", true, 1.0F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "discord.experiment115.com", true, 0.7F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "And be nice!", true, 0.4F);
        this.placeBlock(world, (TFBlocks.boss_spawner_final_boss.get()).defaultBlockState(), 10, 1, 10, sbb);
        return true;
    }
}