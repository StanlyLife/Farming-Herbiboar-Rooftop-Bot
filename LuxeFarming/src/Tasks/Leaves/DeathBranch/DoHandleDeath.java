package Tasks.Leaves.DeathBranch;

import Settings.FarmerSettings;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;

import static org.dreambot.api.methods.tabs.Tabs.logout;

public class DoHandleDeath extends Leaf {

    @Override
    public boolean isValid() {
        return true;
    }
    private static Area LumbridgeSpawnArea = new Area(3216, 3229, 3225, 3209);
    @Override
    public int onLoop() {

        if(!Players.getLocal().isInCombat()) {
            Logger.log("Player is logging out to avoid death");
            logout();
            if(!Client.isLoggedIn()) {
                Logger.log("successfully logged out - stopping script");
                ScriptManager.getScriptManager().stop();
            }
            return 5000;
        }
        try {
            if(Players.getLocal().isInCombat()) Logger.log("Player is in combat with: " + Players.getLocal().getCharacterInteractingWithMe().getName() + " at tile: " + Players.getLocal().getTile() + " - Lowest hp percentage is: " + FarmerSettings.lowestHpPercentage + "%");
        } catch (Exception e) {
            Logger.error("Unable to logg info about combat at tile : " + Players.getLocal().getTile());
        }

        if(LumbridgeSpawnArea.contains(Players.getLocal())) {
            Logger.log("Is in lumbridge spawn, stopping script");
            ScriptManager.getScriptManager().stop();
        }else {
            if(Walking.shouldWalk()) {
                Walking.walk(LumbridgeSpawnArea.getRandomTile());
            }
            return Calculations.random(50,259);
        }

        return 1000;
    }
}
