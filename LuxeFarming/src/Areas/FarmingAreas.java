package Areas;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

import java.util.Arrays;
import java.util.Comparator;

public class FarmingAreas {
    public static Area DraynorFarmingPatch = new Area(3049, 3313, 3060, 3302);
    public static Area CanafisFarmingPatch = new Area(3595, 3545, 3608, 3519);
    public static Area CatherbyFarmingPatch = new Area(2803, 3470, 2815, 3458);
    public static Area ArdougneFarmingPatch = new Area(2659, 3380, 2673, 3369);
    public static Area VarlamoreFarmingPatch = new Area(1578, 3105, 1592, 3092);
    public static Area HosidiousFarmingPatch = new Area(1727, 3561, 1741, 3547);

    public static Area FarmingGuildFarmingPatch = new Area(1233, 3740, 1276, 3719);
    public static Area FarmingGuildAllotmentArea = new Area(1257, 3723, 1272, 3737);
    public static Area FarmingGuildHerbArea = new Area(1235, 3724, 1241, 3730);
    public static Area TrollheimHerbArea = new Area(2822, 3679, 2835, 3700);
    public static Area WeissHerbArea = new Area(2844, 3938, 2850, 3930);
    public static Area FossilIslandUnderWaterArea = new Area(3729, 10283, 3738, 10264, 1);

    public static Tile tile_draynor_flower_patch = new Tile(3054,3307);
    public static Tile tile_draynor_south_allotment_patch = new Tile(3057,3305);
    public static Tile tile_draynor_north_allotment_patch = new Tile(3052,3310);

    public static Tile tile_canafis_flower_patch = new Tile(3601,3525);
    public static Tile tile_canafis_north_allotment_patch = new Tile(3599,3528);
    public static Tile tile_canafis_south_allotment_patch = new Tile(3604,3523);

    public static Tile tile_catherby_flower_patch = new Tile(2810,3462);
    public static Tile tile_catherby_north_allotment_patch = new Tile(2814,3466);
    public static Tile tile_catherby_south_allotment_patch = new Tile(2814,3461);

    public static Tile tile_ardougne_flower_patch = new Tile(2666,3374);
    public static Tile tile_ardougne_north_allotment_patch = new Tile(2667,3377);
    public static Tile tile_ardougne_south_allotment_patch = new Tile(2667,3372);

    public static Tile tile_varlamore_flower_patch = new Tile(1585,3098);
    public static Tile tile_varlamore_north_allotment_patch = new Tile(1583,3101);
    public static Tile tile_varlamore_south_allotment_patch = new Tile(1588,3096);


    public static Tile tile_hosidious_flower_patch = new Tile(1733,3554);
    public static Tile tile_hosidious_north_allotment_patch = new Tile(1737,3557);
    public static Tile tile_hosidious_south_allotment_patch = new Tile(1732,3552);

    public static Tile tile_farming_guild_flower_patch = new Tile(1261,3727);
    public static Tile tile_farming_guild_north_allotment_patch = new Tile(1268,3731);
    public static Tile tile_farming_guild_south_allotment_patch = new Tile(1268,3728);

    public static Tile fossil_island_seaweed_under_water_patch_one = new Tile(3732, 10273, 1);
    public static Tile fossil_island_seaweed_under_water_patch_two = new Tile(3733, 10269, 1);


    public static Tile[] draynor_allotment_patch_tiles = {tile_draynor_south_allotment_patch,tile_draynor_north_allotment_patch};
    public static Tile[] canafis_allotment_patch_tiles = {tile_canafis_south_allotment_patch,tile_canafis_north_allotment_patch};
    public static Tile[] catherby_allotment_patch_tiles = {tile_catherby_north_allotment_patch,tile_catherby_south_allotment_patch};
    public static Tile[] ardougne_allotment_patch_tiles = {tile_ardougne_north_allotment_patch,tile_ardougne_south_allotment_patch};
    public static Tile[] varlamore_allotment_patch_tiles = {tile_varlamore_north_allotment_patch,tile_varlamore_south_allotment_patch};
    public static Tile[] hosidious_allotment_patch_tiles = {tile_hosidious_north_allotment_patch,tile_hosidious_south_allotment_patch};
    public static Tile[] farming_guild_allotment_patch_tiles = {tile_farming_guild_north_allotment_patch,tile_farming_guild_south_allotment_patch};
    public static Tile[] allotment_patch_tiles = {
            //ACTION DRAYNOR
            tile_draynor_south_allotment_patch,
            tile_draynor_north_allotment_patch,
            //ACTION CANAFIS
            tile_canafis_north_allotment_patch,
            tile_canafis_south_allotment_patch,
            //ACTION CATHERBY
            tile_catherby_north_allotment_patch,
            tile_catherby_south_allotment_patch,
            //ACTION ARDOUGNE
            tile_ardougne_north_allotment_patch,
            tile_ardougne_south_allotment_patch,
            //ACTION VARLAMORE
            tile_varlamore_north_allotment_patch,
            tile_varlamore_south_allotment_patch,
            //ACTION HOSIDIOUS
            tile_hosidious_north_allotment_patch,
            tile_hosidious_south_allotment_patch,
            //ACTION FARMING GUILD
            tile_farming_guild_north_allotment_patch,
            tile_farming_guild_south_allotment_patch
    };

    public static Tile getClosestPatch() {
        return Arrays.stream(allotment_patch_tiles)
                .min(Comparator.comparingDouble(tile -> tile.distance(Players.getLocal())))
                .orElse(null); // Return null if the array is empty
    }
}
