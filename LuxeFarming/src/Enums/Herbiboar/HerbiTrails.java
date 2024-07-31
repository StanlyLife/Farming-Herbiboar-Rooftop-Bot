package Enums.Herbiboar;

import Enums.Varbits.HerbiVarbits;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.utilities.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum HerbiTrails {

    TRAIL_31303(31303, HerbiVarbits.HB_TRAIL_31303, null, new Tile(3697, 3875, 0), null, new Tile(3699, 3875, 0)),
    TRAIL_31306(31306, HerbiVarbits.HB_TRAIL_31306, null, new Tile(3672, 3890, 0), null, new Tile(3670, 3889, 0)),
    TRAIL_31309(31309, HerbiVarbits.HB_TRAIL_31309, null, new Tile(3681, 3859, 0), null, new Tile(3681, 3860, 0)),
    TRAIL_31312(31312, HerbiVarbits.HB_TRAIL_31312, new Tile(3699, 3875, 0), new Tile(3710, 3877, 0), new Tile(3697, 3875, 0), new Tile(3708, 3876, 0)),
    TRAIL_31315(31315, HerbiVarbits.HB_TRAIL_31315, new Tile(3699, 3875, 0), new Tile(3728, 3893, 0), new Tile(3697, 3875, 0), null),
    TRAIL_31318(31318, HerbiVarbits.HB_TRAIL_31318, new Tile(3670, 3889, 0), new Tile(3728, 3893, 0), new Tile(3672, 3890, 0), null),
    TRAIL_31321(31321, HerbiVarbits.HB_TRAIL_31321, new Tile(3670, 3889, 0), new Tile(3667, 3862, 0), new Tile(3672, 3890, 0), new Tile(3668, 3865, 0)),
    TRAIL_31324(31324, HerbiVarbits.HB_TRAIL_31324, new Tile(3681, 3860, 0), new Tile(3680, 3836, 0), new Tile(3681, 3859, 0), new Tile(3680, 3838, 0)),
    TRAIL_31327(31327, HerbiVarbits.HB_TRAIL_31327, new Tile(3681, 3860, 0), new Tile(3698, 3847, 0), new Tile(3681, 3859, 0), new Tile(3694, 3847, 0)),
    TRAIL_31330(31330, HerbiVarbits.HB_TRAIL_31330, new Tile(3708, 3876, 0), new Tile(3713, 3850, 0), new Tile(3710, 3877, 0), new Tile(3715, 3851, 0)),
    TRAIL_31333(31333, HerbiVarbits.HB_TRAIL_31333, new Tile(3708, 3876, 0), new Tile(3694, 3847, 0), new Tile(3710, 3877, 0), new Tile(3698, 3847, 0)),
    TRAIL_31336(31336, HerbiVarbits.HB_TRAIL_31336, new Tile(3728, 3893, 0), null, null, null),
    TRAIL_31339(31339, HerbiVarbits.HB_TRAIL_31339, new Tile(3728, 3893, 0), new Tile(3710, 3877, 0), null, new Tile(3708, 3876, 0)),
    TRAIL_31342(31342, HerbiVarbits.HB_TRAIL_31342, new Tile(3668, 3865, 0), new Tile(3681, 3860, 0), new Tile(3667, 3862, 0), new Tile(3681, 3859, 0)),
    TRAIL_31345(31345, HerbiVarbits.HB_TRAIL_31345, new Tile(3668, 3865, 0), new Tile(3680, 3836, 0), new Tile(3667, 3862, 0), new Tile(3680, 3838, 0)),
    TRAIL_31348(31348, HerbiVarbits.HB_TRAIL_31348, new Tile(3680, 3838, 0), new Tile(3706, 3811, 0), new Tile(3680, 3836, 0), null),
    TRAIL_31351(31351, HerbiVarbits.HB_TRAIL_31351, new Tile(3680, 3838, 0), null, new Tile(3680, 3836, 0), null),
    TRAIL_31354(31354, HerbiVarbits.HB_TRAIL_31354, new Tile(3694, 3847, 0), null, new Tile(3698, 3847, 0), null),
    TRAIL_31357(31357, HerbiVarbits.HB_TRAIL_31357, new Tile(3715, 3851, 0), new Tile(3713, 3840, 0), new Tile(3713, 3850, 0), null),
    TRAIL_31360(31360, HerbiVarbits.HB_TRAIL_31360, new Tile(3715, 3851, 0), null, new Tile(3713, 3850, 0), null),
    TRAIL_31363(31363, HerbiVarbits.HB_TRAIL_31363, new Tile(3713, 3850, 0), null, new Tile(3715, 3851, 0), null),
    TRAIL_31366(31366, HerbiVarbits.HB_TRAIL_31366, null, null, null, null),
    TRAIL_31369(31369, HerbiVarbits.HB_TRAIL_31369, new Tile(3706, 3811, 0), null, null, null),
    TRAIL_31372(31372, HerbiVarbits.HB_TRAIL_31372, new Tile(3713, 3840, 0), null, null, null);

    // Constructor
    HerbiTrails(int trailId, HerbiVarbits varbit, Tile objectLoc1, Tile objectLoc2, Tile objectLoc3, Tile objectLoc4)
    {
        this.trailId = trailId;
        this.varbit = varbit;
        this.objectLoc1 = objectLoc1;
        this.objectLoc2 = objectLoc2;
        this.objectLoc3 = objectLoc3;
        this.objectLoc4 = objectLoc4;
    }

    private final int trailId;
    private final HerbiVarbits varbit;

    private Tile objectLoc1;
    private Tile objectLoc2;
    private Tile objectLoc3;
    private Tile objectLoc4;

    private static Set<Integer> trailIds = new HashSet<>();
    private static Set<Tile> inspectLocations = new HashSet<>();
    public static final List<Tile> HERBI_END_LOCATIONS = Arrays.asList(
            new Tile(3693, 3798, 0),
            new Tile(3702, 3808, 0),
            new Tile(3703, 3826, 0),
            new Tile(3710, 3881, 0),
            new Tile(3700, 3877, 0),
            new Tile(3715, 3840, 0),
            new Tile(3751, 3849, 0),
            new Tile(3685, 3869, 0),
            new Tile(3681, 3863, 0)
    );
    public static final List<Tile> HERBI_START_LOCATIONS = Arrays.asList(
            new Tile(3686, 3870, 0),
            new Tile(3705, 3830, 0),
            new Tile(3704, 3810, 0),
            new Tile(3751, 3850, 0)
    );

    static {
        for (HerbiTrails trail : values()) {
            trailIds.add(trail.trailId);
            trailIds.add(trail.trailId + 1);
            inspectLocations.addAll(Arrays.asList(trail.getObjectLocs(1)));
            inspectLocations.addAll(Arrays.asList(trail.getObjectLocs(2)));
        }
    }

    public static HerbiTrails getActiveTrail() {
        for (HerbiTrails trail : HerbiTrails.values()) {
            int currentVarbitValue = PlayerSettings.getBitValue(trail.varbit.getVarbit());
            if (currentVarbitValue == 1 || currentVarbitValue == 2) {
                return trail;
            }
        }
        return null;
    }

    public int getVarbitValue() {
        return PlayerSettings.getBitValue(varbit.getVarbit());
    }

    public static int getHerbiFinishValue() {
        return PlayerSettings.getBitValue(5766);
    }

    public Tile[] getObjectLocs(int varbitValue) {
        switch (varbitValue) {
            case 1:
                return new Tile[]{objectLoc1, objectLoc3};
            case 2:
                return new Tile[]{objectLoc2, objectLoc4};
            case 0:
            default:
                return new Tile[]{};
        }
    }

    public HerbiVarbits getVarbit() {
        return varbit;
    }
}
