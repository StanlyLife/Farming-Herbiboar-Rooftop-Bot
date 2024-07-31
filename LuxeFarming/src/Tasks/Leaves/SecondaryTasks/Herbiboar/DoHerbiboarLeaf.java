package Tasks.Leaves.SecondaryTasks.Herbiboar;

import Enums.Herbiboar.HerbiTrails;
import Enums.Varbits.HerbiVarbits;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static Enums.Herbiboar.HerbiTrails.HERBI_START_LOCATIONS;
import static Enums.Herbiboar.HerbiTrails.getActiveTrail;
import static Settings.herbiboarSettings.getHerbiSettings;

public class DoHerbiboarLeaf extends Leaf {
    private Tile searchedHerbiTile;
    private final int inspectHerbiAnimation = 5216;
    private static Tile correctTrailTile; // Static field to store the correct trail tile

    @Override
    public boolean isValid() {
        return Inventory.getEmptySlots() >= getHerbiSettings().minimumEmptySlots;
    }

    @Override
    public int onLoop() {


        //ACTION - HARVEST HERBI
        if(NPCs.closest("Herbiboar") != null) {
            Logger.log("trying to harvest herbi");
            if(NPCs.closest("Herbiboar").interact("Harvest")) {
                Sleep.sleepUntil(() -> NPCs.closest("Herbiboar") == null, () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 2500, 50, 5);
                Logger.log("Herbi gone!");
            }
            if(getHerbiSettings().dropGuams) Inventory.dropAll("Grimy guam leaf");
            if(getHerbiSettings().dropNumulite) Inventory.dropAll("Numulite");
            if(getHerbiSettings().dropTarromin) Inventory.dropAll("Grimy tarromin");
            return 500;
        }


        //ACTION - FIND Herbi
        if (HerbiTrails.getHerbiFinishValue() != 0) {
            return AttackHerbiboar();
        }

        //ACTION - SEARCH HERBI TRAIL
        HerbiTrails trail = getActiveTrail();
        if (trail != null) {
            Tile[] trailLocs = trail.getObjectLocs(trail.getVarbitValue());
            // Find the correct trail tile
            if (correctTrailTile == null || !Arrays.asList(trailLocs).contains(correctTrailTile)) {
                for (Tile t : trailLocs) {
                    if(t == null) continue;
                    GameObject[] go = GameObjects.getObjectsOnTile(t);
                    if(go == null || Arrays.stream(go)
                            .filter(i -> i != null && i.hasAction("Inspect"))
                            .findFirst().get().distance(Players.getLocal()) > 7) {
                        Walking.walk(t);
                        continue;
                    };
                    Optional<GameObject> optionalGameObject = Arrays.stream(go)
                            .filter(i -> i != null && i.hasAction("Inspect"))
                            .findFirst();

                    if (optionalGameObject.isPresent()) {
                        Logger.log("found correct trail tile");
                        correctTrailTile = t;
                        break;
                    }
                }
            }

            // Interact with the correct trail tile
            if (correctTrailTile != null && Map.isTileOnScreen(correctTrailTile)) {
                Optional<GameObject> optionalGameObject = Arrays.stream(GameObjects.getObjectsOnTile(correctTrailTile))
                        .filter(i -> i != null && i.hasAction("Inspect"))
                        .findFirst();

                if (optionalGameObject.isPresent()) {
                    Logger.log("Interacting with game object at tile: " + correctTrailTile);
                    if (optionalGameObject.get().interact("Inspect")) {
                        Logger.log("INTERACTION: SUCCESS at tile: " + correctTrailTile);
                        Sleep.sleepUntil(() -> Players.getLocal().getAnimation() == inspectHerbiAnimation, () -> Players.getLocal().isMoving(), 2500, 15, 50);
                        Sleep.sleep(500);
                        searchedHerbiTile = correctTrailTile;
                        correctTrailTile = null; // Reset after successful interaction
                        return Calculations.random(250, 500); // Exit loop after successful interaction
                    } else {
                        Logger.log("INTERACTION: FAILED at tile: " + correctTrailTile);
                        return 250;
                    }
                }
            } else if (correctTrailTile != null) {
                if (Walking.shouldWalk()) {
                    Logger.log("Walking to tile: " + correctTrailTile);
                    Walking.walk(correctTrailTile);
                }
                return Calculations.random(250, 500);
            }
        }

        //ACTION - START INSPECTION
        if(trail == null && HerbiTrails.getHerbiFinishValue() == 0) {
            Logger.log("No active trail, starting");

            Tile startTile = getClosestTileToPlayer(HERBI_START_LOCATIONS);
            Logger.log("Start tile is: " + startTile);
            GameObject[] attackCaveList = GameObjects.getObjectsOnTile(startTile);
            boolean attackCaveOnScreen = Arrays.stream(attackCaveList).anyMatch(i -> i.hasAction("Inspect") && i.isOnScreen() && i.distance(Players.getLocal()) < 10);
            if(startTile.distance(Players.getLocal()) > 5  && !attackCaveOnScreen) {
                Logger.log("Trying to walk");
                if(Walking.shouldWalk()) {
                    Logger.log("successfully to walked");
                    Walking.walk(startTile);
                }
                return Calculations.random(30,231);
            }
            Arrays.stream(attackCaveList).filter(i -> i.hasAction("Inspect") && i.isOnScreen()).findFirst().get().interact("Inspect");
            Sleep.sleepUntil(() -> getActiveTrail() != null,  () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 2500, 15, 100);
            Logger.log("Started herbi hunt");
            return Calculations.random(25,250);
        }

        Logger.log("END OF FUNCTION");
        return 2500;
    }

    private static int AttackHerbiboar() {
        Logger.log("Last trail before herbi!");
        Tile endTile = HerbiTrails.HERBI_END_LOCATIONS.get(HerbiTrails.getHerbiFinishValue() - 1);
        GameObject[] attackCaveList = GameObjects.getObjectsOnTile(endTile);
        boolean attackCaveOnScreen = endTile.distance(Players.getLocal()) < 5 && Arrays.stream(attackCaveList).anyMatch(i -> i.hasAction("Attack") && i.isOnScreen() && i.distance(Players.getLocal()) < 10);
        if (endTile.distance(Players.getLocal()) > 5 && !attackCaveOnScreen) {
            Logger.log("Trying to walk");
            if (Walking.shouldWalk()) {
                Logger.log("successfully walked");
                Walking.walk(endTile);
            }
            return Calculations.random(30, 231);
        }

        Optional<GameObject> attackCave = Arrays.stream(attackCaveList).filter(i -> i.hasAction("Attack") && i.isOnScreen()).findFirst();
        if (attackCave.isPresent()) {
            Logger.log("Interacting with attack cave: " + attackCave.get());
            attackCave.get().interact("Attack");
            Sleep.sleepUntil(() -> Players.getLocal().isAnimating(), 2500);
            return Calculations.random(250, 500);
        }
        Logger.log("Attacking herbi boy");
        Sleep.sleepUntil(() -> NPCs.closest("Herbiboar") != null, () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 2500, 15, 125);
        Logger.log("Found herbi boy");

        return Calculations.random(25, 250);
    }

    public static Tile getCorrectTrailTile() {
        return correctTrailTile;
    }

    private static Tile getClosestTileToPlayer(List<Tile> startLocations) {
        return startLocations.stream()
                .filter(tile -> tile != null)
                .min(Comparator.comparingDouble(tile -> tile.distance(Players.getLocal())))
                .orElse(null);
    }
}
