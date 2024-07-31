package Webnodes;

import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class FossilIsland {

    private static Tile RowboatTile = new Tile(3724, 3807, 0);
    private static Tile SmallIslandTile = new Tile(3769, 3898, 0);
    private static Tile SmallIslandRowboatTile = new Tile(3766, 3897, 0);
    private static Tile FossilIslandUnderWaterByAnchorTile = new Tile(3732, 10281, 1);
    private static Tile FossilIslandUnderWaterByPatchOne = new Tile(3732, 10273, 1);
    private static Tile FossilIslandUnderWaterByPatchTwo = new Tile(3733, 10269, 1);
    static AbstractWebNode fossilIslandShip = new AbstractWebNode(RowboatTile.getX(), RowboatTile.getY(), RowboatTile.getZ())
    {

        @Override
        public boolean hasRequirements()
        {
            return true;
        }

        @Override
        public boolean execute()
        {
            GameObject rowboat = GameObjects.closest("Rowboat");
            if (rowboat != null)
            {
                rowboat.interact("Travel");
                sleepUntil(() -> Dialogues.areOptionsAvailable(), 10000, 10);
                if (Dialogues.areOptionsAvailable())
                {
                    Logger.log("Rowing out to sea");
                    Dialogues.chooseOption("Row out to sea, north of the island.");
                }
            }

            return Sleep.sleepUntil(() -> SmallIslandTile.distance() < 20, 2000);
        }
    };
    static AbstractWebNode fossilIslandSmallIslandDive = new AbstractWebNode(SmallIslandRowboatTile.getX(), SmallIslandRowboatTile.getY(), SmallIslandRowboatTile.getZ())
    {

        @Override
        public boolean hasRequirements()
        {
            return true;
        }

        @Override
        public boolean execute()
        {
            Logger.log("Trying to dive from smallIslandShip");
            GameObject rowboat = GameObjects.closest("Rowboat");
            if (rowboat != null)
            {
                if(Equipment.getItemInSlot(EquipmentSlot.WEAPON) != null)Equipment.unequip(EquipmentSlot.WEAPON);
                if(Equipment.getItemInSlot(EquipmentSlot.SHIELD) != null)Equipment.unequip(EquipmentSlot.SHIELD);
                rowboat.interact("Dive");
                Sleep.sleepUntil(Dialogues::inDialogue, () -> Players.getLocal().isMoving(), 1000, 25,20);
                if(Dialogues.continueDialogue()) {
                    Sleep.sleepUntil(Dialogues::areOptionsAvailable, 1000);
                    if (Dialogues.areOptionsAvailable())
                    {
                        Logger.log("Diving");
                        Dialogues.chooseOption("Yes, dive anyway.");
                    }
                }
            }
            return Sleep.sleepUntil(() -> FossilIslandUnderWaterByAnchorTile.distance() < 5, 2000);
        }
    };

    public static void AddFossilIslandNodes(WebFinder webFinder)
    {
        BasicWebNode SmallIslandWn = new BasicWebNode(SmallIslandTile.getX(), SmallIslandTile.getY(), SmallIslandTile.getZ());
        BasicWebNode EnterUnderWaterWn = new BasicWebNode(FossilIslandUnderWaterByAnchorTile.getX(), FossilIslandUnderWaterByAnchorTile.getY(), FossilIslandUnderWaterByAnchorTile.getZ());
        BasicWebNode FossilIslandUnderWaterByPatchOneWn = new BasicWebNode(FossilIslandUnderWaterByPatchOne.getX(), FossilIslandUnderWaterByPatchOne.getY(), FossilIslandUnderWaterByPatchOne.getZ());
        BasicWebNode FossilIslandUnderWaterByPatchTwoWn = new BasicWebNode(FossilIslandUnderWaterByPatchTwo.getX(), FossilIslandUnderWaterByPatchTwo.getY(), FossilIslandUnderWaterByPatchTwo.getZ());

        //ADD SMALL WATER IN DEEP SEA ISLAND
        webFinder.addWebNode(SmallIslandWn);
        //ADD SHIPS
        webFinder.addWebNode(fossilIslandShip);
        webFinder.addWebNode(fossilIslandSmallIslandDive);
        //ADD SEAWEED PATCHES
        webFinder.addWebNode(EnterUnderWaterWn);
        EnterUnderWaterWn.addDualConnections(FossilIslandUnderWaterByPatchOneWn);
        FossilIslandUnderWaterByPatchOneWn.addDualConnections(FossilIslandUnderWaterByPatchTwoWn);
        //CONNECT SHIPS
        fossilIslandShip.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(RowboatTile.getTile(), 15), SmallIslandWn);
        fossilIslandSmallIslandDive.addDualConnections(SmallIslandWn, EnterUnderWaterWn);
    }


}
