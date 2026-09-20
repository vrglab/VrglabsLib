package org.vrglab.azure.azurelib.platform;

import java.util.ServiceLoader;

import org.vrglab.azure.azurelib.platform.services.AzureLibNetwork;
import org.vrglab.azure.azurelib.platform.services.IPlatformHelper;

public final class Services {

    public static final AzureLibNetwork NETWORK = load(AzureLibNetwork.class);

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    private Services() {
        throw new UnsupportedOperationException();
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
