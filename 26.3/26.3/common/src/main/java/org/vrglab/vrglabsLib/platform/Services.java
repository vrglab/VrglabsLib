package org.vrglab.vrglabsLib.platform;

import org.vrglab.vrglabsLib.platform.services.ILibInitializer;
import org.vrglab.vrglabsLib.platform.services.IPlatformHelper;
import org.vrglab.vrglabsLib.platform.services.ITypeService;
import org.vrglab.vrglabsLib.platform.services.energy.IEnergyService;

import java.util.ServiceLoader;

public final class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final ILibInitializer LIB_INITIALIZER = load(ILibInitializer.class);
    public static final IEnergyService ENERGY = load(IEnergyService.class);
    public static final ITypeService TYPE_SERVICE = load(ITypeService.class);

    private Services() {
        throw new UnsupportedOperationException();
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).
                findFirst().
                orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
