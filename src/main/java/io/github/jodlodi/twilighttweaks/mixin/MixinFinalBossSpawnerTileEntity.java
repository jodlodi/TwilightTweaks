package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import twilightforest.client.particle.TFParticleType;
import twilightforest.tileentity.spawner.BossSpawnerTileEntity;
import twilightforest.tileentity.spawner.FinalBossSpawnerTileEntity;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("NullableProblems")
@Mixin(FinalBossSpawnerTileEntity.class)
public class MixinFinalBossSpawnerTileEntity extends BossSpawnerTileEntity<MobEntity> implements ICommandSource {

    protected MixinFinalBossSpawnerTileEntity(TileEntityType<?> type, EntityType<MobEntity> entityType) {
        super(type, entityType);
    }

    @Unique
    private final CommandBlockLogic commandBlock = new CommandBlockLogic() {
        public void setCommand(String command) {
            super.setCommand(command);
            MixinFinalBossSpawnerTileEntity.this.setChanged();
        }

        public ServerWorld getLevel() {
            return (ServerWorld)MixinFinalBossSpawnerTileEntity.this.level;
        }

        public void onUpdated() {
            BlockState blockstate = null;
            if (MixinFinalBossSpawnerTileEntity.this.level != null) {
                blockstate = MixinFinalBossSpawnerTileEntity.this.level.getBlockState(MixinFinalBossSpawnerTileEntity.this.worldPosition);
            }
            if (this.getLevel() != null) {
                this.getLevel().sendBlockUpdated(MixinFinalBossSpawnerTileEntity.this.worldPosition, blockstate, blockstate, 3);
            }
        }

        @OnlyIn(Dist.CLIENT)
        public Vector3d getPosition() {
            return Vector3d.atCenterOf(MixinFinalBossSpawnerTileEntity.this.worldPosition);
        }

        public CommandSource createCommandSourceStack() {
            return new CommandSource(MixinFinalBossSpawnerTileEntity.this, Vector3d.atCenterOf(MixinFinalBossSpawnerTileEntity.this.worldPosition), Vector2f.ZERO, Objects.requireNonNull(this.getLevel()), 2, this.getName().getString(), this.getName(), this.getLevel().getServer(), null);
        }
    };

    /**
     * @author jodlodi
     * @reason :)
     */
    @Overwrite
    public void tick() {
        if (!this.spawnedBoss && this.anyPlayerInRange() && this.level != null) {
            if (this.level.isClientSide) {
                for (int i = 0; i < 2; i++) {
                    double rx = ((float) this.worldPosition.getX() + this.level.random.nextFloat());
                    double ry = ((float) this.worldPosition.getY() + this.level.random.nextFloat());
                    double rz = ((float) this.worldPosition.getZ() + this.level.random.nextFloat());
                    this.level.addParticle(TFParticleType.ANNIHILATE.get(), rx, ry, rz, 0.0D, 0.0D, 0.0D);
                }
            } else if (this.spawnMyBoss((ServerWorld) this.level)) {
                this.level.destroyBlock(this.worldPosition, false);
                this.spawnedBoss = true;
            }
        }
    }

    @Unique
    protected boolean spawnMyBoss(IServerWorld world) {
        CommandBlockLogic commandblocklogic = this.commandBlock;
        if (this.level != null) {
            commandblocklogic.setCommand(TweakConfig.commandCustom);
            return commandblocklogic.performCommand(this.level);
        }
        return false;
    }

    public void sendMessage(ITextComponent iTextComponent, UUID uuid) {

    }

    public boolean acceptsSuccess() {
        return false;
    }

    public boolean acceptsFailure() {
        return false;
    }

    public boolean shouldInformAdmins() {
        return false;
    }
}
