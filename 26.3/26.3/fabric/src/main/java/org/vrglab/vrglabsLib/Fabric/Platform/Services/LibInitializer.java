package org.vrglab.vrglabsLib.Fabric.Platform.Services;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.vrglabsLib.platform.services.ILibInitializer;

public class LibInitializer implements ILibInitializer {

    @Override
    public void LoadAzureLib(Object... args) {
        org.vrglab.azure.azurelib.config.io.ConfigIO.FILE_WATCH_MANAGER.startService();
        AzureLib.initialize();
    }
}
