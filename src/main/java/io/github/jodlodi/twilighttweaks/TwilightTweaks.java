package io.github.jodlodi.twilighttweaks;

import com.mojang.logging.LogUtils;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlock;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.CreativeModeTabRegistry;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFItems;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(TwilightTweaks.MOD_ID)
@SuppressWarnings("ConstantConditions")
@ParametersAreNonnullByDefault
public class TwilightTweaks {
    public static final String MOD_ID = "twilighttweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.createBlocks(TwilightTweaks.MOD_ID);
    public static final DeferredHolder<Block, BossSpawnerRemnantBlock> BOSS_SPAWNER_REMNANT = BLOCK_REGISTRY.register("boss_spawner_remnant", () ->
            new BossSpawnerRemnantBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.8F).noOcclusion().noLootTable().noCollission()));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TwilightTweaks.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BossSpawnerRemnantBlockEntity>> BOSS_SPAWNER_REMNANT_ENTITY = BLOCK_ENTITY_REGISTRY.register("boss_spawner_remnant_entity", () ->
            BlockEntityType.Builder.of(BossSpawnerRemnantBlockEntity::new, BOSS_SPAWNER_REMNANT.get()).build(null));

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.createItems(TwilightTweaks.MOD_ID);
    public static final DeferredHolder<Item, Item> TIME_POWDER = ITEM_REGISTRY.register("time_powder", () -> new Item(new Item.Properties()));

    public TwilightTweaks(IEventBus bus, ModContainer modContainer) {
        BLOCK_REGISTRY.register(bus);
        BLOCK_ENTITY_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);

        bus.addListener(this::registerTFBlocksTab);

        modContainer.registerConfig(ModConfig.Type.COMMON, TweakConfig.SPEC);
    }

    public void registerTFBlocksTab(BuildCreativeModeTabContentsEvent event) {
        ResourceLocation location = CreativeModeTabRegistry.getName(event.getTab());
        if (location != null && location.equals(TwilightForestMod.prefix("items"))) {
            event.insertAfter(TFItems.TRANSFORMATION_POWDER.toStack(), TIME_POWDER.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}