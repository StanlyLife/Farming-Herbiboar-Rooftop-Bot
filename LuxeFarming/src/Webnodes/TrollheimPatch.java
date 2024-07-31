package Webnodes;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.AgilityWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;
import org.dreambot.api.methods.walking.web.node.impl.EntranceWebNode;
import org.dreambot.api.utilities.Sleep;

public class TrollheimPatch {

    public static void WebnodeAdditionTrollheimPatch(WebFinder webFinder) {

        Tile agilityStartGameObjectTile = new Tile(2843, 3693, 0);
        Tile caveStartGameObjectTile = new Tile(2839, 3689, 0);
        Tile trollLadderGameObjectTile = new Tile(2831, 10077, 2);

        AbstractWebNode agilityStartBN = new BasicWebNode(2844,3693,0);
        AbstractWebNode agilityEndBN = new BasicWebNode(2838,3693,0);

        AbstractWebNode trollLadderInsideCaveBN = new BasicWebNode(2831,10076,2);
        AbstractWebNode trollLadderOutsideCaveBN = new BasicWebNode(2831,3676,0);
        AbstractWebNode trollHeimToolLeprechaunBN = new BasicWebNode(2827,3685,0);
        AbstractWebNode trollHeimherbPatchBN = new BasicWebNode(2826,3693,0);

        AbstractWebNode caveStartBN = new BasicWebNode(2840,3690,0);

        AbstractWebNode insideCave = new BasicWebNode(2837,10090,2);
        AbstractWebNode middleCave1 = new BasicWebNode(2836,10078,2);
        AbstractWebNode middleCave2 = new BasicWebNode(2839,10063,2);
        AbstractWebNode middleCave3 = new BasicWebNode(2839,10057,2);
        AbstractWebNode kobCave = new BasicWebNode(2831,10063,2);

        agilityStartBN.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(agilityStartBN.getTile(), 15));
        caveStartBN.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(caveStartBN.getTile(), 15));

        EntranceWebNode cave = new EntranceWebNode(caveStartGameObjectTile.getX(), caveStartGameObjectTile.getY(), caveStartGameObjectTile.getZ());
        cave.setEntityName("Stronghold"); // Name of entrance 1
        cave.setAction("Enter");
        cave.addDualConnections(insideCave);
        insideCave.addDualConnections(middleCave1);
        caveStartBN.addDualConnections(cave);


//        AbstractWebNode enterCaveBN = new BasicWebNode(caveStartGameObjectTile.getX(),caveStartGameObjectTile.getY(),0);

        middleCave1.addDualConnections(middleCave2);
        middleCave2.addDualConnections(middleCave3);
        middleCave3.addDualConnections(kobCave);
        kobCave.addDualConnections(trollLadderInsideCaveBN);


        EntranceWebNode ladder = new EntranceWebNode(trollLadderGameObjectTile.getX(), trollLadderGameObjectTile.getY(), trollLadderGameObjectTile.getZ());
        ladder.setEntityName("Troll ladder"); // Name of entrance 1
        ladder.setAction("Climb-up");
        ladder.addDualConnections(trollLadderInsideCaveBN,trollLadderOutsideCaveBN);

        AgilityWebNode trollheimAgility = new AgilityWebNode(agilityStartGameObjectTile.getX(), agilityStartGameObjectTile.getY(), agilityStartGameObjectTile.getZ());
        trollheimAgility.setObjectName("Rocks");
        trollheimAgility.setAction("Climb");
        trollheimAgility.setLevel(73);
        agilityStartBN.addDualConnections(trollheimAgility);

        trollheimAgility.addDualConnections(agilityEndBN);
        agilityEndBN.addDualConnections(trollHeimherbPatchBN, trollHeimToolLeprechaunBN);

        trollLadderOutsideCaveBN.addDualConnections(trollHeimToolLeprechaunBN);
        trollHeimToolLeprechaunBN.addDualConnections(trollHeimherbPatchBN);


        AbstractWebNode[] webNodes = {
                trollLadderInsideCaveBN,
                trollLadderOutsideCaveBN,
                trollHeimToolLeprechaunBN,
                trollHeimherbPatchBN,
                caveStartBN,
                insideCave,
                middleCave1,
                middleCave3,
                kobCave,
                agilityStartBN,
                agilityEndBN,
        };
//        webFinder.removeNode(4573);
//        webFinder.removeNode(4579);
        webFinder.removeNode(10584);
        webFinder.addWebNodes(cave, ladder, trollheimAgility);
        webFinder.addWebNodes(trollheimAgility);
        webFinder.addWebNodes(webNodes);

    }

}
