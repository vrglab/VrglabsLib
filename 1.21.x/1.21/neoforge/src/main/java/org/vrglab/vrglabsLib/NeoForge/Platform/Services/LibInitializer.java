package org.vrglab.vrglabsLib.NeoForge.Platform.Services;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.AzureLibMod;
import org.vrglab.azure.azurelib.common.config.TestingConfig;
import org.vrglab.azure.azurelib.common.config.format.ConfigFormats;
import org.vrglab.azure.azurelib.common.config.io.ConfigIO;
import org.vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;
import org.vrglab.vrglabsLib.platform.services.ILibInitializer;

public class LibInitializer implements ILibInitializer {

    @Override
    public void LoadAzureLib(Object... Args) {
        IEventBus modEventBus = (IEventBus)Args[0];
        DATA_COMPONENTS_REGISTER.register(modEventBus);
        blockEntityTypeDeferredRegister.register(modEventBus);
        blockDeferredRegister.register(modEventBus);
        AzureLib.initialize();
        AzureLibMod.initRegistry();
        AzureLibMod.config = AzureLibMod.registerConfig(TestingConfig.class, ConfigFormats.json()).getConfigInstance();
        modEventBus.addListener(this::init);
        modEventBus.addListener(this::registerMessages);
    }


    /* HELPER METHODS */
    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            AzureLib.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
            BuiltInRegistries.BLOCK,
            AzureLib.MOD_ID
    );

    public static final DeferredRegister.DataComponents DATA_COMPONENTS_REGISTER = DeferredRegister
            .createDataComponents(
                    AzureLib.MOD_ID
            );



    private void init(final FMLCommonSetupEvent event) {
        ConfigIO.FILE_WATCH_MANAGER.startService();
    }

    public void registerMessages(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(AzureLib.MOD_ID);
        registrar.playBidirectional(
                AzEntityDispatchCommandPacket.TYPE,
                AzEntityDispatchCommandPacket.CODEC,
                (msg, ctx) -> msg.handle()
        );
        registrar.playBidirectional(
                AzItemStackDispatchCommandPacket.TYPE,
                AzItemStackDispatchCommandPacket.CODEC,
                (msg, ctx) -> msg.handle()
        );
        registrar.playBidirectional(
                AzBlockEntityDispatchCommandPacket.TYPE,
                AzBlockEntityDispatchCommandPacket.CODEC,
                (msg, ctx) -> msg.handle()
        );
        registrar.playBidirectional(
                SendConfigDataPacket.TYPE,
                SendConfigDataPacket.CODEC,
                (msg, ctx) -> msg.handle()
        );
    }
}
