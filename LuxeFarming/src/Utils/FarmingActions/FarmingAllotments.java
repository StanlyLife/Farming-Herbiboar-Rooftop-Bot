package Utils.FarmingActions;

import Enums.Allotment.AllotmentPatchEnum;
import Enums.CompostEnum;
import Enums.PatchStatus;
import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.util.Arrays;
import java.util.Objects;

import static Enums.Allotment.AllotmentPatchEnum.getClosestAllotmentPatch;
import static Enums.Allotment.AllotmentSeeds.AllotmentPlantNames;
import static Enums.Allotment.AllotmentSeeds.AllotmentProduceNames;
import static Enums.Widgets.FarmingStoreEnum.OpenFarmingStore;
import static Settings.FarmerSettings.getSettings;
import static Utils.GeneralFunctions.SleepUntilSelected;
import static org.dreambot.api.script.ScriptManager.getScriptManager;

public class FarmingAllotments {


    public static int FarmAllotment(AllotmentPatchEnum patch) {
        if(Inventory.isItemSelected()) Inventory.deselect();
        PatchStatus patchStatus = patch.getPatchStatus();
        Logger.log("Patch status = " + patchStatus.name());


        if(patchStatus == PatchStatus.HARVEST) {

            if(!Inventory.contains("Spade")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SPADE.WithdrawOne();
                return 0;
            }

            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.HARVEST, 25000);
            patch.resetPatch();


            Item InventoryProduce = Inventory.get(i ->
                    Arrays.stream(AllotmentProduceNames).anyMatch(pn -> pn.equalsIgnoreCase(i.getName()))
                            && i.getID() != i.getNotedItemID()
            );
            if(Inventory.contains(InventoryProduce)) {
                NoteProduceOnToolLeprechaun(InventoryProduce);
            }
        }

        if(patchStatus == PatchStatus.RAKE) {

            if(!Inventory.contains("Rake")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.RAKE.WithdrawOne();
                return 250;
            }

            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.RAKE, 25000);
            Inventory.dropAll("Weeds");
        }

        if(patchStatus == PatchStatus.CLEAR) {
            if(!Inventory.contains("Spade")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SPADE.WithdrawOne();
                return 250;
            }
            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.CLEAR, 25000);
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
            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.CURE, 25000);
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
            //TODO SET CURRENT PATCH
            boolean patchIsNotTreated = getClosestAllotmentPatch(patch.getPatchTile()).getCompostUsed() == CompostEnum.NONE;
            if(playerHasCompost && patchIsNotTreated) {
                int amountOfCompostInInventory = Inventory.count(compost.getItemName());
                Logger.log("Starting composting");

                Inventory.use(compost.getItemName());
                SleepUntilSelected();
                if(!Inventory.isItemSelected()) return 0;
                getPatch(patch).interact();
                Sleep.sleepUntil(() -> Inventory.count(compost.getItemName()) < amountOfCompostInInventory, 10000);
                patch.setCompostUsed(compost);
                Logger.log("Done composting");
                Inventory.dropAll("Bucket");
            }

            Inventory.use(getSettings().seedsToPlant.getAllotmentSeed().getSeedName());
            if(!Inventory.contains(getSettings().seedsToPlant.getAllotmentSeed().getSeedName())) {
                Logger.log("Inventory does not contain " + getSettings().seedsToPlant.getAllotmentSeed().getSeedName());
                getScriptManager().stop();
            }
            SleepUntilSelected();
            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.PLANT, 25000);
            if(patch.getPatchStatus() == PatchStatus.PLANTED) {
                patch.setIsPlanted(true);
                patch.setTimePlanted(System.currentTimeMillis());
            }
        }



        Logger.log("End of farming allotment");
        return 1000;
    }

    private static void NoteProduceOnToolLeprechaun(Item produce) {
        Logger.log("Noting produce on leprechaun");
        NPC leprechaun = NPCs.closest("Tool Leprechaun");
        if(leprechaun != null &&  produce.useOn(leprechaun)) {
                Sleep.sleepUntil(() -> !Inventory.contains(produce.getID()),()-> Players.getLocal().isMoving() , 2500, 25 , 10);
        }
    }

    public static GameObject getPatch(AllotmentPatchEnum patch) {
        GameObject closestPatch = GameObjects.closest(p ->
                        (p.getName().equals("Allotment")
                    ||  Arrays.stream(AllotmentPlantNames).anyMatch(apn ->  p.getName().toLowerCase().contains(apn.toLowerCase())))
                    && getSettings().currentPatch.getArea().contains(p.getTile())
                    && p.distance(patch.getPatchTile()) <= 1
                , patch.getPatchTile());
        if(closestPatch == null) {
            Logger.log(Logger.LogType.WARN,"ERROR: Unable to find closest patch! " + patch.name());
        }else {
            Logger.log("Found allotment patch to interact with at: " + closestPatch.getTile());
        }
        return  closestPatch;
    }

}
