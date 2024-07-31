package Enums.Flower;

import Enums.CompostEnum;
import Enums.PatchStatus;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Arrays;

import static Areas.FarmingAreas.*;
import static Enums.Allotment.AllotmentSeeds.AllotmentPlantNames;
import static Enums.Flower.FlowerSeeds.FlowerPlantNames;
import static Enums.PatchStatus.*;
import static Identifiers.FarmingGameObjects.*;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;

public enum FlowerPatchesEnum {
    DRAYNOR_FLOWER_PATCH(Draynor_FlowerPatch_ID,  CompostEnum.NONE,false, 0, tile_draynor_flower_patch),
    CANAFIS_FLOWER_PATCH(Canafis_FlowerPatch_ID,  CompostEnum.NONE,false, 0, tile_canafis_flower_patch),
    ARDOUGNE_FLOWER_PATCH(Ardougne_FlowerPatch_ID,  CompostEnum.NONE,false, 0, tile_ardougne_flower_patch),
    VARLAMORE_FLOWER_PATCH(varlamore_FlowerPatch_ID,  CompostEnum.NONE,false, 0, tile_varlamore_flower_patch),
    HOSIDIOUS_FLOWER_PATCH(hosidious_FlowerPatch_ID,  CompostEnum.NONE,false, 0, tile_hosidious_flower_patch),
    FARMING_GUILD_FLOWER_PATCH(farming_guild_FlowerPatch_ID,  CompostEnum.NONE,false, 0,tile_farming_guild_flower_patch),
    CATHERBY_FLOWER_PATCH(Catherby_FlowerPatch_ID,  CompostEnum.NONE,false, 0,tile_catherby_flower_patch);

    private int patchId;
    private CompostEnum compostUsed;
    private boolean isPlanted;
    private long timePlanted;
    private Tile patchTile;
    FlowerPatchesEnum(int patchId, CompostEnum compostUsed, boolean isPlanted, long timePlanted, Tile patchTile) {
        this.patchId = patchId;
        this.compostUsed = compostUsed;
        this.isPlanted = isPlanted;
        this.timePlanted = timePlanted;
        this.patchTile = patchTile;
    }
    private void setTimePlantedSettings(long time) {
        if(name().equals("DRAYNOR_FLOWER_PATCH")) getSettings().draynor_flower_tp = time;
        else if(name().equals("CANAFIS_FLOWER_PATCH")) getSettings().canafis_flower_tp = time;
        else if(name().equals("CATHERBY_FLOWER_PATCH")) getSettings().catherby_flower_tp = time;
        else if(name().equals("ARDOUGNE_FLOWER_PATCH")) getSettings().ardougne_flower_tp = time;
        else if(name().equals("VARLAMORE_FLOWER_PATCH")) getSettings().varlamore_flower_tp = time;
        else if(name().equals("HOSIDIOUS_FLOWER_PATCH")) getSettings().hosidious_flower_tp = time;
        else if(name().equals("FARMING_GUILD_FLOWER_PATCH")) getSettings().farming_guild_flower_tp = time;
        else {Logger.log("UNABLE TO FIND FLOWER PATCH FOR: " + name());}
    }

    public void resetPatch() {
        this.compostUsed = CompostEnum.NONE;
        this.isPlanted = false;
        this.timePlanted = 0;
        setTimePlantedSettings(0);
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
        setTimePlantedSettings(System.currentTimeMillis());
        Logger.log("Saved settings: Flower patch planted at - " + getSettings().draynor_flower_tp);
        ScriptSettings.save(getSettings(), SAVENAME);
    }



    public long getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(long timePlanted) {
        this.timePlanted = timePlanted;
    }


    public PatchStatus getPatchStatus() {
        GameObject p = GameObjects.closest(patch ->
                (Arrays.stream(FlowerPlantNames).anyMatch(apn ->  patch.getName().toLowerCase().contains(apn.toLowerCase()))
            ||  patch.getName().toLowerCase().contains("flower patch"))
                && patch.distance(patchTile) <= 2
        );

        if(p == null) {
            Logger.log("Unable to find the flower patch");
            if(Walking.shouldWalk()) {
                Walking.walk(this.getPatchTile());
            }
            return UNKNOWN;
        }

        if(p.hasAction("Rake")) {
            return RAKE;
        }
        if(p.hasAction("Pick")) {
            return HARVEST;
        }
        if(p.hasAction("Clear")) {
            return CLEAR;
        }
        if(p.hasAction("Cure")) {
            return CURE;
        }
        if(p.hasAction("Rake")) {
            return RAKE;
        }
        if(Arrays.stream(GameObjects.getObjectsOnTile(p.getTile())).anyMatch(g -> g.getName().equalsIgnoreCase(getSettings().seedsToPlant.getFlowerSeeds().getPlantName()) && !p.hasAction("Pick")))
        {
            return PLANTED;
        }
        return PLANT;
    }


    public Tile getPatchTile() {
        return patchTile;
    }
}
