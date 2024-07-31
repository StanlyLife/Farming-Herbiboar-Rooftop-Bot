package Enums.Herbiboar;

import Enums.Varbits.HerbiVarbits;

import java.util.HashSet;
import java.util.Set;

class TrailToSpot
{

    private final HerbiVarbits varbitId;
    /**
     * The cutoff reference value to compare against the value of {@link TrailToSpot#getVarbitId()} ()} to determine its state
     * along the current trail.
     */
    private final int value;
    /**
     * The object ID of the footprints which appear when the trail is made visible.
     */
    private final int footprint;

    TrailToSpot(HerbiVarbits varbitId, int value, int footprint) {
        this.varbitId = varbitId;
        this.value = value;
        this.footprint = footprint;
    }

    Set<Integer> getFootprintIds()
    {
        Set<Integer> footprintIds = new HashSet<>();
        footprintIds.add(footprint);
        footprintIds.add(footprint + 1);
        return footprintIds;
    }

    public int getVarbitId() {
        return varbitId.getVarbit();
    }

    public int getValue() {
        return value;
    }
}
