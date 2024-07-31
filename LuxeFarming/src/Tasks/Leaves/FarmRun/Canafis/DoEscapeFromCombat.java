package Tasks.Leaves.FarmRun.Canafis;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;

import static Enums.FarmingAreaEnum.CANAFIS;
import static org.dreambot.api.methods.world.Worlds.getWorld;

public class DoEscapeFromCombat extends Leaf {

    boolean shouldPersist = false;

    @Override
    public boolean isValid() {
        return Players.getLocal().isInCombat() || shouldPersist;
    }

    private static Tile safeTile = new Tile(3603,3543);
    @Override
    public int onLoop() {
        shouldPersist = true;

        if(Players.getLocal().getTile().equals(safeTile)) {
            World world = Worlds.getRandomWorld(w -> !w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0 && w.isNormal());
            if(WorldHopper.hopWorld(world)) {
                Sleep.sleepUntil(() -> world.getWorld() == world.getWorld(),10000);
                if(Worlds.getCurrentWorld() == world.getWorld()) shouldPersist = false;
                return Calculations.random(1000,5000);
            }
        }

        if(!Players.getLocal().getTile().equals(safeTile) && Walking.shouldWalk()) {
            Walking.walk(safeTile);
        }
        return Calculations.random(50,250);
    }
}
