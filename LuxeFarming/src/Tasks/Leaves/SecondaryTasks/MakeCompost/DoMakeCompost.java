package Tasks.Leaves.SecondaryTasks.MakeCompost;

import Enums.Widgets.FarmingStoreEnum;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.helpers.ItemProcessing;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import static Areas.FarmingAreas.getClosestPatch;
import static Enums.Widgets.FarmingStoreEnum.IsToolLeprechaunOpen;
import static Enums.Widgets.FarmingStoreEnum.OpenFarmingStore;

public class DoMakeCompost extends Leaf {
    @Override
    public boolean isValid() {
        return true;
//        return NPCs.closest("Tool leprechaun") != null && NPCs.closest("Tool leprechaun").distance() < 15;
    }


    Tile closestPatch = getClosestPatch();
    @Override
    public int onLoop() {
        if(NPCs.closest("Tool leprechuan") != null && NPCs.closest("Tool leprechaun").distance() > 10) {
            if(Walking.shouldWalk() ){
                Walking.walk(closestPatch);
            }
            Logger.log("Tried walking");
            return Calculations.random(54, 432);
        }



        if(FarmingStoreEnum.IsToolLeprechaunOpen() && !FarmingStoreEnum.SUPERCOMPOST.hasInStore() || FarmingStoreEnum.ULTRACOMPOST.getItemsInStore() == 1000) {
            Logger.log("No supercompost left");
            return 10000;
        }


        if(Inventory.isFull() || Inventory.contains("Supercompost")) {
            if(!IsToolLeprechaunOpen()) {
                OpenFarmingStore();
                return 50;
            }
            if(!Inventory.contains("Supercompost") && Inventory.contains("Ultracompost")) {
                FarmingStoreEnum.ULTRACOMPOST.DespositAll();
                return 100;
            }

            Logger.log("Combining to make compost");
            Inventory.combine("Supercompost", "Volcanic ash");
            Sleep.sleepUntil(() -> ItemProcessing.isOpen(), 2000);
            if(!ItemProcessing.isOpen()) return 0;
            ItemProcessing.makeAll("Ultracompost");
            Sleep.sleepUntil(() -> !Inventory.contains("Supercompost") || !Inventory.contains("Volcanic ash"), () -> Players.getLocal().getAnimation() == 7699, 5000, 10, 75);
            Logger.log("Made compost");
            return 0;
        }

        if(IsToolLeprechaunOpen()) {

            if(FarmingStoreEnum.SUPERCOMPOST.WithdrawMax()) Sleep.sleepUntil(() -> Inventory.isFull(), 500);
            Logger.log("Made compost");
            return 0;
        }

        if(!FarmingStoreEnum.IsToolLeprechaunOpen()) {
            Logger.log("opening store");
            Sleep.sleepUntil(() -> OpenFarmingStore(), 2500);
            return Calculations.random(54, 432);
        }

        return Calculations.random(54, 432);
    }
}
