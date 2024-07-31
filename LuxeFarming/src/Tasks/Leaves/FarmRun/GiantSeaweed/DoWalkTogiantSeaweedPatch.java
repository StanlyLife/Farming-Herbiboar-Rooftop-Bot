package Tasks.Leaves.FarmRun.GiantSeaweed;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.GameObject;

import static Areas.FarmingAreas.FossilIslandUnderWaterArea;
import static Areas.FarmingAreas.fossil_island_seaweed_under_water_patch_one;

public class DoWalkTogiantSeaweedPatch extends Leaf {
    @Override
    public boolean isValid() {
        return !FossilIslandUnderWaterArea.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        GameObject patch = GameObjects.closest(i -> i.getName().toLowerCase().contains("seaweed") && i.distance(fossil_island_seaweed_under_water_patch_one) <= 1, fossil_island_seaweed_under_water_patch_one);
        if(fossil_island_seaweed_under_water_patch_one.distance(Players.getLocal()) > 5 || patch == null){
            if(Walking.shouldWalk()){
                Walking.walk(fossil_island_seaweed_under_water_patch_one);
                if(fossil_island_seaweed_under_water_patch_one.distance(Players.getLocal()) <= 1 && patch == null) {
                }
                Logger.error("Unable to find patch : " + Players.getLocal().getTile());
                return 100;
            }
        }
        return Calculations.random(50,200);
    }
}
