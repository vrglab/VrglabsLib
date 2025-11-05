package org.Vrglab.VrglabsLib.Core;

import mod.azure.azurelib.AzureLib;
import org.Vrglab.VrglabsLib.API.AutoRegistry.AutoRegistryLoader;
import org.Vrglab.VrglabsLib.platform.Services;

import java.util.ServiceLoader;

public class VrglabsInitializer {


    public static void Initialize(String ModId, String modPackage, Object... args)
    {
        Constants.LOG.info("Initializing VrglabsLib");
        InitializeAutoregistration(ModId, modPackage);

        InitializeAzureLib(args);
    }

    public static void InitializeAutoregistration(String ModId, String modPackage)
    {
        Constants.LOG.info("Initializing Autoregistration");
        AutoRegistryLoader.LoadAllInPackage(modPackage, ModId);
    }

    public static void InitializeAzureLib(Object... args)
    {
        Constants.LOG.info("Initializing AzureLib");
        Services.LIB_INITIALIZER.LoadAzureLib(args);
    }
}
