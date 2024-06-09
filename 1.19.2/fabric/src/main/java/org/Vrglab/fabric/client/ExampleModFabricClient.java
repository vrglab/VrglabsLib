package org.Vrglab.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;

public final class ExampleModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricLikeRegisteryCreator.CreateClient(VLModInfo.MOD_ID);
    }
}
