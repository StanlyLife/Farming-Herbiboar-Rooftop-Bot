package Tasks.Leaves.SecondaryTasks.AgilityRooftop;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;

import static Utils.SelectNextPatch.GetAmountOfReadyFarmingAreas;
import static Utils.SelectNextPatch.SelectNextFarmingArea;

public class DoArdougneRooftop extends Leaf {
    @Override
    public boolean isValid() {
        return Skill.AGILITY.getLevel() >= 90 && Skill.AGILITY.getLevel() < 99;
    }


    public Tile ardougne_rooftop_start = new Tile(2673,3297,0);
    public int ARDOUGNE_ROOF_TOP_Z_INDEX = 3;
    public int GO_WOODEN_BEAMS = 15608;
    public int GO_GAP_1 = 15609;
    public int GO_PLANK_1 = 26635;
    public int GO_GAP_2 = 15610;
    public int GO_GAP_3 = 15611;
    public int GO_STEP_ROOF_1 = 28912;
    public int GO_GAP_4 = 15612;

    public static int CURRENT_LAPS = 0;
    @Override
    public int onLoop() {

        if(((Players.getLocal().getTile().getZ() == 0) && GameObjects.closest(GO_WOODEN_BEAMS) == null)) {
            SelectNextFarmingArea();
            Logger.log("walking to rooftop start");
            if(Walking.shouldWalk()) {
               Walking.walk(ardougne_rooftop_start);
            }
            return Calculations.random(25,253);
        }

        if(GroundItems.closest("Mark of grace") != null && GroundItems.closest("Mark of grace").distance(Players.getLocal()) <= 1) {
            Logger.log("found MOG");
            int mogInInventory = Inventory.count("Mark of grace");
            if(GroundItems.closest("Mark of grace").interact())            Sleep.sleepUntil(()-> Inventory.count("Marks of grace") > mogInInventory,() -> Players.getLocal().isMoving() , 2000, 15, 10);
            return 0;
        }

        if(GameObjects.closest(GO_WOODEN_BEAMS) != null) {
            if(!Walking.isRunEnabled()) Walking.toggleRun();
            if(GameObjects.closest(GO_WOODEN_BEAMS).interact()) Sleep.sleepUntil(() -> Players.getLocal().getTile().getZ() == 3, () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 1500, 10,500);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_1) != null && GameObjects.closest(GO_GAP_1).canReach() && Players.getLocal().getY() <= 3309) {
            if(GameObjects.closest(GO_GAP_1).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_PLANK_1) != null && GameObjects.closest(GO_PLANK_1).canReach() && Players.getLocal().getX() <= 2665) {
            if(GameObjects.closest(GO_PLANK_1).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(100);
        }
        if(GameObjects.closest(GO_GAP_2) != null && GameObjects.closest(GO_GAP_2).canReach()) {
            if(GameObjects.closest(GO_GAP_2).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_3) != null && GameObjects.closest(GO_GAP_3).canReach()) {
            if(GameObjects.closest(GO_GAP_3).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_STEP_ROOF_1) != null && GameObjects.closest(GO_STEP_ROOF_1).canReach()) {
            if(GameObjects.closest(GO_STEP_ROOF_1).interact()) Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 500, 10,25);
            return Calculations.random(0,83);
        }
        if(GameObjects.closest(GO_GAP_4) != null && Players.getLocal().getTile().equals(new Tile(2656,3297,3))) {
            if(GameObjects.closest(GO_GAP_4).interact()) {
                Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving() && Players.getLocal().getZ()  == 0, () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 2500, 10,150);
            }else {
                return 1000;
            }
            CURRENT_LAPS++;

            Logger.log("Laps completed: " + CURRENT_LAPS);
            Logger.log("READY AREAS: " + GetAmountOfReadyFarmingAreas());
            if(GetAmountOfReadyFarmingAreas() > 3 && Players.getLocal().getZ() == 0) {
                SelectNextFarmingArea();
            }
            return Calculations.random(0,83);
        }


        Logger.log("end of function");
        return 10000;
    }
}
