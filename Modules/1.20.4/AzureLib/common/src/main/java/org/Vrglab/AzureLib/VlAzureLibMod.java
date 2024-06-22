package org.Vrglab.AzureLib;

import mod.azure.azurelib.common.internal.common.AzureLib;

public final class VlAzureLibMod {
    public static final String MOD_ID = "vrglabs_azurelib";

    public static void init() {
        AzureLib.initialize();
    }
}
