package Tasks.Leaves.FarmRun.InventoryHandler;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import java.util.Arrays;

import static Areas.FarmingAreas.FossilIslandUnderWaterArea;
import static Enums.Allotment.AllotmentSeeds.AllotmentProduceNames;
import static Enums.Allotment.AllotmentSeeds.getAllotmentSeedByProduceName;
import static Enums.Flower.FlowerSeeds.FlowerProduceNames;
import static Settings.FarmerSettings.getSettings;
import static Utils.GeneralFunctions.SleepUntilSelected;

public class ClearInventoryLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Inventory.isFull();
    }

    @Override
    public int onLoop() {

        //ACTION - CLEAR INVENTORY
        ClearAllotmentProduce();
        NoteHerbProduce();
        NoteFlowerProduce();
        NoteGiantSeaweed();

        Inventory.dropAll("Weeds","Vial", "Bucket");

        return Calculations.random(0,125);
    }

    private static void NoteGiantSeaweed() {
        NPC leprechaun = NPCs.closest("Tool leprechaun");
        if(leprechaun == null) {
            Logger.error("Unable to find Leprechaun");
            if(Walking.shouldWalk()){
                Walking.walk(FossilIslandUnderWaterArea.getCenter());
            }
        }
        if(Inventory.get(i-> i.getName().equalsIgnoreCase("giant seaweed") && i.getNotedItemID() != i.getID()) != null &&Inventory.get(i-> i.getName().equalsIgnoreCase("giant seaweed") && i.getNotedItemID() != i.getID()).useOn(leprechaun)) Sleep.sleepUntil(() -> !Inventory.isFull() && !Players.getLocal().isMoving(), () -> Players.getLocal().isMoving() && Inventory.isFull(), 1500, 75, 10);
        else Logger.warn("Failed to note giant seaweed on tool leprechaun");
    }

    private static void NoteFlowerProduce() {
        Logger.log("Noting flower produce");
        Item flowerProduce = Inventory.get(i ->
                Arrays.stream(FlowerProduceNames).anyMatch(prod -> prod.equalsIgnoreCase(i.getName()))
            && i.getID() != i.getNotedItemID()
        );
        if(flowerProduce != null) {
            Inventory.use(flowerProduce.getID());
            SleepUntilSelected();
            int emptySlots = Inventory.getEmptySlots();
            NPCs.closest("Tool leprechaun").interact();
            Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
        }else {
            Logger.log("No flower produce");
        }
    }

    private static void NoteHerbProduce() {
        Logger.log("Noting herb produce");
        Item herbProduce = Inventory.get(i ->
                i.getName().toLowerCase().contains("grimy")
            &&  i.getID() != i.getNotedItemID());
        if(herbProduce != null) {
            Inventory.use(herbProduce.getID());
            SleepUntilSelected();
            int emptySlots = Inventory.getEmptySlots();
            NPCs.closest("Tool leprechaun").interact();
            Sleep.sleepUntil(() -> Inventory.getEmptySlots() > emptySlots, 10000 );
        }else {
            Logger.log("No herbs to note");
        }
    }


    private static void ClearAllotmentProduce() {
        Logger.log("Clearing allotment produce");

        Item InventoryProduce = Inventory.get(i ->
                Arrays.stream(AllotmentProduceNames).anyMatch(pn -> pn.equalsIgnoreCase(i.getName()))
                && i.getID() != i.getNotedItemID()
        );

        if(InventoryProduce != null && !getAllotmentSeedByProduceName(InventoryProduce.getName()).isNoteable()) {
            Inventory.dropAll(InventoryProduce.getName());
        }else if(InventoryProduce != null) {
            WalkToToolLeprechaun();
            NoteProduceOnToolLeprechaun(InventoryProduce);
        }
    }

    private static void NoteProduceOnToolLeprechaun(Item produce) {
        Logger.log("Noting produce on leprechaun");
        Inventory.use(produce);
        Sleep.sleepUntil(Inventory::isItemSelected, 1000);
        Sleep.sleep(50,250);
        NPCs.closest("Tool Leprechaun").interact();
        Sleep.sleepUntil(() -> !Inventory.isFull(), 2500);
    }

    private static void WalkToToolLeprechaun() {
        Logger.log("Walking to tool leprechaun");
        if(NPCs.closest("Tool Leprechaun") == null) {
            Walking.walk(getSettings().currentPatch.getArea().getCenter());
            Sleep.sleepUntil(() -> !Players.getLocal().isMoving(), 10000);
        }
        while(NPCs.closest("Tool Leprechaun").distance(Players.getLocal()) > 2) {
            if(Walking.shouldWalk()) {
                Walking.walk(NPCs.closest("Tool Leprechaun").getTile());
            }
            Sleep.sleepUntil(() -> !Inventory.isFull(), 10000);
            Sleep.sleep(50,400);
        }
    }

}
