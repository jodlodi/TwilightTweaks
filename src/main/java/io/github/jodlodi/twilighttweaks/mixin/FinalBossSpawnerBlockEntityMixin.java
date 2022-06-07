package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import twilightforest.block.entity.spawner.BossSpawnerBlockEntity;
import twilightforest.block.entity.spawner.FinalBossSpawnerBlockEntity;
import twilightforest.entity.boss.PlateauBoss;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@Mixin(FinalBossSpawnerBlockEntity.class)
public abstract class FinalBossSpawnerBlockEntityMixin extends BossSpawnerBlockEntity<PlateauBoss> implements GameMasterBlock {
    protected FinalBossSpawnerBlockEntityMixin(BlockEntityType<?> type, EntityType<PlateauBoss> entityType, BlockPos pos, BlockState state) {
        super(type, entityType, pos, state);
    }

    @Unique
    private final BaseCommandBlock commandBlock = new BaseCommandBlock() {
        @Override
        public void setCommand(String command) {
            super.setCommand(command);
            FinalBossSpawnerBlockEntityMixin.this.setChanged();
        }

        @Override
        public @NotNull ServerLevel getLevel() {
            return (ServerLevel)Objects.requireNonNull(FinalBossSpawnerBlockEntityMixin.this.level);
        }

        @Override
        public void onUpdated() {
            if (FinalBossSpawnerBlockEntityMixin.this.level != null) {
                BlockState blockstate = FinalBossSpawnerBlockEntityMixin.this.level.getBlockState(FinalBossSpawnerBlockEntityMixin.this.worldPosition);
                this.getLevel().sendBlockUpdated(FinalBossSpawnerBlockEntityMixin.this.worldPosition, blockstate, blockstate, 3);
            }
        }

        @Override
        public @NotNull Vec3 getPosition() {
            return Vec3.atCenterOf(FinalBossSpawnerBlockEntityMixin.this.worldPosition);
        }

        public @NotNull CommandSourceStack createCommandSourceStack() {
            return new CommandSourceStack(this, Vec3.atCenterOf(FinalBossSpawnerBlockEntityMixin.this.worldPosition), Vec2.ZERO, this.getLevel(), 2, this.getName().getString(), this.getName(), this.getLevel().getServer(), null);
        }
    };

    /**
     * @author jodlodi
     * @reason :)
     */
    @Overwrite(remap = false)
    protected boolean spawnMyBoss(ServerLevelAccessor levelAccessor) {
        BaseCommandBlock commandBlock = this.commandBlock;
        commandBlock.setCommand(TweakConfig.commandCustom);
        return commandBlock.performCommand(levelAccessor.getLevel());
    }
}