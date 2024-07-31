package Settings;


import Enums.FarmingAreaEnum;
import Enums.SeedsToPlant;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Logger;

public class FarmerSettings {
    public static String SAVENAME = "LuxeFarming-"+ Players.getLocal().getName().replace(" ", "_") +".json";
    private static FarmerSettings farmerSettings = new FarmerSettings();
    public static FarmerSettings getSettings() {
        return farmerSettings;
    }
    public static int lowestHpPercentage = Skills.getBoostedLevel(Skill.HITPOINTS);

    public static FarmerSettings setSettings(FarmerSettings settings) {
        farmerSettings = settings;
        if(settings.currentPatchString != "") {
            try {
                settings.currentPatch = FarmingAreaEnum.valueOf(settings.currentPatchString);
                if(settings.currentPatch == null) throw new IllegalArgumentException("Invalid patch name: " + settings.currentPatchString);
                Logger.log("set patch, current patch: " + settings.currentPatchString);
            } catch (IllegalArgumentException e) {
                Logger.log("Unable to set current patch, defaulting patch");
                settings.currentPatch = FarmingAreaEnum.CATHERBY;
                settings.currentPatchString = FarmingAreaEnum.CATHERBY.name();
                Logger.log(Logger.LogType.WARN,"Unable to set current patch based of currentPatchString: " + settings.currentPatchString);
            }
        }
        lowestHpPercentage = 100;
        return farmerSettings;
    }

    public boolean shouldLoop = true;
    public String currentPatchString = "CATHERBY";
    public FarmingAreaEnum currentPatch = FarmingAreaEnum.CATHERBY;
    public SeedsToPlant seedsToPlant = SeedsToPlant.DEFAULT;
    public String CompostToUse = "Ultracompost";
    public int successfullFarms = 0;

    //ACTION DRAYNOR
    public long draynor_allotment_north_tp = 0;
    public long draynor_allotment_south_tp = 0;
    public long draynor_herb_tp = 0;
    public long draynor_flower_tp = 0;
    public long draynor_last_visited = 0;
    //ACTION CANAFIS
    public long canafis_allotment_north_tp = 0;
    public long canafis_allotment_south_tp = 0;
    public long canafis_herb_tp = 0;
    public long canafis_flower_tp = 0;
    public long canafis_last_visited = 0;
    //ACTION CATHERBY
    public long catherby_allotment_north_tp = 0;
    public long catherby_allotment_south_tp = 0;
    public long catherby_herb_tp = 0;
    public long catherby_flower_tp = 0;
    public long catherby_last_visited = 0;
    //ACTION ARDOUGNE
    public long ardougne_allotment_north_tp = 0;
    public long ardougne_allotment_south_tp = 0;
    public long ardougne_herb_tp = 0;
    public long ardougne_flower_tp = 0;
    public long ardougne_last_visited = 0;
    //ACTION VARLAMORE
    public long varlamore_allotment_north_tp = 0;
    public long varlamore_allotment_south_tp = 0;
    public long varlamore_herb_tp = 0;
    public long varlamore_flower_tp = 0;
    public long varlamore_last_visited = 0;
    //ACTION HOSIDIUS
    public long hosidious_allotment_north_tp = 0;
    public long hosidious_allotment_south_tp = 0;
    public long hosidious_herb_tp = 0;
    public long hosidious_flower_tp = 0;
    public long hosidious_last_visited = 0;
    //ACTION FARMING GUILD
    public long farming_guild_allotment_north_tp = 0;
    public long farming_guild_allotment_south_tp = 0;
    public long farming_guild_herb_tp = 0;
    public long farming_guild_flower_tp = 0;
    public long farming_guild_last_visited = 0;
    //ACTION TROLLHEIM
    public long trollheim_herb_tp = 0;
    //ACTION WEISS
    public long weiss_herb_tp = 0;
    //ACTION FOSSIL ISLAND UNDER WATER
    public long giant_seaweed_one_tp = 0;
    public long giant_seaweed_two_tp = 0;

    FarmerSettings() {
    }


}
