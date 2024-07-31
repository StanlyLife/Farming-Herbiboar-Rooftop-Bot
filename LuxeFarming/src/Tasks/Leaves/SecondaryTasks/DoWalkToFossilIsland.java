package Tasks.Leaves.SecondaryTasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.ArrayList;
import java.util.List;

import static Areas.HerbiboarAreas.fossilIsland;
import static Settings.secondarySettings.getSecondSettings;

public class DoWalkToFossilIsland extends Leaf {
    @Override
    public boolean isValid() {
        return
                (
                getSecondSettings().action == "ash"
            ||  getSecondSettings().action == "herbi"
                )
            &&  !fossilIsland.contains(Players.getLocal());
    }
    private static Tile charterToFossilIslandTile = new Tile(3362, 3445);

    @Override
    public int onLoop() {

        if(!fossilIsland.contains(Players.getLocal())) {
            if(Players.getLocal().distance(charterToFossilIslandTile) > 5) {
                Logger.log("walking to charter ship man");
                if(Walking.shouldWalk()) Walking.walk(charterToFossilIslandTile);
                return Calculations.random(25,300);
            }else {
                Logger.log("Trying to charter");
                NPCs.closest("Barge guard").interact("Quick-Travel");
                Sleep.sleepUntil(() -> fossilIsland.contains(Players.getLocal()), 15000);
            }
            return Calculations.random(250,642);
        }

        return 0;
    }
}
