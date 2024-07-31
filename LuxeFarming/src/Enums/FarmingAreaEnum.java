package Enums;

import Enums.Allotment.AllotmentPatchesEnum;
import Enums.Flower.FlowerPatchesEnum;
import Enums.Herb.HerbPatchesEnum;
import org.dreambot.api.methods.map.Area;

import static Areas.FarmingAreas.*;
import static Enums.Allotment.AllotmentPatchesEnum.*;
import static Enums.Flower.FlowerPatchesEnum.*;
import static Enums.Herb.HerbPatchesEnum.*;

public enum FarmingAreaEnum {

    NONE(null, null,null,null, 0),
    DRAYNOR(DraynorFarmingPatch, DRAYNOR_ALLOTMENT, DRAYNOR_FLOWER_PATCH, DRAYNOR_HERB_PATCH, 0),
    CANAFIS(CanafisFarmingPatch, CANAFIS_ALLOTMENT, CANAFIS_FLOWER_PATCH, CANAFIS_HERB_PATCH, 0),
    ARDOUGNE(ArdougneFarmingPatch, ARDOUGNE_ALLOTMENT, ARDOUGNE_FLOWER_PATCH, ARDOUGNE_HERB_PATCH, 0),
    VARLAMORE(VarlamoreFarmingPatch, VARLAMORE_ALLOTMENT, VARLAMORE_FLOWER_PATCH, VARLAMORE_HERB_PATCH, 0),
    HOSIDIOUS(HosidiousFarmingPatch, HOSIDIOUS_ALLOTMENT, HOSIDIOUS_FLOWER_PATCH, HOSIDIOUS_HERB_PATCH, 0),
    FARMING_GUILD(FarmingGuildFarmingPatch, FARMING_GUILD_ALLOTMENT, FARMING_GUILD_FLOWER_PATCH, FARMING_GUILD_HERB_PATCH, 0),
    TROLLHEIM(TrollheimHerbArea, null, null, TROLLHEIM_HERB_PATCH, 0),
    WEISS(WeissHerbArea, null, null, WEISS_HERB_PATCH, 0),
    FOSSIL_ISLAND_SEAWEED(FossilIslandUnderWaterArea, null, null, null, 0), //new Tile(3732,10281,1)
    CATHERBY(CatherbyFarmingPatch, CATHERBY_ALLOTMENT, CATHERBY_FLOWER_PATCH, CATHERBY_HERB_PATCH, 0);
    private final Area area;
    private final AllotmentPatchesEnum AllotmentPatches;
    private final FlowerPatchesEnum FlowerPatch;
    private final HerbPatchesEnum HerbPatch;
    private long LastVisited;
    FarmingAreaEnum(Area area, AllotmentPatchesEnum AllotmentPatches, FlowerPatchesEnum FlowerPatch, HerbPatchesEnum HerbPatch, long LastVisited) {
        this.area = area;
        this.AllotmentPatches = AllotmentPatches;
        this.FlowerPatch = FlowerPatch;
        this.HerbPatch = HerbPatch;
        this.LastVisited = LastVisited;
    }

    public Area getArea() {
        return area;
    }

    public AllotmentPatchesEnum getAllotmentPatches() {
        return AllotmentPatches;
    }
    public FlowerPatchesEnum getFlowerPatch() {
        return FlowerPatch;
    }

    public HerbPatchesEnum getHerbPatch() {
        return HerbPatch;
    }

    public long getLastVisited() {
        return LastVisited;
    }

    public long setLastVisited(long time) {
        this.LastVisited = time;
        return LastVisited;
    }

}
