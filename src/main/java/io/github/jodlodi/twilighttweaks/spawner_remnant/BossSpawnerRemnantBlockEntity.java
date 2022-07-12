package io.github.jodlodi.twilighttweaks.spawner_remnant;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.data.EntityTagGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BossSpawnerRemnantBlockEntity extends BlockEntity {
    private String spawnerLocation;
    public boolean active = false;
    public int reSpawnerTick = 0;

    public BossSpawnerRemnantBlockEntity(BlockPos pos, BlockState state) {
        super(TwilightTweaks.BOSS_SPAWNER_REMNANT_ENTITY.get(), pos, state);
    }

    public void setSpawnerLocation(String spawnerLocation) {
        this.spawnerLocation = spawnerLocation;
    }

    public @Nullable Block getSpawnerBlock() {
        if (this.spawnerLocation != null) {
            return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(spawnerLocation));
        } else return null;
    }

    @Override
    public @Nonnull CompoundTag save(CompoundTag compoundTag) {
        compoundTag.putString("spawnerLocation", this.spawnerLocation);
        compoundTag.putBoolean("active", this.active);
        compoundTag.putInt("reSpawnerTick", this.reSpawnerTick);
        return super.save(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.spawnerLocation = compoundTag.getString("spawnerLocation");
        this.active = compoundTag.getBoolean("active");
        this.reSpawnerTick = compoundTag.getInt("reSpawnerTick");
    }

    public static void shtick(Level level, BlockPos pos, BlockState state, BossSpawnerRemnantBlockEntity te) {
        if (te.active) {
            if (te.reSpawnerTick > 0) te.reSpawnerTick++;
            if (level.isClientSide) {
                int tick = Math.min(100, te.reSpawnerTick);
                double distance = (double)tick * 0.01D * 0.7D;

                Vec3 center = Vec3.atCenterOf(pos);

                double maxX = center.x + distance;
                double minX = center.x - distance;

                double maxY = center.y + distance;
                double minY = center.y - distance;

                double maxZ = center.z + distance;
                double minZ = center.z - distance;

                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), maxY, maxZ);
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), maxY, minZ);
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), minY, maxZ);
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), minY, minZ);

                addRandomStationaryParticle(level, tick, maxX, randomOffset(level.random, center.y, distance), maxZ);
                addRandomStationaryParticle(level, tick, maxX, randomOffset(level.random, center.y, distance), minZ);
                addRandomStationaryParticle(level, tick, minX, randomOffset(level.random, center.y, distance), maxZ);
                addRandomStationaryParticle(level, tick, minX, randomOffset(level.random, center.y, distance), minZ);

                addRandomStationaryParticle(level, tick, maxX, maxY, randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, maxX, minY, randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, minX, maxY, randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, minX, minY, randomOffset(level.random, center.z, distance));

                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), randomOffset(level.random, center.y, distance), randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), randomOffset(level.random, center.y, distance), randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), randomOffset(level.random, center.y, distance), randomOffset(level.random, center.z, distance));
                addRandomStationaryParticle(level, tick, randomOffset(level.random, center.x, distance), randomOffset(level.random, center.y, distance), randomOffset(level.random, center.z, distance));
            } else if (te.reSpawnerTick >= 120) {
                Block spawner = te.getSpawnerBlock();
                if (spawner != null) {
                    level.setBlockAndUpdate(pos, Block.pushEntitiesUp(state, spawner.defaultBlockState(), level, pos));
                    level.destroyBlock(pos.below(), true);
                }
            }
        } else if (level.getEntitiesOfClass(Mob.class, AABB.ofSize(Vec3.atCenterOf(pos), 40, 40 ,40), entity -> entity.getType().is(EntityTagGenerator.BOSSES)).isEmpty()) {
            te.active = true;
        }
    }

    private static void addRandomStationaryParticle(Level level, int chance, double x, double y, double z) {
        if (level.random.nextInt(100 - Math.min(99, chance)) <= chance) {
            level.addParticle(ParticleTypes.WAX_OFF, x, y, z, 0, 0, 0);
            if (level.random.nextBoolean()) level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
        }
    }

    private static double randomOffset(Random random, double point, double distance) {
        return point + random.nextDouble() * distance * (random.nextBoolean() ? 1 : -1);
    }
}
