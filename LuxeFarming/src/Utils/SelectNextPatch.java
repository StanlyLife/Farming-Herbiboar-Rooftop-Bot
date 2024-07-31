package Utils;

import Enums.FarmingAreaEnum;
import Enums.Flower.FlowerSeeds;
import Enums.Herb.HerbSeeds;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.quest.book.PaidQuest;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;

import java.util.stream.Stream;

import static Enums.Allotment.AllotmentSeeds.NONE;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Settings.secondarySettings.getSecondSettings;

public class SelectNextPatch {

    public static int GetAmountOfReadyFarmingAreas() {
        return (int) Stream.of(
                        new FarmingArea(IsLocationReady(getSettings().draynor_allotment_north_tp,getSettings().draynor_allotment_south_tp, getSettings().draynor_flower_tp,getSettings().draynor_herb_tp), isAllPlanted(getSettings().draynor_allotment_north_tp, getSettings().draynor_allotment_south_tp, getSettings().draynor_flower_tp, getSettings().draynor_herb_tp), true),
                        new FarmingArea(IsLocationReady(getSettings().canafis_allotment_north_tp,getSettings().canafis_allotment_south_tp, getSettings().canafis_flower_tp,getSettings().canafis_herb_tp), isAllPlanted(getSettings().canafis_allotment_north_tp, getSettings().canafis_allotment_south_tp, getSettings().canafis_flower_tp, getSettings().canafis_herb_tp), PaidQuest.PRIEST_IN_PERIL.isFinished()),
                        new FarmingArea(IsLocationReady(getSettings().catherby_allotment_north_tp, getSettings().catherby_allotment_south_tp, getSettings().catherby_flower_tp, getSettings().catherby_herb_tp), isAllPlanted(getSettings().catherby_allotment_north_tp, getSettings().catherby_allotment_south_tp, getSettings().catherby_flower_tp, getSettings().catherby_herb_tp), true),
                        new FarmingArea(IsLocationReady(getSettings().ardougne_allotment_north_tp, getSettings().ardougne_allotment_south_tp, getSettings().ardougne_flower_tp, getSettings().ardougne_herb_tp), isAllPlanted(getSettings().ardougne_allotment_north_tp, getSettings().ardougne_allotment_south_tp, getSettings().ardougne_flower_tp, getSettings().ardougne_herb_tp), true),
                        new FarmingArea(IsLocationReady(getSettings().varlamore_allotment_north_tp, getSettings().varlamore_allotment_south_tp, getSettings().varlamore_flower_tp, getSettings().varlamore_herb_tp), isAllPlanted(getSettings().varlamore_allotment_north_tp, getSettings().varlamore_allotment_south_tp, getSettings().varlamore_flower_tp, getSettings().varlamore_herb_tp), PaidQuest.CHILDREN_OF_THE_SUN.isFinished()),
                        new FarmingArea(IsLocationReady(getSettings().hosidious_allotment_north_tp, getSettings().hosidious_allotment_south_tp, getSettings().hosidious_flower_tp, getSettings().hosidious_herb_tp), isAllPlanted(getSettings().hosidious_allotment_north_tp, getSettings().hosidious_allotment_south_tp, getSettings().hosidious_flower_tp, getSettings().hosidious_herb_tp), PaidQuest.CLIENT_OF_KOUREND.isFinished()),
                        new FarmingArea(IsFarmingGuildReady(), isFarmingGuildPlanted(getSettings().farming_guild_allotment_north_tp, getSettings().farming_guild_allotment_south_tp, getSettings().farming_guild_flower_tp, getSettings().farming_guild_herb_tp), Skill.FARMING.getLevel() >= 45),
                        new FarmingArea(isPatchReady(getSettings().trollheim_herb_tp, getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow()), getSettings().trollheim_herb_tp != 0, PaidQuest.MY_ARMS_BIG_ADVENTURE.isFinished()),
                        new FarmingArea(isPatchReady(getSettings().giant_seaweed_one_tp, 40), getSettings().giant_seaweed_one_tp != 0, PaidQuest.BONE_VOYAGE.isFinished()),
                        new FarmingArea(isPatchReady(getSettings().weiss_herb_tp, getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow()), getSettings().weiss_herb_tp != 0, PaidQuest.MAKING_FRIENDS_WITH_MY_ARM.isFinished())
                )
                .filter(farmingArea -> farmingArea.isReady() && farmingArea.isPlanted() && farmingArea.shouldDo())
                .count();
    }
    private static class FarmingArea {
        private final boolean ready;
        private final boolean planted;
        private final boolean shouldDo;

