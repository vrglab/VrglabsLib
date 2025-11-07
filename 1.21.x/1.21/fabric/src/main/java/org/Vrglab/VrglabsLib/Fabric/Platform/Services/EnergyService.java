package org.vrglab.vrglabsLib.Fabric.Platform.Services;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.vrglab.TeamReborn.energy.api.EnergyStorage;
import org.vrglab.TeamReborn.energy.api.base.SimpleEnergyStorage;
import org.vrglab.vrglabsLib.api.energy.EnergyContainer;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergyContainer;
import org.vrglab.vrglabsLib.platform.services.energy.IEnergyService;

public class EnergyService implements IEnergyService {
    @Override
    public Object CreateContainerInstance(long capacity, long maxTransfer, long maxEnergy, long energy, IEnergyContainer safeContainer, Object rawBlockEntity) {
        return new SimpleEnergyStorage(capacity, maxTransfer, maxEnergy);
    }

    @Override
    public Long ExtractEnergyFromContainer(Object rawEnergyContainer, long maxExtract, boolean simulate) {
        SimpleEnergyStorage storage = (SimpleEnergyStorage)rawEnergyContainer;
        try(Transaction openTransaction = Transaction.openOuter()) {
            long result = storage.extract(maxExtract, openTransaction);
            openTransaction.commit();
            return result;
        }
    }

    @Override
    public Long GiveEnergyToContainer(Object rawEnergyContainer, long maxReceive, boolean simulate) {
        SimpleEnergyStorage storage = (SimpleEnergyStorage)rawEnergyContainer;
        try(Transaction openTransaction = Transaction.openOuter()) {
            long result = storage.insert(maxReceive, openTransaction);
            openTransaction.commit();
            return result;
        }
    }

    @Override
    public boolean EntityHasEnergyCapabilities(BlockEntity entity) {
        if(entity == null){
            return false;
        }

        return (EnergyStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), Direction.NORTH) != null ||
                EnergyStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), Direction.EAST) != null ||
                EnergyStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), Direction.WEST) != null ||
                EnergyStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), Direction.SOUTH) != null);
    }

    @Override
    public EnergyContainer WrapExternalStorage(Level level, BlockPos pos, Direction facing, BlockEntity blockEntity) {
        EnergyStorage storage = EnergyStorage.SIDED.find(level, pos, facing);
        return EnergyContainer.ExternalContainerBuilder.Open()
                .capacity(storage.getCapacity())
                .maxExtract(storage.getCapacity())
                .maxReceive(storage.getCapacity())
                .energy(storage.getAmount())
                .rawLoaderDependentContainer(storage)
                .rawBlockEntity(blockEntity)
                .Build();
    }
}
