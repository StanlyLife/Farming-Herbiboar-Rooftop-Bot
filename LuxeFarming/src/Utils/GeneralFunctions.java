package Utils;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.utilities.Sleep;

public class GeneralFunctions {
    public static void SleepUntilSelected() {
        Sleep.sleepUntil(Inventory::isItemSelected, 2500);
        Sleep.sleep(250,750);
    }

}
