package mod.azure.azurelib.neoforge.event;

import mod.azure.azurelib.common.internal.common.event.GeoRenderEvent;
import mod.azure.azurelib.common.platform.services.GeoRenderPhaseEventFactory;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * @author Boston Vanseghi
 */
public class NeoForgeGeoRenderPhaseEvent implements GeoRenderPhaseEventFactory.GeoRenderPhaseEvent {

    public static class NeoForgeGeoRenderEvent extends Event implements ICancellableEvent {
        public final GeoRenderEvent geoRenderEvent;

        public NeoForgeGeoRenderEvent(GeoRenderEvent geoRenderEvent) {
            this.geoRenderEvent = geoRenderEvent;
        }

        public GeoRenderEvent getGeoRenderEvent() {
            return this.geoRenderEvent;
        }
    }

    @Override
    public boolean handle(GeoRenderEvent geoRenderEvent) {
        return !NeoForge.EVENT_BUS.post(new NeoForgeGeoRenderEvent(geoRenderEvent)).isCanceled();
    }
}
