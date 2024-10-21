package org.Vrglab.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;


public class VlModelProvider extends FabricModelProvider {

    public VlModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        FabricLikeRegisteryCreator.callBlockDataGen(blockStateModelGenerator, VLModInfo.MOD_ID);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        FabricLikeRegisteryCreator.callItemDataGen(itemModelGenerator, VLModInfo.MOD_ID);
    }
}
