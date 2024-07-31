package Enums.Allotment;

import org.dreambot.api.methods.map.Tile;

import static Areas.FarmingAreas.*;
import static Enums.Allotment.AllotmentPatchEnum.*;

public enum AllotmentPatchesEnum {

    DRAYNOR_ALLOTMENT(DRAYNOR_ALLOTMENT_PATCH_NORTH, DRAYNOR_ALLOTMENT_PATCH_SOUTH, draynor_allotment_patch_tiles),
    CANAFIS_ALLOTMENT(CANAFIS_ALLOTMENT_PATCH_NORTH, CANAFIS_ALLOTMENT_PATCH_SOUTH, canafis_allotment_patch_tiles),
    ARDOUGNE_ALLOTMENT(ARDOUGNE_ALLOTMENT_PATCH_NORTH, ARDOUGNE_ALLOTMENT_PATCH_SOUTH, ardougne_allotment_patch_tiles),
    VARLAMORE_ALLOTMENT(VARLAMORE_ALLOTMENT_PATCH_NORTH, VARLAMORE_ALLOTMENT_PATCH_SOUTH, varlamore_allotment_patch_tiles),
    HOSIDIOUS_ALLOTMENT(HOSIDIOUS_ALLOTMENT_PATCH_NORTH, HOSIDIOUS_ALLOTMENT_PATCH_SOUTH, hosidious_allotment_patch_tiles),
    FARMING_GUILD_ALLOTMENT(FARMING_GUILD_ALLOTMENT_PATCH_NORTH, FARMING_GUILD_ALLOTMENT_PATCH_SOUTH, farming_guild_allotment_patch_tiles),
    CATHERBY_ALLOTMENT(CATHERBY_ALLOTMENT_PATCH_NORTH, CATHERBY_ALLOTMENT_PATCH_SOUTH, catherby_allotment_patch_tiles);

    private AllotmentPatchEnum north;
    private AllotmentPatchEnum south;
    private Tile[] allotmentPatchTiles;
    AllotmentPatchesEnum(AllotmentPatchEnum north, AllotmentPatchEnum south, Tile[] allotmentPatchTiles) {
        this.north = north;
        this.south = south;
        this.allotmentPatchTiles = allotmentPatchTiles;
    }

    public AllotmentPatchEnum getPatchById(int id) {
        if(north.getPatchId() == id) return north;
        if(south.getPatchId() == id) return south;
        return null;
    }

    public AllotmentPatchEnum getNorthPatch() {
        return north;
    }
    public AllotmentPatchEnum getSouthPatch() {
        return south;
    }

    public Tile[] getAllotmentPatchTiles() {
        return allotmentPatchTiles;
    }
}
