package org.Vrglab.EnergySystem;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.VLModInfo;

import java.util.HashMap;
import java.util.Map;

public class EnergyStorageUtils {
    public static ICallBack createStorageInstance, extractEnergyInstance, receiveEnergyInstance, wrapExternalStorage, hasExternalStorage;
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


    public static boolean pushEnergyTo(BlockEntity self, World world, BlockPos blockPos, Direction dir, long amnt) {
        if(EnergyStorage.containEnergyStorage(world, blockPos.offset(dir))) {
            EnergyStorage storage = (EnergyStorage)EnergyStorage.getStorageInWorld(world, blockPos, dir);
            EnergyStorage self_storage = ((IEnergySupplier)self).getEnergyStorage();
            if(storage != null && (!self_storage.isEmpty() && !storage.atMaxCapacity())) {
                storage.receiveEnergy(amnt);
                self_storage.extractEnergy(amnt);
                return true;
            }
        }
        return false;
    }

    public static boolean pullEnergyFrom(BlockEntity self, World world, BlockPos blockPos, Direction dir, long amnt) {
        if(EnergyStorage.containEnergyStorage(world, blockPos.offset(dir))) {
            EnergyStorage storage = (EnergyStorage)EnergyStorage.getStorageInWorld(world, blockPos, dir);
            EnergyStorage self_storage = ((IEnergySupplier)self).getEnergyStorage();
            if(storage != null && (!self_storage.atMaxCapacity() && !storage.isEmpty())) {
                storage.extractEnergy(amnt);
                self_storage.receiveEnergy(amnt);
                return true;
            }
        }
        return false;
    }

}
