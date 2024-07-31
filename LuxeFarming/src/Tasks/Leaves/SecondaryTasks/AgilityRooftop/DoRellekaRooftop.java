package Tasks.Leaves.SecondaryTasks.AgilityRooftop;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import static Areas.RooftopAreas.rellekaRooftopArea;
import static Areas.RooftopAreas.rellekaRooftopStartArea;
import static Utils.SelectNextPatch.GetAmountOfReadyFarmingAreas;
import static Utils.SelectNextPatch.SelectNextFarmingArea;

public class DoRellekaRooftop extends Leaf {
    @Override
    public boolean isValid() {
        return Skill.AGILITY.getLevel() >= 80 && Skill.AGILITY.getLevel() < 90;
    }


    public int relleka_z_index = 3;
    public int GO_ROUGH_WALL_START = 14946;
    public int GO_GAP_1 = 14947;
    public int GO_TIGHTROPE_2 = 14987;
    public int GO_GAP_3 = 14990;
    public int GO_GAP_4 = 14991;
    public int GO_TIGHTROPE_5 = 14992;
    public int GO_FISH_6 = 14994;

    public static int CURRENT_LAPS = 0;
    @Override
    public int onLoop() {

        if((Players.getLocal().getTile().getZ() != relleka_z_index && !rellekaRooftopArea.contains(Players.getLocal())) && !rellekaRooftopStartArea.contains(Players.getLocal())) {
            SelectNextFarmingArea();
            Logger.log("walking to rooftop");
            if(Walking.shouldWalk()) {
               Walking.walk(rellekaRooftopStartArea.getNearestTile(Players.getLocal()));
            }
            return Calculations.random(25,253);
        }

        if(GroundItems.closest("Mark of grace") != null && GroundItems.closest("Mark of grace").canReach()) {
            Logger.log("found MOG");
            int mogInInventory = Inventory.count("Mark of grace");
            if(GroundItems.closest("Mark of grace").interact())            Sleep.sleepUntil(()-> Inventory.count("Marks of grace") > mogInInventory,() -> Players.getLocal().isMoving() , 2000, 15, 10);
            return 0;
        }

        if(GameObjects.closest(GO_ROUGH_WALL_START) != null) {
            if(!Walking.isRunEnabled()) Walking.toggleRun();
            Logger.log("found rough wall start");
            if(GameObjects.closest(GO_ROUGH_WALL_START).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_1) != null && GameObjects.closest(GO_GAP_1).canReach() && Players.getLocal().getY() > 3668) {
            Logger.log("found gap 1");
            if(GameObjects.closest(GO_GAP_1).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_TIGHTROPE_2) != null && GameObjects.closest(GO_TIGHTROPE_2).canReach() && Players.getLocal().getX() < 2627) {
            Logger.log("found tight rope 1");
            if(GameObjects.closest(GO_TIGHTROPE_2).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_3) != null && GameObjects.closest(GO_GAP_3).canReach()) {
            if(GameObjects.closest(GO_GAP_3).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_4) != null && GameObjects.closest(GO_GAP_4).canReach()) {
            if(GameObjects.closest(GO_GAP_4).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_TIGHTROPE_5) != null && GameObjects.closest(GO_TIGHTROPE_5).canReach()) {
            if(GameObjects.closest(GO_TIGHTROPE_5).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_FISH_6) != null && GameObjects.closest(GO_FISH_6).canReach()) {
            if(GameObjects.closest(GO_FISH_6).interact()) {
                Sleep.sleep(1000);
                Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving() && Players.getLocal().getZ() != 3, () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            }
            CURRENT_LAPS++;

            Logger.log("Laps completed: " + CURRENT_LAPS);
            Logger.log("READY AREAS: " + GetAmountOfReadyFarmingAreas());
            if(GetAmountOfReadyFarmingAreas() > 3) {
                SelectNextFarmingArea();
            }
            return Calculations.random(0,83);
        }


        Logger.log("end of function");
        return 10000;
    }
}
