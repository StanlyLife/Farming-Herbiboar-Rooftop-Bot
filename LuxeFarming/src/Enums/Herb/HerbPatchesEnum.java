package Enums.Herb;

import Enums.CompostEnum;
import Enums.PatchStatus;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Arrays;

import static Enums.PatchStatus.*;
import static Identifiers.FarmingGameObjects.*;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;

public enum HerbPatchesEnum {
    DRAYNOR_HERB_PATCH(Draynor_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    CATHERBY_HERB_PATCH(Catherby_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    ARDOUGNE_HERB_PATCH(Ardougne_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    VARLAMORE_HERB_PATCH(varlamore_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    HOSIDIOUS_HERB_PATCH(hosidious_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    FARMING_GUILD_HERB_PATCH(farming_guild_HerbPatch_ID,  CompostEnum.NONE,false, 0),
    TROLLHEIM_HERB_PATCH(farming_trollheim_herbPatch_ID,  CompostEnum.NONE,false, 0),
    WEISS_HERB_PATCH(farming_weiss_herbPatch_ID,  CompostEnum.NONE,false, 0),
    CANAFIS_HERB_PATCH(Canafis_HerbPatch_ID,  CompostEnum.NONE,false, 0);
    private final int patchId;
    private CompostEnum compostUsed;
    private boolean isPlanted;
    private long timePlanted;
    HerbPatchesEnum(int patchId, CompostEnum compostUsed, boolean isPlanted, long timePlanted) {
        this.patchId = patchId;
        this.compostUsed = compostUsed;
        this.isPlanted = isPlanted;
        this.timePlanted = timePlanted;
    }
    private void setTimePlantedSettings(long time) {
        if(this.name().equals("DRAYNOR_HERB_PATCH")) getSettings().draynor_herb_tp = time;
        else if(this.name().equals("CANAFIS_HERB_PATCH")) getSettings().canafis_herb_tp = time;
        else if(this.name().equals("CATHERBY_HERB_PATCH")) getSettings().catherby_herb_tp = time;
        else if(this.name().equals("ARDOUGNE_HERB_PATCH")) getSettings().ardougne_herb_tp = time;
        else if(this.name().equals("VARLAMORE_HERB_PATCH")) getSettings().varlamore_herb_tp = time;
        else if(this.name().equals("HOSIDIOUS_HERB_PATCH")) getSettings().hosidious_herb_tp = time;
        else if(this.name().equals("FARMING_GUILD_HERB_PATCH")) getSettings().farming_guild_herb_tp = time;
        else if(this.name().equals("TROLLHEIM_HERB_PATCH")) getSettings().trollheim_herb_tp = time;
        else Logger.log("UNABLE TO FIND HERB PATCH FOR: " + name());
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
        setTimePlantedSettings(System.currentTimeMillis());
        this.isPlanted = isPlanted;
        ScriptSettings.save(getSettings(), SAVENAME);
    }

    public PatchStatus getPatchStatus() {
        GameObject p = GameObjects.closest(patch -> patch.getName().equals("Herb patch") || patch.getName().toLowerCase().contains("herbs"), Players.getLocal().getTile());

        if(p == null) return null;

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
        if(
                (Arrays.stream(GameObjects.getObjectsOnTile(p.getTile())).anyMatch(g -> g.getName().equalsIgnoreCase("Herbs") && !p.hasAction("Pick")))
                || p.getID() == 18816 //18816 = planted in trollheim as it does not change name when planted
        )
        {
            return PLANTED;
        }
        return PLANT;
    }


    public long getTimePlanted() {
        return timePlanted;
    }

    public void setTimePlanted(long timePlanted) {
        this.timePlanted = timePlanted;
    }


}
