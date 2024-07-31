package Tasks.Leaves.SecondaryTasks.VolcanicAsh;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import static Areas.MiningAshAreas.mineVolcanicAshArea;
import static Settings.secondarySettings.getSecondSettings;

public class DoMineVolcanicAsh extends Leaf
{
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "ash";
    }

    @Override
    public int onLoop() {

        if(Inventory.isFull() && Inventory.contains(i -> i.getName().equalsIgnoreCase("soda ash") && !i.isStackable())) Inventory.dropAll("Soda ash");

        if(!mineVolcanicAshArea.contains(Players.getLocal())) {
            if(Walking.shouldWalk()) {
                Walking.walk(mineVolcanicAshArea.getCenter());
            }
            return Calculations.random(25,250);
        }

        GameObject closestAsh = GameObjects.closest("Ash pile");
        if(closestAsh != null) closestAsh.interact();
        else Walking.walkOnScreen(mineVolcanicAshArea.getRandomTile());
        Sleep.sleepUntil(() -> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving(), () -> Players.getLocal().isAnimating() || Players.getLocal().isMoving(), 2500, 15,100 );
        Logger.log("Ash pile empty");
        return 1250;
    }
}
