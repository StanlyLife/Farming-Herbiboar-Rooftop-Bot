package Utils.FarmingActions;

import Enums.CompostEnum;
import Enums.Herb.HerbPatchesEnum;
import Enums.PatchStatus;
import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.Objects;

import static Areas.FarmingAreas.*;
import static Enums.PatchStatus.*;
import static Enums.PatchStatus.PLANT;
import static Settings.FarmerSettings.SAVENAME;
import static Settings.FarmerSettings.getSettings;
import static Utils.GeneralFunctions.SleepUntilSelected;
import static org.dreambot.api.script.ScriptManager.getScriptManager;

public class FarmGiantSeaweed {


    
    public static int FarmGianSeaweed(Tile patchTile) {






        GroundItem seaweedspores = GroundItems.closest("Seaweed spore");
        if(seaweedspores != null) {
            int inventorySpores = Inventory.count("Seaweed spore");
            if(seaweedspores.interact()) Sleep.sleepUntil(() -> Inventory.count("Seaweed spore") > inventorySpores, () -> Players.getLocal().isMoving(), 2500, 25,10);
            else Logger.warn("Unable to pickup spores");
            return 100;
        }
        GameObject patch = GameObjects.closest(i -> i.getName().toLowerCase().contains("seaweed") && i.distance(patchTile) <= 1, patchTile);
        PatchStatus status = getPatchStatus(patch);
        Logger.log("STATUS = " + status);

        if(Inventory.isFull() && Inventory.contains(i-> i.getName().equalsIgnoreCase("giant seaweed") && i.getNotedItemID() != i.getID())) {
            NPC leprechaun = NPCs.closest(7757);
            if(leprechaun == null) {
                Logger.error("Unable to find Leprechaun");
                if(Walking.shouldWalk()){
                    Walking.walk(patchTile);
                }
                return Calculations.random(50,200);
            }
            if(Inventory.get(i-> i.getName().equalsIgnoreCase("giant seaweed") && i.getNotedItemID() != i.getID()).useOn(leprechaun)) Sleep.sleepUntil(() -> !Inventory.isFull() && !Players.getLocal().isMoving(), () -> Players.getLocal().isMoving() && Inventory.isFull(), 1500, 75, 10);
            else Logger.warn("Failed to note giant seaweed on tool leprechaun");
            return 0;
        }

        if(status == UNKNOWN)
        {
            Logger.error("Unable to find patch status : " + Players.getLocal().getTile());
            if(FossilIslandUnderWaterArea.contains(Players.getLocal())) {
                Walking.walk(patchTile);
            }
        }

        if (status == PLANTED) {
            if(patchTile.equals(fossil_island_seaweed_under_water_patch_one) && getSettings().giant_seaweed_one_tp == 0) getSettings().giant_seaweed_one_tp = System.currentTimeMillis();
            if(patchTile.equals(fossil_island_seaweed_under_water_patch_two) && getSettings().giant_seaweed_two_tp == 0 ) getSettings().giant_seaweed_two_tp = System.currentTimeMillis();
            ScriptSettings.save(getSettings(), SAVENAME);
            return Calculations.random(50,200);
        }

        if(status == RAKE) {
            if(!Inventory.contains("Rake")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,() -> Players.getLocal().isMoving(),1500, 25,20);
                FarmingStoreEnum.RAKE.WithdrawOne();
                return 250;
            }
            if(patch.interact()) Sleep.sleepUntil(() -> !GameObjects.closest(i -> i.getName().contains("seaweed"), patchTile).hasAction("Rake"), () -> Players.getLocal().isMoving(), 15000, 25, 30);
            else Logger.warn("failed to rake patch");
            return 0;
        }

        if(status == HARVEST) {
            if(patch.interact()) Sleep.sleepUntil(() -> !GameObjects.closest(i -> i.getName().toLowerCase().contains("seaweed"), patchTile).hasAction("Pick") || Inventory.isFull(),10000);
            else Logger.warn("failed to harvest patch");
            return 0;
        }

        if(status == CLEAR) {
            if(!Inventory.contains("Spade")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SPADE.WithdrawOne();
                return 250;
            }
            if(patch.interact()) Sleep.sleepUntil(() -> !GameObjects.closest(i -> i.getName().contains("seaweed"), patchTile).hasAction("Clear"), ()-> Players.getLocal().isMoving() ,15000, 25, 10);
            else Logger.warn("failed to clear patch");
            return 0;
        }

        if(status == PLANT) {

            if(!Inventory.contains("Seaweed spore")) {
                Logger.error("Inventory doesnt contain seaweed spore");
                getScriptManager().stop();
            }

            //DIBBER
            if(!Inventory.contains("Seed dibber")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.SEED_DIBBER.WithdrawOne();
                return 250;
            }
            //COMPOST
            CompostEnum compost = CompostEnum.getCompostEnum(getSettings().CompostToUse);
            if(Inventory.count(compost.getItemName()) < 1 && !Objects.equals(compost.getItemName(), "")) {
                Sleep.sleepUntil(FarmingStoreEnum::OpenFarmingStore,15000);
                FarmingStoreEnum.getFarmingStoreWidget(compost.getFarmingStoreItemName()).WithdrawOne();
                Sleep.sleepUntil(() -> Inventory.contains(getSettings().CompostToUse), 1000);
                return 250;
            }

            boolean playerHasCompost = Inventory.contains(compost.getItemName());
            if(!playerHasCompost) {
                return 250;
            }
            if(getSettings().CompostToUse != "") {
                int amountOfCompostInInventory = Inventory.count(compost.getItemName());
                Logger.log("Starting composting");
                Inventory.use(compost.getItemName());
                SleepUntilSelected();
                if(!Inventory.isItemSelected()) return 0;
                if(patch.interact()) Sleep.sleepUntil(() -> Inventory.count(compost.getItemName()) < amountOfCompostInInventory, 10000);
                else Logger.warn("failed to compost");
                Logger.log("Done composting");
                Inventory.dropAll("Bucket");
            }


            if(Inventory.get("Seaweed spore").useOn(patch)) {
                Sleep.sleepUntil(() -> GameObjects.closest(i -> i.getName().toLowerCase().contains("seaweed"), patchTile).getName().equalsIgnoreCase("seaweed"), () -> Players.getLocal().isMoving(), 1500, 25, 10);
                if(patchTile.equals(fossil_island_seaweed_under_water_patch_one)) getSettings().giant_seaweed_one_tp = System.currentTimeMillis();
                if(patchTile.equals(fossil_island_seaweed_under_water_patch_two)) getSettings().giant_seaweed_two_tp = System.currentTimeMillis();
                ScriptSettings.save(getSettings(), SAVENAME);
            }
            else Logger.warn("failed to seaweed spore");
            return 750;
        }

        Logger.log("End of function");

        return Calculations.random(50,200);
    }

    private static PatchStatus getPatchStatus(GameObject patch) {
        if(patch == null) {
            Logger.log("UNABLE TO FIND ALLOTMENT PATCH");
            return  UNKNOWN;
        }
        if(patch.hasAction("Rake")) {
            return RAKE;
        }
        if(patch.hasAction("Pick")) {
            return HARVEST;
        }
        if(patch.hasAction("Clear")) {
            return CLEAR;
        }
        if(patch.hasAction("Cure")) {
            return CURE;
        }
        if(patch.hasAction("Rake")) {
            return RAKE;
        }
        if(patch.getName().equalsIgnoreCase("seaweed"))
        {
            return PLANTED;
        }
        Logger.log("Can plant patch " + patch.getName());
        return PLANT;

    }
    
}