        public FarmingArea(boolean ready, boolean planted, boolean shouldDo) {
            this.ready = ready;
            this.planted = planted;
            this.shouldDo = shouldDo;
        }

        public boolean isReady() {
            return ready;
        }

        public boolean isPlanted() {
            return planted;
        }

        public boolean shouldDo() {
            return shouldDo;
        }
    }
    public static void SelectNextFarmingArea() {

        //if all patches are over grown time at area
        //and player is not currently in area
        boolean draynor = IsLocationReady(getSettings().draynor_allotment_north_tp,getSettings().draynor_allotment_south_tp, getSettings().draynor_flower_tp,getSettings().draynor_herb_tp);
        boolean draynorIsPlanted = isAllPlanted(getSettings().draynor_allotment_north_tp,getSettings().draynor_allotment_south_tp, getSettings().draynor_flower_tp,getSettings().draynor_herb_tp);

        boolean canafis = IsLocationReady(getSettings().canafis_allotment_north_tp,getSettings().canafis_allotment_south_tp, getSettings().canafis_flower_tp,getSettings().canafis_herb_tp);
        boolean canafisIsPlanted = isAllPlanted(getSettings().canafis_allotment_north_tp,getSettings().canafis_allotment_south_tp, getSettings().canafis_flower_tp,getSettings().canafis_herb_tp);
        boolean shouldDoCanafis = PaidQuest.PRIEST_IN_PERIL.isFinished(); //TODO FIX

        boolean catherby = IsLocationReady(getSettings().catherby_allotment_north_tp,getSettings().catherby_allotment_south_tp, getSettings().catherby_flower_tp,getSettings().catherby_herb_tp);
        boolean catherbyIsPlanted = isAllPlanted(getSettings().catherby_allotment_north_tp,getSettings().catherby_allotment_south_tp, getSettings().catherby_flower_tp,getSettings().catherby_herb_tp);

        boolean ardougne = IsLocationReady(getSettings().ardougne_allotment_north_tp,getSettings().ardougne_allotment_south_tp, getSettings().ardougne_flower_tp,getSettings().ardougne_herb_tp);
        boolean ardougneIsPlanted = isAllPlanted(getSettings().ardougne_allotment_north_tp,getSettings().ardougne_allotment_south_tp, getSettings().ardougne_flower_tp,getSettings().ardougne_herb_tp);

        boolean varlamore = IsLocationReady(getSettings().varlamore_allotment_north_tp,getSettings().varlamore_allotment_south_tp, getSettings().varlamore_flower_tp,getSettings().varlamore_herb_tp);
        boolean varlamoreIsPlanted = isAllPlanted(getSettings().varlamore_allotment_north_tp,getSettings().varlamore_allotment_south_tp, getSettings().varlamore_flower_tp,getSettings().varlamore_herb_tp);
        boolean shouldDoVarlamore = PaidQuest.CHILDREN_OF_THE_SUN.isFinished(); //TODO FIX

        boolean hosidus = IsLocationReady(getSettings().hosidious_allotment_north_tp,getSettings().hosidious_allotment_south_tp, getSettings().hosidious_flower_tp,getSettings().hosidious_herb_tp);
        boolean hosidusIsPlanted = isAllPlanted(getSettings().hosidious_allotment_north_tp,getSettings().hosidious_allotment_south_tp, getSettings().hosidious_flower_tp,getSettings().hosidious_herb_tp);
        boolean shouldDoHosidius = PaidQuest.CLIENT_OF_KOUREND.isFinished();

        boolean farming_guild = IsFarmingGuildReady();
        boolean farming_guildIsPlanted = isFarmingGuildPlanted(getSettings().farming_guild_allotment_north_tp,getSettings().farming_guild_allotment_south_tp, getSettings().farming_guild_flower_tp,getSettings().farming_guild_herb_tp);
        boolean shouldDoFarmingGuild = Skill.FARMING.getLevel() >= 45;


        boolean trollheimIsPlanted = getSettings().trollheim_herb_tp != 0;
        boolean trollheim = isPatchReady(getSettings().trollheim_herb_tp, getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow());
        boolean shouldDoTrollheim = PaidQuest.MY_ARMS_BIG_ADVENTURE.isFinished();

        boolean weissIsPlanted = getSettings().weiss_herb_tp != 0;
        boolean weiss = isPatchReady(getSettings().weiss_herb_tp, getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow());
        boolean shouldDoWeiss = PaidQuest.MAKING_FRIENDS_WITH_MY_ARM.isFinished();

        boolean seaweedOneIsPlanted = getSettings().giant_seaweed_one_tp != 0;
        boolean seaweedTwoIsPlanted = getSettings().giant_seaweed_two_tp != 0;
        boolean seaweedOne = isPatchReady(getSettings().giant_seaweed_one_tp, 40);
        boolean seaweedTwo = isPatchReady(getSettings().giant_seaweed_two_tp, 40);
        boolean shouldDoSeaweed = PaidQuest.BONE_VOYAGE.isFinished() && Inventory.contains("Seaweed spore");


        Logger.log("Draynor       is " + (draynor ? "Ready" : "Not ready") + " ::: is planted : " + (draynorIsPlanted ? "its planted" : "not planted"));
        Logger.log("Canafis       is " + (canafis ? "Ready" : "Not ready") + " ::: is planted : " + (canafisIsPlanted ? "its planted" : "not planted") + " ::: ShouldDoPatch? : " + (shouldDoCanafis ? "yes" : "no"));
        Logger.log("catherby      is " + (catherby ? "Ready" : "Not ready") + " ::: is planted : " + (catherbyIsPlanted ? "its planted" : "not planted"));
        Logger.log("ardougne      is " + (ardougne ? "Ready" : "Not ready") + " ::: is planted : " + (ardougneIsPlanted ? "its planted" : "not planted"));
        Logger.log("varlamore     is " + (varlamore ? "Ready" : "Not ready") + " ::: is planted : " + (varlamoreIsPlanted ? "its planted" : "not planted") + " ::: ShouldDoPatch? : " + (shouldDoVarlamore ? "yes" : "no"));
        Logger.log("hosidus       is " + (hosidus ? "Ready" : "Not ready") + " ::: is planted : " + (hosidusIsPlanted ? "its planted" : "not planted")+ " ::: ShouldDoPatch? : " + (shouldDoCanafis ? "yes" : "no"));
        Logger.log("trollheim     is " + (trollheim ? "Ready" : "Not ready") + " ::: is planted : " + (trollheimIsPlanted ? "its planted" : "not planted")+ " ::: ShouldDoPatch? : " + (shouldDoTrollheim ? "yes" : "no"));
        Logger.log("weiss         is " + (weiss ? "Ready" : "Not ready") + " ::: is planted : " + (weissIsPlanted ? "its planted" : "not planted")+ " ::: ShouldDoPatch? : " + (shouldDoWeiss ? "yes" : "no"));
        Logger.log("farming guild is " + (farming_guild ? "Ready" : "Not ready") + " ::: is planted : " + (farming_guildIsPlanted ? "its planted" : "not planted") + " ::: ShouldDoPatch? : " + (shouldDoFarmingGuild ? "yes" : "no"));
        Logger.log("seaweed is " + (seaweedOne && seaweedTwo ? "Ready" : "Not ready") + " ::: is planted : " + (seaweedTwoIsPlanted && seaweedOneIsPlanted ? "its planted" : "not planted") + " ::: ShouldDoPatch? : " + (shouldDoSeaweed ? "yes" : "no"));
        if(((seaweedOne && seaweedTwo) || !seaweedOneIsPlanted)  && shouldDoSeaweed) {
            getSettings().currentPatch = FarmingAreaEnum.FOSSIL_ISLAND_SEAWEED;
            getSettings().currentPatchString = FarmingAreaEnum.FOSSIL_ISLAND_SEAWEED.name();
        }

        else if(draynor || !draynorIsPlanted) {
            getSettings().currentPatch = FarmingAreaEnum.DRAYNOR;
            getSettings().currentPatchString = FarmingAreaEnum.DRAYNOR.name();
        }
        else if(catherby || !catherbyIsPlanted) {
            getSettings().currentPatch = FarmingAreaEnum.CATHERBY;
            getSettings().currentPatchString = FarmingAreaEnum.CATHERBY.name();
        }
        else if(ardougne || !ardougneIsPlanted) {
            getSettings().currentPatch = FarmingAreaEnum.ARDOUGNE;
            getSettings().currentPatchString = FarmingAreaEnum.ARDOUGNE.name();
        }
        else if((varlamore || !varlamoreIsPlanted)          && shouldDoVarlamore)  {
            getSettings().currentPatch = FarmingAreaEnum.VARLAMORE;
            getSettings().currentPatchString = FarmingAreaEnum.VARLAMORE.name();
        }
        else if((hosidus || !hosidusIsPlanted)              && shouldDoHosidius) {
            getSettings().currentPatch = FarmingAreaEnum.HOSIDIOUS;
            getSettings().currentPatchString = FarmingAreaEnum.HOSIDIOUS.name();
        }
        else if((farming_guild || !farming_guildIsPlanted)  && shouldDoFarmingGuild) {
            getSettings().currentPatch = FarmingAreaEnum.FARMING_GUILD;
            getSettings().currentPatchString = FarmingAreaEnum.FARMING_GUILD.name();
        }else if((canafis || !canafisIsPlanted)                   && shouldDoCanafis) {
            getSettings().currentPatch = FarmingAreaEnum.CANAFIS;
            getSettings().currentPatchString = FarmingAreaEnum.CANAFIS.name();
        }
        else if((trollheim || !trollheimIsPlanted)  && shouldDoTrollheim) {
            getSettings().currentPatch = FarmingAreaEnum.TROLLHEIM;
            getSettings().currentPatchString = FarmingAreaEnum.TROLLHEIM.name();
        }else if((weiss || !weissIsPlanted)  && shouldDoWeiss) {
            getSettings().currentPatch = FarmingAreaEnum.WEISS;
            getSettings().currentPatchString = FarmingAreaEnum.WEISS.name();
        }

        else {

            getSettings().currentPatchString = FarmingAreaEnum.NONE.name();
            getSettings().currentPatch = FarmingAreaEnum.NONE;
            ScriptSettings.save(getSettings(), SAVENAME);
            Logger.log("No patch selected");
            Logger.log("Setting secondary action");
            getSecondSettings().action = "rooftop";
            return;
        }
        getSecondSettings().action = "";
        Logger.log("----------------------------------------------------");
        Logger.log("Current patch: " + getSettings().currentPatch.name());
        Logger.log("----------------------------------------------------");

    }


