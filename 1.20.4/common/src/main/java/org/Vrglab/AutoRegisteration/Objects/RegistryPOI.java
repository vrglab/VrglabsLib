package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.poi.PointOfInterestType;

public class RegistryPOI extends AutoRegisteryObject<PointOfInterestType> {
    private String id;

    public RegistryPOI(String modid, RegistryBlock<?> block, int tickcount, int searchdistance) {
        super(modid);
        this.args.put("block", block);
        this.args.put("tickcount", tickcount);
        this.args.put("searchdistance", searchdistance);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
