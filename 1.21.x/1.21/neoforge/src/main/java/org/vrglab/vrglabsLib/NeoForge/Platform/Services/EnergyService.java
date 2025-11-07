package org.vrglab.vrglabsLib.NeoForge.Platform.Services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.vrglab.vrglabsLib.Utils.ReflectionUtil;
import org.vrglab.vrglabsLib.api.energy.EnergyContainer;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergyContainer;
import org.vrglab.vrglabsLib.platform.services.energy.IEnergyService;

public class EnergyService implements IEnergyService {
    @Override
    public Object CreateContainerInstance(long capacity, long maxTransfer, long maxEnergy, long energy, IEnergyContainer safeContainer, Object rawBlockEntity) {
        return new EnergyStorage((int)capacity, (int)maxTransfer, (int)maxEnergy, (int)energy);
    }

    @Override
    public Long ExtractEnergyFromContainer(Object rawEnergyContainer, long maxExtract, boolean simulate) {
        EnergyStorage  energyStorage = (EnergyStorage)rawEnergyContainer;

        return (long)energyStorage.extractEnergy((int)maxExtract, simulate);
    }

    @Override
    public Long GiveEnergyToContainer(Object rawEnergyContainer, long maxReceive, boolean simulate) {
        return 0L;
    }

    @Override
    public boolean EntityHasEnergyCapabilities(BlockEntity entity) {
        if(entity == null){
            return false;
        }

        IEnergyStorage storage = entity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK, entity.getBlockPos(), null);
        if(storage != null){
            return true;
        }

        return false;
    }

    @Override
    public EnergyContainer WrapExternalStorage(Level level, BlockPos pos, Direction facing, BlockEntity blockEntity) {
        IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, pos, facing);
        return EnergyContainer.ExternalContainerBuilder.Open()
                .maxReceive(ReflectionUtil.getField(storage, "maxReceive", int.class))
                .maxExtract(ReflectionUtil.getField(storage, "maxExtract", int.class))
                .capacity(ReflectionUtil.getField(storage, "capacity", int.class))
                .energy(ReflectionUtil.getField(storage, "energy", int.class))
                .rawLoaderDependentContainer(storage)
                .rawBlockEntity(blockEntity)
                .Build();
    }
}
