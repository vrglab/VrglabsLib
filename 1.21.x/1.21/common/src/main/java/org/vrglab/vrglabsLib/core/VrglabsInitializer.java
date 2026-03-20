package org.vrglab.vrglabsLib.core;

import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.platform.Services;

public class VrglabsInitializer {


    public static void Initialize(String ModId, String modPackage, Object... args)
    {
        Constants.LOG.info("Initializing VrglabsLib");

        Constants.LOG.info("Initializing Autoregistration");
        InitializeAutoregistration(ModId, modPackage);

        Constants.LOG.info("Initializing AzureLib");
        InitializeAzureLib(args);
    }

    public static void InitializeAutoregistration(String ModId, String modPackage)
    {
        AutoRegistryLoader.LoadAllInPackage(modPackage, ModId);
    }

    public static void InitializeAzureLib(Object... args)
    {
        Services.LIB_INITIALIZER.LoadAzureLib(args);
    }
}
