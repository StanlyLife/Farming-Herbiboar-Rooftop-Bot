package Settings;


import Enums.FarmingAreaEnum;
import Enums.SeedsToPlant;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.ArrayList;
import java.util.List;

public class herbiboarSettings {
    public static String SAVENAME = "LuxeFarming-"+ Players.getLocal().getName().replace(" ", "_") +".json";
    private static herbiboarSettings herbiboarSettings = new herbiboarSettings();
    public static herbiboarSettings getHerbiSettings() {
        return herbiboarSettings;
    }

    public static herbiboarSettings setHerbiSettings(herbiboarSettings settings) {
        herbiboarSettings = settings;
        return herbiboarSettings;
    }

    public boolean shouldLoop = true;

    public Tile successfullInspectTile = null;

    public int minimumEmptySlots = 4;
    public boolean dropGuams = true;
    public boolean dropNumulite = true;
    public boolean dropTarromin = true;


    public List<GameObject> footPrints = new ArrayList<>();


    herbiboarSettings() {
    }


}
