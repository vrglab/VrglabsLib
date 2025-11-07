package org.vrglab.vrglabsLib.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergyContainer;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergySupplier;
import org.vrglab.vrglabsLib.platform.Services;

import java.util.HashMap;
import java.util.Map;

public class EnergyController {
    private static Map<BlockPos, IEnergyContainer> CACHED_CONTAINER = new HashMap<>();

    public static <T extends IEnergyContainer> T getCachedContainer(BlockPos pos){
        return (T) CACHED_CONTAINER.get(pos);
    }

    public static <T extends IEnergyContainer> void cacheEnergyContainer(BlockPos pos, T container){
        CACHED_CONTAINER.put(pos, container);
    }

    public static void removeFromCache(BlockPos pos) {
        CACHED_CONTAINER.remove(pos);
    }


    public static boolean pushEnergyTo(BlockEntity self, Level world, BlockPos blockPos, Direction dir, long amnt) {
        if(containEnergyStorage(world, blockPos.offset(dir.getNormal()))) {
            EnergyContainer storage = (EnergyContainer)getStorageInWorld(world, blockPos, dir);
            EnergyContainer self_storage = (EnergyContainer) ((IEnergySupplier)self).getEnergyStorage();
            if(storage != null && (!self_storage.isEmpty() && !storage.atMaxCapacity())) {
                storage.receiveEnergy(amnt);
                self_storage.extractEnergy(amnt);
                return true;
            }
        }
        return false;
    }

    public static boolean pullEnergyFrom(BlockEntity self, Level world, BlockPos blockPos, Direction dir, long amnt) {
        if(containEnergyStorage(world, blockPos.offset(dir.getNormal()))) {
            EnergyContainer storage = (EnergyContainer)getStorageInWorld(world, blockPos, dir);
            EnergyContainer self_storage = (EnergyContainer)((IEnergySupplier)self).getEnergyStorage();
            if(storage != null && (!self_storage.atMaxCapacity() && !storage.isEmpty())) {
                storage.extractEnergy(amnt);
                self_storage.receiveEnergy(amnt);
                return true;
            }
        }
        return false;
    }

    public static IEnergyContainer getStorageInWorld(Level world, BlockPos blockPos, Direction facing){
        IEnergyContainer storage =  null;
        BlockEntity entity = world.getBlockEntity(blockPos.offset(facing.getNormal()));
        if(containEnergyStorage(entity)) {
            try {
                if(entity != null && entity instanceof IEnergySupplier<?>) {
                    storage = ((IEnergySupplier<?>)entity).getEnergyStorage();
                } else if(Services.ENERGY.EntityHasEnergyCapabilities(entity)){
                    if (getCachedContainer(blockPos.offset(facing.getNormal())) != null) {
                        try{
                            storage = getCachedContainer(blockPos.offset(facing.getNormal()));
                            if(((BlockEntity)((EnergyContainer)storage).getRawBlockEntity()).isRemoved()) {
                                removeFromCache(blockPos.offset(facing.getNormal()));
                                throw new RuntimeException("Accessed Storage is removed");
                            }
                        } catch (Throwable t) {
                            storage = Services.ENERGY.WrapExternalStorage(world, blockPos, facing, entity);
                            cacheEnergyContainer(blockPos.offset(facing.getNormal()), storage);
                        }
                    } else {
                        storage = Services.ENERGY.WrapExternalStorage(world, blockPos, facing, entity);
                        cacheEnergyContainer(blockPos.offset(facing.getNormal()), storage);
                    }
                }

            } catch (Throwable t) {

            }
        }
        return storage;
    }

    public static boolean containEnergyStorage(Level world, BlockPos blockPos){
        BlockEntity entity = world.getBlockEntity(blockPos);
        return containEnergyStorage(entity);
    }

    public static boolean containEnergyStorage(BlockEntity entity){
        if(entity != null && entity instanceof IEnergySupplier<?> || Services.ENERGY.EntityHasEnergyCapabilities(entity)) {
            try {
                return true;
            } catch (Throwable t) {

            }
        }
        return  false;
    }
}
