package Enums.Herbiboar;

import Enums.Varbits.HerbiVarbits;
import com.google.common.collect.ImmutableList;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.List;
import java.util.Map;

public enum HerbiSearchSpots {

    A_MUSHROOM(Group.A, new Tile(3670, 3889, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31318, 1, 31318),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31321, 1, 31321)),
    A_PATCH(Group.A, new Tile(3672, 3890, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31306, 2, 31306)),

    // Wiki B location
    B_SEAWEED(Group.B, new Tile(3728, 3893, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31315, 2, 31315),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31318, 2, 31318),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31336, 1, 31336),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31339, 1, 31339)),

    // Wiki C location
    C_MUSHROOM(Group.C, new Tile(3697, 3875, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31303, 2, 31303)),
    C_PATCH(Group.C, new Tile(3699, 3875, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31312, 1, 31312),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31315, 1, 31315)),

    // Wiki D location
    D_PATCH(Group.D, new Tile(3708, 3876, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31330, 1, 31330),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31333, 1, 31333)),
    D_SEAWEED(Group.D, new Tile(3710, 3877, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31312, 2, 31312),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31339, 2, 31339)),

    // Wiki E location
    E_MUSHROOM(Group.E, new Tile(3668, 3865, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31342, 1, 31342),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31345, 1, 31345)),
    E_PATCH(Group.E, new Tile(3667, 3862, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31321, 2, 31321)),

    // Wiki F location
    F_MUSHROOM(Group.F, new Tile(3681, 3860, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31324, 1, 31324),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31327, 1, 31327),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31342, 2, 31342)),
    F_PATCH(Group.F, new Tile(3681, 3859, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31309, 2, 31309)),

    // Wiki G location
    G_MUSHROOM(Group.G, new Tile(3694, 3847, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31333, 2, 31333),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31354, 1, 31354)),
    G_PATCH(Group.G, new Tile(3698, 3847, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31327, 2, 31327)),

    // Wiki H location
    H_SEAWEED_EAST(Group.H, new Tile(3715, 3851, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31357, 1, 31357),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31360, 1, 31360)),
    H_SEAWEED_WEST(Group.H, new Tile(3713, 3850, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31330, 2, 31330),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31363, 1, 31363)),

    // Wiki I location
    I_MUSHROOM(Group.I, new Tile(3680, 3838, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31348, 1, 31348),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31351, 1, 31351)),
    I_PATCH(Group.I, new Tile(3680, 3836, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31324, 2, 31324),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31345, 2, 31345)),

    // Wiki J location
    J_PATCH(Group.J, new Tile(3713, 3840, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31357, 2, 31357),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31372, 1, 31372)),

    // Wiki K location
    K_PATCH(Group.K, new Tile(3706, 3811, 0),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31348, 2, 31348),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31366, 1, 31366),
            new TrailToSpot(HerbiVarbits.HB_TRAIL_31369, 1, 31369)),
    ;


    private final Group group;
    private final Tile location;
    private final List<TrailToSpot> trails;
    private static Map<Tile, GameObject> trailObjects;

    HerbiSearchSpots(Group group, Tile location, TrailToSpot... trails)
    {
        this.group = group;
        this.location = location;
        this.trails = ImmutableList.copyOf(trails);
    }

    public Map<Tile, GameObject> getTrailObjects()
    {
        return trailObjects;
    }


}