    private static boolean isPatchReady(long timePlanted, int timeToGrow) {
        long currentTime = System.currentTimeMillis();
        return timeWhenIsDoneGrowing(timePlanted,timeToGrow) < currentTime;
    }

    private static boolean isFarmingGuildPlanted(long allotmentNorth, long allotmentSouth, long flower, long herb) {
        boolean shouldPlantAllotments = getSettings().seedsToPlant.getAllotmentSeed() != NONE;
        boolean northAllotmentIsPlanted = !shouldPlantAllotments ||  allotmentNorth != 0;
        boolean southAllotmentIsPlanted = !shouldPlantAllotments ||  allotmentSouth != 0;
        boolean allotmentsPlanted = northAllotmentIsPlanted && southAllotmentIsPlanted;


        boolean shouldPlantFlowers = getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.NONE;
        boolean flowerIsPlanted = !shouldPlantFlowers || flower != 0;


        boolean shouldPlantHerbs = getSettings().seedsToPlant.getHerbSeeds() != HerbSeeds.NONE && Skill.FARMING.getLevel() >= 65;
        boolean herbIsPlanted = shouldPlantHerbs && herb != 0;

        if(shouldPlantHerbs) return allotmentsPlanted && flowerIsPlanted && herbIsPlanted;
        else return allotmentsPlanted && flowerIsPlanted;
    }

