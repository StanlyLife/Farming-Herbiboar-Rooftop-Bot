package Enums.Varbits;

import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.utilities.Logger;

public enum HerbiVarbits {

    HB_TRAIL_31303(5737),
    HB_TRAIL_31306(5738),
    HB_TRAIL_31309(5739),
    HB_TRAIL_31312(5740),
    HB_TRAIL_31315(5741),
    HB_TRAIL_31318(5742),
    HB_TRAIL_31321(5743),
    HB_TRAIL_31324(5744),
    HB_TRAIL_31327(5745),
    HB_TRAIL_31330(5746),

    HB_TRAIL_31333(5768),
    HB_TRAIL_31336(5769),
    HB_TRAIL_31339(5770),
    HB_TRAIL_31342(5771),
    HB_TRAIL_31345(5772),
    HB_TRAIL_31348(5773),
    HB_TRAIL_31351(5774),
    HB_TRAIL_31354(5775),
    HB_TRAIL_31357(5776),
    HB_TRAIL_31360(5777),

    HB_TRAIL_31363(5747),
    HB_TRAIL_31366(5748),
    HB_TRAIL_31369(5749),
    HB_TRAIL_31372(5750),

    HB_FINISH(5766);
//    HB_STARTED(5767); //not working

    private final int varbit;
    HerbiVarbits(int varbit) {
        this.varbit = varbit;
    }

    public void LogVarbitStatus() {
         int configValue =  PlayerSettings.getBitValue(this.varbit);
        Logger.log("Logging value for " + this.varbit + " ::: value = " + configValue +" " + ((configValue == 1 || configValue == 2) ? "CURRENT TRAIL" : "") );
    }


    public int getVarbit() {
        return varbit;
    }
}
