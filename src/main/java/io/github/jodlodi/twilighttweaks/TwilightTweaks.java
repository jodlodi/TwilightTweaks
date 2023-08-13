package io.github.jodlodi.twilighttweaks;

import com.mojang.logging.LogUtils;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlock;
import io.github.jodlodi.twilighttweaks.spawner_remnant.BossSpawnerRemnantBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.CreativeModeTabRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import twilightforest.TwilightForestMod;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod(TwilightTweaks.MOD_ID)
@SuppressWarnings("ConstantConditions")
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(modid = TwilightTweaks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TwilightTweaks
{
    public static final String MOD_ID = "twilighttweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TwilightTweaks.MOD_ID);
    public static final RegistryObject<Block> BOSS_SPAWNER_REMNANT = BLOCK_REGISTRY.register("boss_spawner_remnant", () ->
            new BossSpawnerRemnantBlock(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.8F).noOcclusion().noLootTable().noCollission()));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TwilightTweaks.MOD_ID);
    public static final RegistryObject<BlockEntityType<BossSpawnerRemnantBlockEntity>> BOSS_SPAWNER_REMNANT_ENTITY = BLOCK_ENTITY_REGISTRY.register("boss_spawner_remnant_entity", () ->
            BlockEntityType.Builder.of(BossSpawnerRemnantBlockEntity::new, BOSS_SPAWNER_REMNANT.get()).build(null));

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TwilightTweaks.MOD_ID);
    public static final RegistryObject<Item> TIME_POWDER = ITEM_REGISTRY.register("time_powder", () -> new Item(new Item.Properties()));

    public TwilightTweaks() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        BLOCK_REGISTRY.register(bus);
        BLOCK_ENTITY_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);

        bus.addListener(this::configSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TweakConfig.COMMON_SPEC);
    }

    @SubscribeEvent
    public static void registerTFBlocksTab(CreativeModeTabEvent.BuildContents event) {
        ResourceLocation location = CreativeModeTabRegistry.getName(event.getTab());
        if (location != null && location.equals(TwilightForestMod.prefix("items"))) {
            event.accept(TIME_POWDER);
        }
    }

    private void configSetup(final FMLCommonSetupEvent event) {
        TweakConfig.refresh();
    }
}