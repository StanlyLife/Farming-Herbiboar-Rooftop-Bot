package Tasks.Leaves.SecondaryTasks.Herbiboar.HerbiInventoryHandler;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Utils.GeneralFunctions.SleepUntilSelected;

public class HerbiUimFullInvHandlerLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return true;
    }

    private static Tile toolLeprechaunTile = new Tile(3712, 3838);
    @Override
    public int onLoop() {
        Logger.log("Handle full inventory");

        if(toolLeprechaunTile.distance(Players.getLocal()) > 5) {
            if(Walking.shouldWalk()) {
                Walking.walk(toolLeprechaunTile);
            }
            return Calculations.random(24,267);
        }

        for(Item herb : Inventory.all(i -> i.getID() != i.getNotedItemID() && i.getName().startsWith("Grimy"))) {
            if(Inventory.isItemSelected()) Inventory.deselect();
            Inventory.use(herb.getID());
            SleepUntilSelected();
            Sleep.sleep(500);
            if(Inventory.isItemSelected()) {
                if(NPCs.closest("Tool Leprechaun").interact("Use")) Sleep.sleep(150,350);
            }
            else Sleep.sleep(250);
        }

        Inventory.dropAll(i ->
                (i.getName().startsWith("Unidentified") && i.getName().contains("fossil"))
            ||  i.getName().equalsIgnoreCase("Numulite")
        );

        Logger.log("Done cleaning inventory");
        return 50;
    }
}
