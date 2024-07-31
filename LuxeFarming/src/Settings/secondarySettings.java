package Settings;


import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.util.ArrayList;
import java.util.List;

public class secondarySettings {
    public static String SAVENAME = "LuxeFarming-"+ Players.getLocal().getName().replace(" ", "_") +".json";
    private static secondarySettings secondSettings = new secondarySettings();
    public static secondarySettings getSecondSettings() {
        return secondSettings;
    }

    public static secondarySettings setSecondSettings(secondarySettings settings) {
        secondSettings = settings;
        return secondSettings;
    }

    public String action = "";

    secondarySettings() {

    }


}
