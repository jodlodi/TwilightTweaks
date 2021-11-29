package io.github.jodlodi.twilighttweaks.mixin;

import io.github.jodlodi.twilighttweaks.TweakConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import twilightforest.client.particle.ParticleAnnihilate;
import twilightforest.tileentity.spawner.TileEntityTFBossSpawner;
import twilightforest.tileentity.spawner.TileEntityTFFinalBossSpawner;

@SuppressWarnings("NullableProblems")
@Mixin(TileEntityTFFinalBossSpawner.class)
public class MixinFinalBossSpawnerTileEntity extends TileEntityTFBossSpawner {

    protected MixinFinalBossSpawnerTileEntity(ResourceLocation mobID) {
        super(mobID);
    }

    @Unique
    private final CommandBlockBaseLogic commandBlock = new CommandBlockBaseLogic() {
        public void setCommand(String command) {
            super.setCommand(command);
            MixinFinalBossSpawnerTileEntity.this.markDirty();
        }

        public MinecraftServer getServer() {
            return MixinFinalBossSpawnerTileEntity.this.world.getMinecraftServer();
        }

        public void updateCommand() {
            IBlockState iblockstate = MixinFinalBossSpawnerTileEntity.this.world.getBlockState(MixinFinalBossSpawnerTileEntity.this.pos);
            MixinFinalBossSpawnerTileEntity.this.getWorld().notifyBlockUpdate(MixinFinalBossSpawnerTileEntity.this.pos, iblockstate, iblockstate, 3);
        }

        public BlockPos getPosition() {
            return MixinFinalBossSpawnerTileEntity.this.pos;
        }

        public Vec3d getPositionVector() {
            return new Vec3d((double)MixinFinalBossSpawnerTileEntity.this.pos.getX() + 0.5D, (double)MixinFinalBossSpawnerTileEntity.this.pos.getY() + 0.5D, (double)MixinFinalBossSpawnerTileEntity.this.pos.getZ() + 0.5D);
        }

        public World getEntityWorld() {
            return MixinFinalBossSpawnerTileEntity.this.getWorld();
        }

        @SideOnly(Side.CLIENT)
        public int getCommandBlockType() {
            return 0;
        }

        @SideOnly(Side.CLIENT)
        public void fillInInfo(ByteBuf buf) {
            buf.writeInt(MixinFinalBossSpawnerTileEntity.this.pos.getX());
            buf.writeInt(MixinFinalBossSpawnerTileEntity.this.pos.getY());
            buf.writeInt(MixinFinalBossSpawnerTileEntity.this.pos.getZ());
        }

        public boolean shouldTrackOutput() {
            return false;
        }
    };

    @Unique
    public void update() {
        if (!this.spawnedBoss && this.anyPlayerInRange()) {
            if (this.world.isRemote) {
                for (int i = 0; i < 2; i++) {
                    double rx = ((float)this.pos.getX() + this.world.rand.nextFloat());
                    double ry = ((float)this.pos.getY() + this.world.rand.nextFloat());
                    double rz = ((float)this.pos.getZ() + this.world.rand.nextFloat());
                    new ParticleAnnihilate(this.world, rx, ry, rz, 0, 0, 0);
                }
            } else if (this.spawnMyBoss()) {
                this.world.destroyBlock(this.pos, false);
                this.spawnedBoss = true;
            }
        }
    }

    @Unique
    protected boolean spawnMyBoss() {
        if (this.world != null) {
            this.commandBlock.setCommand(TweakConfig.commandCustom);
            return this.commandBlock.trigger(this.world);
        }
        return false;
    }
}