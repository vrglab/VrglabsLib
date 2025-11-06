package org.Vrglab.VrglabsLib.Forge.Platform.Services;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.AzureLibMod;
import org.Vrglab.azure.azurelib.common.config.TestingConfig;
import org.Vrglab.azure.azurelib.common.config.format.ConfigFormats;
import org.Vrglab.azure.azurelib.common.config.io.ConfigIO;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.Vrglab.VrglabsLib.platform.services.ILibInitializer;

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
    }


    /* HELPER METHODS */
    public static DeferredRegister<BlockEntityType<?>> blockEntityTypeDeferredRegister = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES,
            AzureLib.MOD_ID
    );

    public static DeferredRegister<Block> blockDeferredRegister = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            AzureLib.MOD_ID
    );

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS_REGISTER = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AzureLib.MOD_ID);



    private void init(final FMLCommonSetupEvent event) {
        ConfigIO.FILE_WATCH_MANAGER.startService();
    }
}
