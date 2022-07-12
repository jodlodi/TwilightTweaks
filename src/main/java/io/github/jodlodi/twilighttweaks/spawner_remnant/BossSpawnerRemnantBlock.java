package io.github.jodlodi.twilighttweaks.spawner_remnant;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.SpecialMagicLogBlock;

import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
public class BossSpawnerRemnantBlock extends BaseEntityBlock {
    private static final VoxelShape WITH_HITBOX = Block.box(6, 6, 6, 10, 10, 10);
    private static final VoxelShape NO_HITBOX = Shapes.empty();

    public BossSpawnerRemnantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> container) {
        super.createBlockStateDefinition(container);
        container.add(SpecialMagicLogBlock.ACTIVE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant) {
            if (spawnerRemnant.reSpawnerTick == 0) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.is(TwilightTweaks.TIME_POWDER.get())) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    spawnerRemnant.reSpawnerTick++;

                    if (level.isClientSide) {
                        for (int i = 0; i < 30; i++) {
                            level.addParticle(ParticleTypes.CRIT,
                                    pos.getX() + level.random.nextFloat(),
                                    pos.getY() + level.random.nextFloat(),
                                    pos.getZ() + level.random.nextFloat(),
                                    0, 0, 0);
                        }
                    }
                } else if (level.isClientSide) player.displayClientMessage(new TranslatableComponent("remnant.twilighttweaks.use").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD), true);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, TwilightTweaks.BOSS_SPAWNER_REMNANT_ENTITY.get(), BossSpawnerRemnantBlockEntity::shtick);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return blockGetter.getBlockEntity(pos) instanceof BossSpawnerRemnantBlockEntity spawnerRemnant && !spawnerRemnant.active ? NO_HITBOX : WITH_HITBOX;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BossSpawnerRemnantBlockEntity(pos, state);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity) {
        return entity instanceof Player player && player.getAbilities().instabuild;
    }

    @Override
    protected void spawnDestroyParticles(Level level, Player player, BlockPos pos, BlockState state) { }
}
