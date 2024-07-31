package Enums.Allotment;

import Enums.CompostEnum;
import Enums.PatchStatus;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.GameObject;
import sun.rmi.runtime.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static Areas.FarmingAreas.*;
import static Enums.Allotment.AllotmentSeeds.AllotmentPlantNames;
import static Enums.PatchStatus.*;
import static Identifiers.FarmingGameObjects.*;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;

public enum AllotmentPatchEnum {
    DRAYNOR_ALLOTMENT_PATCH_NORTH(Draynor_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0,tile_draynor_north_allotment_patch),
    DRAYNOR_ALLOTMENT_PATCH_SOUTH(Draynor_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_draynor_south_allotment_patch),
    CANAFIS_ALLOTMENT_PATCH_NORTH(Canafis_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_canafis_north_allotment_patch),
    CANAFIS_ALLOTMENT_PATCH_SOUTH(Canafis_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_canafis_south_allotment_patch),
    CATHERBY_ALLOTMENT_PATCH_NORTH(Catherby_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_catherby_north_allotment_patch),
    CATHERBY_ALLOTMENT_PATCH_SOUTH(Catherby_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_catherby_south_allotment_patch),
    ARDOUGNE_ALLOTMENT_PATCH_NORTH(Ardougne_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_ardougne_north_allotment_patch),
    ARDOUGNE_ALLOTMENT_PATCH_SOUTH(Ardougne_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_ardougne_south_allotment_patch),
    VARLAMORE_ALLOTMENT_PATCH_NORTH(varlamore_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_varlamore_north_allotment_patch),
    VARLAMORE_ALLOTMENT_PATCH_SOUTH(varlamore_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_varlamore_south_allotment_patch),
    HOSIDIOUS_ALLOTMENT_PATCH_NORTH(hosidious_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_hosidious_north_allotment_patch),
    HOSIDIOUS_ALLOTMENT_PATCH_SOUTH(hosidious_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_hosidious_south_allotment_patch),
    FARMING_GUILD_ALLOTMENT_PATCH_NORTH(farming_guild_North_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_farming_guild_north_allotment_patch),
    FARMING_GUILD_ALLOTMENT_PATCH_SOUTH(farming_guild_South_AllotmentPatch_ID, CompostEnum.NONE, false,false, 0, tile_farming_guild_south_allotment_patch);

    private final int patchId;
    private CompostEnum compostUsed;
    private boolean isPlanted;
    private boolean isWatered;
    private long timePlanted;
    private final Tile patchTile;
    AllotmentPatchEnum(int patchId, CompostEnum compostUsed, boolean isPlanted,boolean isWatered, long timePlanted, Tile patchTile) {
        this.patchId = patchId;
        this.compostUsed = compostUsed;
        this.isPlanted = isPlanted;
        this.timePlanted = timePlanted;
        this.isWatered = isWatered;
        this.patchTile = patchTile;
    }
    private void setTimePlantedSettings(long timePlanted) {
        if(name().equals("DRAYNOR_ALLOTMENT_PATCH_NORTH")) getSettings().draynor_allotment_north_tp = timePlanted;
        else if(name().equals("DRAYNOR_ALLOTMENT_PATCH_SOUTH")) getSettings().draynor_allotment_south_tp = timePlanted;
        else if(name().equals("CANAFIS_ALLOTMENT_PATCH_NORTH")) getSettings().canafis_allotment_north_tp = timePlanted;
        else if(name().equals("CANAFIS_ALLOTMENT_PATCH_SOUTH")) getSettings().canafis_allotment_south_tp = timePlanted;
        else if(name().equals("CATHERBY_ALLOTMENT_PATCH_NORTH")) getSettings().catherby_allotment_north_tp = timePlanted;
        else if(name().equals("CATHERBY_ALLOTMENT_PATCH_SOUTH")) getSettings().catherby_allotment_south_tp = timePlanted;
        else if(name().equals("ARDOUGNE_ALLOTMENT_PATCH_NORTH")) getSettings().ardougne_allotment_north_tp = timePlanted;
        else if(name().equals("ARDOUGNE_ALLOTMENT_PATCH_SOUTH")) getSettings().ardougne_allotment_south_tp = timePlanted;
        else if(name().equals("VARLAMORE_ALLOTMENT_PATCH_NORTH")) getSettings().varlamore_allotment_north_tp = timePlanted;
        else if(name().equals("VARLAMORE_ALLOTMENT_PATCH_SOUTH")) getSettings().varlamore_allotment_south_tp = timePlanted;
        else if(name().equals("HOSIDIOUS_ALLOTMENT_PATCH_NORTH")) getSettings().hosidious_allotment_north_tp = timePlanted;
        else if(name().equals("HOSIDIOUS_ALLOTMENT_PATCH_SOUTH")) getSettings().hosidious_allotment_south_tp = timePlanted;
        else if(name().equals("FARMING_GUILD_ALLOTMENT_PATCH_NORTH")) getSettings().farming_guild_allotment_north_tp = timePlanted;
        else if(name().equals("FARMING_GUILD_ALLOTMENT_PATCH_SOUTH")) getSettings().farming_guild_allotment_south_tp = timePlanted;
        else Logger.log("UNABLE TO FIND PATCH FOR: " + name());
    }

