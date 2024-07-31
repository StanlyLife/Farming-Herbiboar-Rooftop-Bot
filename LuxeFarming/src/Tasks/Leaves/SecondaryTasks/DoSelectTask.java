package Tasks.Leaves.SecondaryTasks;

import Enums.Herbiboar.HerbiTrails;
import Enums.Varbits.HerbiVarbits;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static Enums.Herbiboar.HerbiTrails.*;
import static Settings.secondarySettings.getSecondSettings;

public class DoSelectTask extends Leaf {
    @Override
    public boolean isValid() {
        return getSecondSettings().action == "herbi";
    }

    @Override
    public int onLoop() {


        Logger.log("LOGGING VARBITS");
        for (HerbiVarbits bit : HerbiVarbits.values()) {
            bit.LogVarbitStatus();
        }
        Logger.log("-------------------------------------------------------");

        if (NPCs.closest("Herbiboar") != null) {
            Logger.log("trying to harvest herbi");
            NPCs.closest("Herbiboar").interact();
            Sleep.sleepUntil(() -> NPCs.closest("Herbiboar") == null, () -> NPCs.closest("Herbiboar") != null, 2500, 50, 5);
            Logger.log("Herbi gone!");
            return 0;
        }
        return 5000;
    }
}