    private static boolean isAllPlanted(long allotmentNorth, long allotmentSouth, long flower, long herb) {
        boolean shouldPlantAllotments = getSettings().seedsToPlant.getAllotmentSeed() != NONE;
        boolean northAllotmentIsPlanted = !shouldPlantAllotments || allotmentNorth != 0;
        boolean southAllotmentIsPlanted = !shouldPlantAllotments || allotmentSouth != 0;
        boolean allotmentsPlanted = northAllotmentIsPlanted && southAllotmentIsPlanted;


        boolean shouldPlantFlowers = getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.NONE;
        boolean flowerIsPlanted = !shouldPlantFlowers || flower != 0;


        boolean shouldPlantHerbs = getSettings().seedsToPlant.getHerbSeeds() != HerbSeeds.NONE;
        boolean herbIsPlanted = shouldPlantHerbs && herb != 0;

        return allotmentsPlanted && flowerIsPlanted && herbIsPlanted;
    }

    private static boolean IsLocationReady(long north_allotment, long south_allotment, long flower, long herb) {
        long currentTime = System.currentTimeMillis();

        boolean shouldCheckAllotments = getSettings().seedsToPlant.getAllotmentSeed() != NONE;
        boolean allotmentSeedsReady =
                timeWhenIsDoneGrowing(north_allotment,getSettings().seedsToPlant.getAllotmentSeed().getTimeToGrow()) < currentTime
                        && timeWhenIsDoneGrowing(south_allotment,getSettings().seedsToPlant.getAllotmentSeed().getTimeToGrow()) < currentTime;

        boolean shouldCheckFlowers = getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.NONE && getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.WHITE_LILY;
        boolean flowerSeedsReady = getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.NONE && getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.WHITE_LILY
                && timeWhenIsDoneGrowing(flower,getSettings().seedsToPlant.getFlowerSeeds().getTimeToGrow()) < currentTime;


        boolean HerbSeedsReady = getSettings().seedsToPlant.getHerbSeeds() != HerbSeeds.NONE
                && timeWhenIsDoneGrowing(herb,getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow()) < currentTime;

        return (!shouldCheckAllotments || allotmentSeedsReady) && (!shouldCheckFlowers || flowerSeedsReady) && HerbSeedsReady;
    }


