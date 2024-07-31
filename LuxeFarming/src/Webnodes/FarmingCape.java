package Webnodes;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.impl.teleports.Teleport;
import org.dreambot.api.methods.walking.web.node.impl.teleports.TeleportWebNode;
import org.dreambot.api.utilities.Sleep;

public class FarmingCape {

    public static void WebnodeAdditionFarmingCapeTeleport(WebFinder webFinder) {
        Teleport FarmingCapeEquipmentTeleport = new Teleport()
        {
            @Override
            public int getX()
            {
                return 1249;
            }

            @Override
            public int getY()
            {
                return 3725;
            }

            @Override
            public int getZ()
            {
                return 0;
            }

            @Override
            public boolean hasRequirements()
            {
                return (Equipment.contains(item -> item.getName().contains("Farming cape") || item.getName().contains("Farming cape(t)")));
            }

            @Override
            public boolean execute()
            {
                Tile endTile = new Tile(getX(), getY(), getZ());
                return Equipment.interact(EquipmentSlot.CAPE, "Teleport") && Sleep.sleepUntil(() -> endTile.distance() < 20, 10000);
            }
        };
        Teleport FarmingCapeInventoryTeleport = new Teleport()
        {
            @Override
            public int getX()
            {
                return 1249;
            }

            @Override
            public int getY()
            {
                return 3725;
            }

            @Override
            public int getZ()
            {
                return 0;
            }

            @Override
            public boolean hasRequirements()
            {
                return (Inventory.contains(item -> item.getName().contains("Farming cape") || item.getName().contains("Farming cape(t)")));
            }

            @Override
            public boolean execute()
            {
                Tile endTile = new Tile(getX(), getY(), getZ());
                return Inventory.interact(i -> i.getName().toLowerCase().contains("farming cape"), "Teleport") && Sleep.sleepUntil(() -> endTile.distance() < 20, 10000);
            }
        };
        webFinder.addWebNodes(new TeleportWebNode(FarmingCapeInventoryTeleport));
        webFinder.addWebNodes(new TeleportWebNode(FarmingCapeEquipmentTeleport));
    }

}
