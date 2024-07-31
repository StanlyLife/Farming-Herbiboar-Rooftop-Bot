package Utils.FarmingActions;

import Enums.CompostEnum;
import Enums.Flower.FlowerPatchesEnum;
import Enums.PatchStatus;
import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.Arrays;
import java.util.Objects;

import static Enums.Flower.FlowerSeeds.FlowerPlantNames;
import static Enums.Flower.FlowerSeeds.FlowerProduceNames;
import static Enums.PatchStatus.UNKNOWN;
import static Enums.Widgets.FarmingStoreEnum.OpenFarmingStore;
import static Settings.FarmerSettings.getSettings;
import static Utils.GeneralFunctions.SleepUntilSelected;

public class FarmingFlowers {

    public static int FarmFlowerPatch(FlowerPatchesEnum patch) {
        if(Inventory.isItemSelected()) Inventory.deselect();
        PatchStatus patchStatus = patch.getPatchStatus();
        Logger.log("Flower Patch status = " + patchStatus.name());

        String flowerProduce = getSettings().seedsToPlant.getFlowerSeeds().getProduceName();
        if(GroundItems.closest(flowerProduce) != null) {
            if(!Inventory.isFull()){
                for (GroundItem groundItem : GroundItems.all(flowerProduce)) {
                    if (Inventory.isFull()) break;
                    int inventoryProduce = Inventory.count(flowerProduce);
                    if (groundItem != null && groundItem.interact()) {
                        Sleep.sleep(500);
                        Sleep.sleepUntil(() -> Inventory.count(flowerProduce) > inventoryProduce, () -> Players.getLocal().isMoving(), 1000, 50, 10);
                    }
                }
            }

            Item produce = Inventory.get(i -> Arrays.stream(FlowerProduceNames).anyMatch(prod -> prod.equalsIgnoreCase(i.getName())));
            if(produce != null && Inventory.contains(i -> i.getID() == produce.getNotedItemID() - 1)) {
                Inventory.use(produce.getNotedItemID() - 1);
                SleepUntilSelected();
                if(!Inventory.isItemSelected()) return 0;
                int emptySlots = Inventory.getEmptySlots();
                NPCs.closest("Tool leprechaun").interact();
                Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
            }
            return 0;
        }

        if(patchStatus == PatchStatus.HARVEST) {
            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.HARVEST, 25000);
            patch.resetPatch();

            Item produce = Inventory.get(i -> Arrays.stream(FlowerProduceNames).anyMatch(prod -> prod.equalsIgnoreCase(i.getName())));
            if(produce != null && Inventory.contains(i -> i.getID() == produce.getNotedItemID() - 1)) {
                Inventory.use(produce.getNotedItemID() - 1);
                SleepUntilSelected();
                if(!Inventory.isItemSelected()) return 0;
                int emptySlots = Inventory.getEmptySlots();
                NPCs.closest("Tool leprechaun").interact();
                Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
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
                getPatch(patch).interact();
                Sleep.sleepUntil(() -> Inventory.count(compost.getItemName()) < amountOfCompostInInventory, 10000);
                patch.setCompostUsed(compost);
                Logger.log("Done composting");
                Inventory.dropAll("Bucket");
            }

            Inventory.use(getSettings().seedsToPlant.getFlowerSeeds().getSeedName());
            SleepUntilSelected();
            if(!Inventory.isItemSelected()) {
                Logger.log("Unable to select seed! - " + getSettings().seedsToPlant.getFlowerSeeds().getSeedName() + " - " + getSettings().seedsToPlant.getFlowerSeeds().name());
                return 0;
            }
            getPatch(patch).interact();
            Sleep.sleepUntil(() -> Inventory.isFull() ||  patch.getPatchStatus() != PatchStatus.PLANT, 25000);
            if(patch.getPatchStatus() == PatchStatus.PLANTED) {
                patch.setIsPlanted(true);
                patch.setTimePlanted(System.currentTimeMillis());
            }
        }

        Logger.log("End of farming flowers");
        return 1000;
    }

    public static GameObject getPatch(FlowerPatchesEnum flowerPatchEnum) {
        GameObject p = GameObjects.closest(patch ->
                Arrays.stream(FlowerPlantNames).anyMatch(apn -> ((
                        patch.getName().toLowerCase().contains(apn.toLowerCase()))
                    ||  patch.getName().toLowerCase().contains("flower patch")))
                    &&  patch.distance(flowerPatchEnum.getPatchTile()) < 3, flowerPatchEnum.getPatchTile()
        );

        if(p == null) {
            if(Walking.shouldWalk()) {
                Walking.walk(flowerPatchEnum.getPatchTile());
            }
            Logger.log("Unable to find the flower patch");
            return null;
        }
        return p;
    }

}
