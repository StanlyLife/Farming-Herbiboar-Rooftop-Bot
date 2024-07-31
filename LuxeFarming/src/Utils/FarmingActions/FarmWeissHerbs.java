package Utils.FarmingActions;

import Enums.CompostEnum;
import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Objects;

import static Enums.PatchStatus.*;
import static Enums.Widgets.FarmingStoreEnum.OpenFarmingStore;
import static Settings.FarmerSettings.getSettings;
import static Utils.GeneralFunctions.SleepUntilSelected;

public class FarmWeissHerbs {
    public static int farmWeissHerbs(HerbPatchesEnum patch) {
        if(Inventory.isItemSelected()) Inventory.deselect();
        PatchStatus patchStatus = getWeissPatchStatus();
        Logger.log("HERB patch status = " + patchStatus.name());


        if(patchStatus == PatchStatus.HARVEST) {

            if(!Inventory.contains("Spade")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SPADE.WithdrawOne();
                return 250;
            }

            getTrollheimPatch().interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  getWeissPatchStatus() != PatchStatus.HARVEST, 25000);
            if(getWeissPatchStatus() == PatchStatus.HARVEST) return 0;
            patch.resetPatch();
            if (getWeissPatchStatus() == PatchStatus.HARVEST) return 150;
            int herbProduce = Inventory.get(i -> i.getName().toLowerCase().contains("grimy") && i.getID() != i.getNotedItemID()).getID();
            if(Inventory.contains(herbProduce)) {
                Inventory.use(herbProduce);
                SleepUntilSelected();
                int emptySlots = Inventory.getEmptySlots();
                NPCs.closest("Tool leprechaun").interact();
                Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
            }
            return 0;
        }

        if(patchStatus == PatchStatus.RAKE) {

            if(!Inventory.contains("Rake")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.RAKE.WithdrawOne();
                return 250;
            }

            getTrollheimPatch().interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  getWeissPatchStatus() != PatchStatus.RAKE, 25000);
            Inventory.dropAll("Weeds");
        }

        if(patchStatus == PatchStatus.CLEAR) {
            if(!Inventory.contains("Spade")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SPADE.WithdrawOne();
                return 250;
            }
            getTrollheimPatch().interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  getWeissPatchStatus() != PatchStatus.CLEAR, 25000);
        }
        if(patchStatus == PatchStatus.CURE) {
            if(!Inventory.contains("Plant cure")) {
                if(!FarmingStoreEnum.IsToolLeprechaunOpen()) {
                    Sleep.sleepUntil(() -> OpenFarmingStore(), 5000);
                    return 250;
                }

                Sleep.sleepUntil(() -> FarmingStoreEnum.PLANT_CURE.WithdrawOne(), 10000);
                return 250;
            }
            getTrollheimPatch().interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  getWeissPatchStatus() != PatchStatus.CURE, 25000);
            Inventory.dropAll("Vial");
        }

        if(patchStatus == PatchStatus.PLANT) {

            if(!Inventory.contains("Seed dibber")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SEED_DIBBER.WithdrawOne();
                return 250;
            }

            CompostEnum compost = CompostEnum.getCompostEnum(getSettings().CompostToUse);
            if(Inventory.count(compost.getItemName()) < 1 && !Objects.equals(compost.getItemName(), "")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.getFarmingStoreWidget(compost.getFarmingStoreItemName()).WithdrawOne();
                return 250;
            }

            boolean playerHasCompost = Inventory.contains(compost.getItemName());
            boolean patchIsNotTreated = patch.getCompostUsed() == CompostEnum.NONE;
            if(playerHasCompost && patchIsNotTreated) {
                int amountOfCompostInInventory = Inventory.count(compost.getItemName());
                Logger.log("Starting composting");

                Inventory.use(compost.getItemName());
                SleepUntilSelected();
                if(!Inventory.isItemSelected()) {
                    Logger.log("Unable to select compost! - " + compost.getItemName());
                    return 0;
                }
                getTrollheimPatch().interact();
                Sleep.sleepUntil(() -> Inventory.count(compost.getItemName()) < amountOfCompostInInventory, 10000);
                patch.setCompostUsed(compost);
                Logger.log("Done composting");
                Inventory.dropAll("Bucket");
            }

            int seedsCount =  Inventory.count(getSettings().seedsToPlant.getHerbSeeds().getSeedName());
            if(Inventory.get(getSettings().seedsToPlant.getHerbSeeds().getSeedName()).useOn(getTrollheimPatch()))
                Sleep.sleepUntil(() -> seedsCount < Inventory.count(getSettings().seedsToPlant.getHerbSeeds().getSeedName()), () -> Players.getLocal().isMoving(), 3000, 25,15);

            if(getWeissPatchStatus() == PatchStatus.PLANTED || seedsCount > Inventory.count(getSettings().seedsToPlant.getHerbSeeds().getSeedName())) {
                patch.setIsPlanted(true);
                patch.setTimePlanted(System.currentTimeMillis());
                getSettings().weiss_herb_tp = System.currentTimeMillis();
                int herbProduce = getSettings().seedsToPlant.getHerbSeeds().getProduceID();
                if(Inventory.contains(herbProduce)) {
                    Inventory.use( herbProduce);
                    SleepUntilSelected();
                    int emptySlots = Inventory.getEmptySlots();
                    NPCs.closest("Tool leprechaun").interact();
                    Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
                }
            }
        }
        Logger.log("End of farming herb");
        return 1000;
    }

    public static PatchStatus getWeissPatchStatus() {

        int bitValue =  PlayerSettings.getBitValue(4771);
        GameObject p = GameObjects.closest(patch -> patch.getName().equals("Herb patch") || patch.getName().toLowerCase().contains("herbs"), Players.getLocal().getTile());

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
        if(
                (!p.hasAction("Rake") && bitValue > 3)
        )
        {
            return PLANTED;
        }
        return PLANT;
    }

    public static GameObject getTrollheimPatch() {
        GameObject result = GameObjects.closest(g -> g.getName().contains("Herb patch") || g.getName().toLowerCase().contains("herbs"), Players.getLocal().getTile());
        if(result == null) {
            Logger.log("Unable to find herb patch");
            return null;
        }
        return  result;
    }


}