    public int getPatchId() {
        return patchId;
    }

    public CompostEnum getCompostUsed() {
        return compostUsed;
    }
    public void setCompostUsed(CompostEnum compost) {
        this.compostUsed = compost;
    }

    public boolean isPlanted() {
        return isPlanted;
    }

    public void setIsPlanted(boolean isPlanted) {

        this.isPlanted = isPlanted;
        this.timePlanted = System.currentTimeMillis();
        setTimePlantedSettings(timePlanted);

        ScriptSettings.save(getSettings(), SAVENAME);
    }



    public long getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(long timePlanted) {
        this.timePlanted = timePlanted;
        setTimePlantedSettings(timePlanted);
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public Tile getPatchTile() {
        return patchTile;
    }

    public PatchStatus getPatchStatus() {
        String plantName = getSettings().seedsToPlant.getAllotmentSeed().getPlantName();
        GameObject p = GameObjects.closest(patch ->
                patch.getName().equals("Allotment")
            ||  Arrays.stream(AllotmentPlantNames).anyMatch(apn ->  patch.getName().toLowerCase().contains(apn.toLowerCase()))
                , patchTile);

        if(p == null) {
            Logger.log("UNABLE TO FIND ALLOTMENT PATCH");
            return  UNKNOWN;
        }

        if(p.hasAction("Rake")) {
            Logger.log("Rake - " + getClosestAllotmentPatch(p.getTile()).name());
            return RAKE;
        }
        if(p.hasAction("Harvest")) {
            Logger.log("Harvest - " + getClosestAllotmentPatch(p.getTile()).name());
            return HARVEST;
        }
        if(p.hasAction("Clear")) {
            Logger.log("Clear - " + getClosestAllotmentPatch(p.getTile()).name());
            return CLEAR;
        }
        if(p.hasAction("Cure")) {
            Logger.log("Cure - " + getClosestAllotmentPatch(p.getTile()).name());
            return CURE;
        }
        if(p.hasAction("Rake")) {
            Logger.log("Rake - " + getClosestAllotmentPatch(p.getTile()).name());
            return RAKE;
        }
        if(Arrays.stream(GameObjects.getObjectsOnTile(p.getTile())).anyMatch(g -> g.getName().toLowerCase().contains(plantName.toLowerCase()) && !p.hasAction("Harvest")))
        {
            return PLANTED;
        }
        Logger.log("Plant - " + getClosestAllotmentPatch(p.getTile()).name());
        return PLANT;
    }

    public void resetPatch() {
        this.compostUsed = CompostEnum.NONE;
        this.timePlanted = 0;
        this.isPlanted = false;
        this.isWatered = false;
        setTimePlantedSettings(0);

    }



    public static AllotmentPatchEnum getClosestAllotmentPatch(Tile startTile) {
        Tile startLocation = null;
        if(startTile != null) startLocation = startTile;
        if(startTile == null) startLocation = Players.getLocal().getServerTile();


        Tile finalStartLocation = startLocation;
        Tile closestTile = Arrays.stream(allotment_patch_tiles)
                .min(Comparator.comparingDouble(tile -> tile.distance(finalStartLocation))).get();


        for (AllotmentPatchEnum patch : AllotmentPatchEnum.values()) {
            if (patch.getPatchTile().getX() == closestTile.getX() && patch.getPatchTile().getY() == closestTile.getY()) {
                return patch;
            }
        }
        Logger.log("Invalid location x: " + closestTile.getX() + " y: " + closestTile.getY());
        throw new IllegalArgumentException("Invalid tile");
    }
}