    private static boolean IsFarmingGuildReady() {
        long currentTime = System.currentTimeMillis();
        boolean shouldCheckAllotments = getSettings().seedsToPlant.getAllotmentSeed() != NONE;
        boolean allotmentSeedsReady =
                timeWhenIsDoneGrowing(getSettings().farming_guild_allotment_north_tp,getSettings().seedsToPlant.getAllotmentSeed().getTimeToGrow()) < currentTime
                        && timeWhenIsDoneGrowing(getSettings().farming_guild_allotment_south_tp,getSettings().seedsToPlant.getAllotmentSeed().getTimeToGrow()) < currentTime;
        Logger.log("farming guild allotment seeds ready: " + (allotmentSeedsReady ? "yes" : "no"));

        boolean flowerSeedsReady = getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.NONE && getSettings().seedsToPlant.getFlowerSeeds() != FlowerSeeds.WHITE_LILY
                && timeWhenIsDoneGrowing(getSettings().farming_guild_flower_tp,getSettings().seedsToPlant.getFlowerSeeds().getTimeToGrow()) < currentTime;
        Logger.log("farming guild flower seeds ready: " + (flowerSeedsReady ? "yes" : "no"));

        boolean HerbSeedsReady = getSettings().seedsToPlant.getHerbSeeds() != HerbSeeds.NONE
                && timeWhenIsDoneGrowing(getSettings().farming_guild_herb_tp,getSettings().seedsToPlant.getHerbSeeds().getTimeToGrow()) < currentTime;
        Logger.log("farming guild herb seeds ready: " + (HerbSeedsReady ? "yes" : "no"));

        if(Skill.FARMING.getLevel() >= 65) return (!shouldCheckAllotments || allotmentSeedsReady) && flowerSeedsReady && HerbSeedsReady;
        else return (!shouldCheckAllotments || allotmentSeedsReady) && flowerSeedsReady;
    }


    private static long timeWhenIsDoneGrowing(long timePlanted,int timeToGrow) {
        int minutesToMillisecondsMultiplier = 60000;
        return (timePlanted + ((long) timeToGrow * minutesToMillisecondsMultiplier));
    }

}
