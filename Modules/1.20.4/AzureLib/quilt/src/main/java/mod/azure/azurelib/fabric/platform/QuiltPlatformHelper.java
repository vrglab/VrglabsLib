package mod.azure.azurelib.fabric.platform;

import mod.azure.azurelib.common.internal.common.blocks.TickingLightBlock;
import mod.azure.azurelib.common.internal.common.blocks.TickingLightEntity;
import mod.azure.azurelib.common.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.Vrglab.AzureLib.quilt.VlAzureLibModQuilt;

import java.nio.file.Path;

public class QuiltPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public boolean isServerEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public TickingLightBlock getTickingLightBlock() {
        return VlAzureLibModQuilt.TICKING_LIGHT_BLOCK;
    }

    @Override
    public BlockEntityType<TickingLightEntity> getTickingLightEntity() {
        return VlAzureLibModQuilt.TICKING_LIGHT_ENTITY;
    }

    @Override
    public Enchantment getIncendairyenchament() {
        return VlAzureLibModQuilt.INCENDIARYENCHANTMENT;
    }

    @Override
    public Path modsDir() {
        return FabricLoader.getInstance().getGameDir().resolve("mods");
    }
}