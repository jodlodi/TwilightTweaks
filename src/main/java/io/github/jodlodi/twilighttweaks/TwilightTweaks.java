package io.github.jodlodi.twilighttweaks;

import com.mojang.logging.LogUtils;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlock;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static twilightforest.item.TFItems.defaultBuilder;

@Mod(TwilightTweaks.MOD_ID)
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = TwilightTweaks.MOD_ID)
public class TwilightTweaks
{
    public static final String MOD_ID = "twilighttweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TwilightTweaks.MOD_ID);

    public static final RegistryObject<Block> BOSS_SPAWNER_REMNANT = BLOCK_REGISTRY.register("boss_spawner_remnant", () ->
            new BossSpawnerRemnantBlock(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.8F).noOcclusion().noDrops().noCollission()));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, TwilightTweaks.MOD_ID);

    public static final RegistryObject<BlockEntityType<BossSpawnerRemnantBlockEntity>> BOSS_SPAWNER_REMNANT_ENTITY = BLOCK_ENTITY_REGISTRY.register("boss_spawner_remnant_entity", () ->
            BlockEntityType.Builder.of(BossSpawnerRemnantBlockEntity::new, BOSS_SPAWNER_REMNANT.get()).build(null));

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TwilightTweaks.MOD_ID);

    public static final RegistryObject<Item> TIME_POWDER = ITEM_REGISTRY.register("time_powder", () ->
            new Item(defaultBuilder()));

    public TwilightTweaks() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        BLOCK_REGISTRY.register(bus);
        BLOCK_ENTITY_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);

        bus.addListener(this::configSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TweakConfig.COMMON_SPEC);
    }

    private void configSetup(final FMLCommonSetupEvent event) {
        TweakConfig.refresh();
    }
}