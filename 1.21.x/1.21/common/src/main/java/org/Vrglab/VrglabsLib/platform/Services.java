package org.Vrglab.VrglabsLib.platform;

import org.Vrglab.VrglabsLib.platform.services.ILibInitializer;
import org.Vrglab.VrglabsLib.platform.services.IPlatformHelper;
import org.spongepowered.asm.logging.ILogger;

import java.util.ServiceLoader;

public final class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final ILibInitializer LIB_INITIALIZER = load(ILibInitializer.class);

    private Services() {
        throw new UnsupportedOperationException();
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
