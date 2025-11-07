package org.vrglab.vrglabsLib.platform.services.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.vrglab.vrglabsLib.api.energy.EnergyContainer;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergyContainer;

public interface IEnergyService {

    Object CreateContainerInstance(long capacity, long maxTransfer, long maxEnergy, long energy, IEnergyContainer safeContainer, Object rawBlockEntity);
    Long ExtractEnergyFromContainer(Object rawEnergyContainer, long maxExtract, boolean simulate);
    Long GiveEnergyToContainer(Object rawEnergyContainer, long maxReceive, boolean simulate);
    boolean EntityHasEnergyCapabilities(BlockEntity entity);
    EnergyContainer WrapExternalStorage(Level level, BlockPos pos, Direction facing, BlockEntity blockEntity);
}
