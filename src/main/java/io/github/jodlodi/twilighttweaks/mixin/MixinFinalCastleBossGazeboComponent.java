package io.github.jodlodi.twilighttweaks.mixin;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import twilightforest.block.BlockTFBossSpawner;
import twilightforest.block.TFBlocks;
import twilightforest.enums.BossVariant;
import twilightforest.structures.StructureTFComponentOld;
import twilightforest.structures.finalcastle.ComponentTFFinalCastleBossGazebo;

import java.util.Random;

@Mixin(value = ComponentTFFinalCastleBossGazebo.class)
public class MixinFinalCastleBossGazeboComponent extends StructureTFComponentOld {
    /**
     * @author jodlodi
     * @reason Would be impossible to get to the final boss with the barrier still there
    */
    @Overwrite
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox sbb) {
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "You win!", true, 2.0F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "You can join the Twilight Forest Discord server to follow", true, 1.3F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "the latest updates on this castle and other content at:", true, 1.0F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "discord.experiment115.com", true, 0.7F);
        this.setInvisibleTextEntity(world, 10, 0, 10, sbb, "And be nice!", true, 0.4F);
        this.setBlockState(world, TFBlocks.boss_spawner.getDefaultState().withProperty(BlockTFBossSpawner.VARIANT, BossVariant.FINAL_BOSS), 10, 1, 10, sbb);
        return true;
    }
}