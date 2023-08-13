package io.github.jodlodi.twilighttweaks;

import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlockEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;
import twilightforest.block.entity.spawner.FinalBossSpawnerBlockEntity;
import twilightforest.init.TFBlocks;
import twilightforest.world.components.structures.finalcastle.FinalCastleBossGazeboComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

@ParametersAreNonnullByDefault
@SuppressWarnings({"JavadocReference", "unused", "RedundantSuppression", "deprecation"})
public class ASMHooks {
    /**
     * Injection Point:<br>
     * {@link BossSpawnerBlockEntity#tick(Level, BlockPos, BlockState, BossSpawnerBlockEntity)}<br>
     * [AFTER PUTFIELD]
     */
    public static void bossSpawner(Level level, BlockPos pos, BlockState state) {
        if (TweakConfig.remnantFlag) {
            level.setBlock(pos, TwilightTweaks.BOSS_SPAWNER_REMNANT.get().defaultBlockState(), 2);
            if (level.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant) {
                spawnerRemnant.setSpawnerLocation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).toString());
            }
        }
    }

    /**
     * Injection Point:<br>
     * {@link FinalBossSpawnerBlockEntity#spawnMyBoss(ServerLevelAccessor)}<br>
     * [BEFORE ICONST_0]
     */
    public static boolean finalSpawner(BlockPos pos, ServerLevelAccessor levelAccessor) {
        BaseCommandBlock commandBlock = new BaseCommandBlock() {
            @Override
            public @NotNull ServerLevel getLevel() {
                return levelAccessor.getLevel();
            }

            @Override
            public void onUpdated() {
                //Jack shit
            }

            @Override
            public @NotNull Vec3 getPosition() {
                return Vec3.atCenterOf(pos);
            }

            @Override
            public @NotNull CommandSourceStack createCommandSourceStack() {
                return new CommandSourceStack(this, this.getPosition(), Vec2.ZERO, this.getLevel(), 2, this.getName().getString(), this.getName(), this.getLevel().getServer(), null);
            }

            @Override
            public boolean shouldInformAdmins() {
                return false; //We don't want admins (or single-player players) to be informed of commands going off each time the final spawner goes off
            }

            @Override
            public boolean isValid() {
                return true;
            }
        };

        commandBlock.setCommand(TweakConfig.commandCustom);
        return commandBlock.performCommand(levelAccessor.getLevel());
    }

    /**
     * Injection Point:<br>
     * {@link FinalCastleBossGazeboComponent#postProcess(WorldGenLevel, StructureFeatureManager, ChunkGenerator, Random, BoundingBox, ChunkPos, BlockPos)}<br>
     * [BEFORE GETSTATIC]
     */
    public static void gazebo(StructurePiece structurePiece, WorldGenLevel level, BoundingBox sbb) {
        structurePiece.placeBlock(level, TFBlocks.FINAL_BOSS_BOSS_SPAWNER.get().defaultBlockState(), 10, 1, 10, sbb);
    }
}
