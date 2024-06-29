package mod.azure.azurelib.fabric.platform;

import mod.azure.azurelib.fabric.event.QuiltGeoRenderPhaseEvent;
import mod.azure.azurelib.common.platform.services.GeoRenderPhaseEventFactory;

/**
 * @author Boston Vanseghi
 */
public class QuiltGeoRenderPhaseEventFactory implements GeoRenderPhaseEventFactory {
    @Override
    public GeoRenderPhaseEvent create() {
        return new QuiltGeoRenderPhaseEvent();
    }
}
