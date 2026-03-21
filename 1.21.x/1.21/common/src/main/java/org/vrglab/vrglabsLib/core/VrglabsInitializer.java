package org.vrglab.vrglabsLib.core;

import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.platform.Services;

public class VrglabsInitializer {


    public static void Initialize(String modId, String modPackage, Object... args) {
        Constants.LOG.info("Initializing VrglabsLib");

        Constants.LOG.info("Initializing Autoregistration");
        InitializeAutoregistration(modId, modPackage);

        Constants.LOG.info("Initializing AzureLib");
        InitializeAzureLib(args);
    }

    public static void InitializeAutoregistration(String modId, String modPackage) {
        AutoRegistryLoader.LoadAllInPackage(modPackage, modId);
    }

    public static void InitializeAzureLib(Object... args) {
        Services.LIB_INITIALIZER.LoadAzureLib(args);
    }
}
