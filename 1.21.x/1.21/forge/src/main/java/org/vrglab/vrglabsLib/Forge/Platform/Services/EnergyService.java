package org.vrglab.vrglabsLib.Forge.Platform.Services;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
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
        EnergyStorage  energyStorage = (EnergyStorage)rawEnergyContainer;

        return (long)energyStorage.receiveEnergy((int)maxReceive, simulate);
    }

    @Override
    public boolean EntityHasEnergyCapabilities(BlockEntity entity) {
        if(entity == null){
            return false;
        }

        if(entity.getCapability(ForgeCapabilities.ENERGY).isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public EnergyContainer WrapExternalStorage(Level level, BlockPos pos, Direction facing, BlockEntity blockEntity) {
        IEnergyStorage storage = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve().get();

        return EnergyContainer.ExternalContainerBuilder.Open()
                .maxReceive(ReflectionUtil.getField(storage, "maxReceive", int.class))
                .maxExtract(ReflectionUtil.getField(storage, "maxExtract", int.class))
                .capacity(storage.getMaxEnergyStored())
                .energy(storage.getEnergyStored())
                .rawLoaderDependentContainer(storage)
                .rawBlockEntity(blockEntity)
                .Build();
    }
}
