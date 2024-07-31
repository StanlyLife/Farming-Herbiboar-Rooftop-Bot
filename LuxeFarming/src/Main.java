import Enums.FarmingAreaEnum;
import Enums.SeedsToPlant;
import Enums.Herbiboar.HerbiTrails;
import GUI.Paint.Cell;
import GUI.Paint.GridPainter;
import Settings.FarmerSettings;
import Tasks.Branches.FarmRun.Ardougne.FarmArdougneBranch;
import Tasks.Branches.FarmRun.Canafis.FarmCanafisBranch;
import Tasks.Branches.FarmRun.Catherby.FarmCatherbyBranch;
import Tasks.Branches.FarmRun.DoWalkToPatchBranch;
import Tasks.Branches.FarmRun.Draynor.FarmDraynorBranch;
import Tasks.Branches.FarmRun.FarmingGuild.FarmFarmingGuildBranch;
import Tasks.Branches.FarmRun.GiantSeaweed.FarmGiantSeaweed;
import Tasks.Branches.FarmRun.Hosidious.FarmHosidiousBranch;
import Tasks.Branches.FarmRun.PatchSelector.SelectPatchBranch;
import Tasks.Branches.FarmRun.Trollheim.FarmTrollheimBranch;
import Tasks.Branches.FarmRun.Varlamore.FarmVarlamoreBranch;
import Tasks.Branches.FarmRun.Weiss.FarmWeissPatchBranch;
import Tasks.Branches.FarmRunBranch;
import Tasks.Branches.SecondaryTasks.AgilityRoofTop.AgilityRooftopBranch;
import Tasks.Branches.SecondaryTasks.Herbiboar.HerbiSubBranches.HandleHerbiboarInventoryBranch;
import Tasks.Branches.SecondaryTasks.Herbiboar.HerbiSubBranches.DoHerbiboarBranch;
import Tasks.Branches.SecondaryTasks.Herbiboar.HerbiboarMainBranch;
import Tasks.Branches.InventoryHandlerBranch;
import Tasks.Branches.SecondaryTasks.MakeCompost.MakeCompostBranch;
import Tasks.Branches.SecondaryTasks.SecondaryActivityBranch;
import Tasks.Branches.SecondaryTasks.VolcanicAsh.VolcanicAshBranch;
import Tasks.Branches.TaskSelectorBranch;
import Tasks.Branches.UtilBranches.DeathBranch;
import Tasks.Leaves.DeathBranch.DoHandleDeath;
import Tasks.Leaves.FarmRun.Ardougne.*;
import Tasks.Leaves.FarmRun.Canafis.*;
import Tasks.Leaves.FarmRun.Catherby.*;
import Tasks.Leaves.FarmRun.Draynor.*;
import Tasks.Leaves.FarmRun.FarmingGuild.*;
import Tasks.Leaves.FarmRun.GiantSeaweed.DoFarmGiantSeaweedPatchOne;
import Tasks.Leaves.FarmRun.GiantSeaweed.DoFarmGiantSeaweedPatchTwo;
import Tasks.Leaves.FarmRun.GiantSeaweed.DoWalkTogiantSeaweedPatch;
import Tasks.Leaves.FarmRun.GiantSeaweed.FinishGiantSeaweed;
import Tasks.Leaves.FarmRun.Hosidious.*;
import Tasks.Leaves.FarmRun.InventoryHandler.ClearInventoryLeaf;
import Tasks.Leaves.FarmRun.PatchSelector.SelectPatch;
import Tasks.Leaves.FarmRun.Weiss.DoWeissHerbPatch;
import Tasks.Leaves.FarmRun.Weiss.FinishWeissLeaf;
import Tasks.Leaves.SecondaryTasks.AgilityRooftop.DoArdougneRooftop;
import Tasks.Leaves.SecondaryTasks.AgilityRooftop.DoRellekaRooftop;
import Tasks.Leaves.SecondaryTasks.Herbiboar.DoHerbiboarLeaf;
import Tasks.Leaves.SecondaryTasks.Herbiboar.HerbiInventoryHandler.HerbiUimFullInvHandlerLeaf;
import Tasks.Leaves.SecondaryTasks.MakeCompost.DoMakeCompost;
import Tasks.Leaves.SecondaryTasks.VolcanicAsh.DoMineVolcanicAsh;
import Tasks.Leaves.SecondaryTasks.DoWalkToFossilIsland;
import Tasks.Leaves.FarmRun.Trollheim.DoTrollheimHerbPatch;
import Tasks.Leaves.FarmRun.Trollheim.TestTrollheimLeaf;
import Tasks.Leaves.FarmRun.Varlamore.*;
import Tasks.Leaves.Walking.DoWalkToPatch;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.StandardDropPattern;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.event.impl.BreakEvent;
import org.dreambot.api.script.event.impl.ExperienceEvent;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.listener.BreakListener;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.ExperienceListener;
import org.dreambot.api.settings.ScriptSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static GUI.Paint.AreaPainter.PaintArdy;
import static Settings.FarmerSettings.*;
import static Settings.secondarySettings.getSecondSettings;
import static Utils.SelectNextPatch.SelectNextFarmingArea;
import static Webnodes.FarmingCape.WebnodeAdditionFarmingCapeTeleport;
import static Webnodes.FossilIsland.AddFossilIslandNodes;
import static Webnodes.TrollheimPatch.WebnodeAdditionTrollheimPatch;


@ScriptManifest(name = "Luxe Premium Farming", version = 1.1, author = "LUXE", category = Category.FARMING, image = "https://i.ibb.co/NZZtR7r/Finishing-Logo-LUXE.png")
public class Main extends TreeScript implements ChatListener, ExperienceListener, BreakListener {
    private Timer timer;
    private long startTime;
    private boolean breakIsActive = false;
    private int initialHerbs;
    private int initialSecondaries = 0;
    public static  final WebFinder webFinder = WebFinder.getWebFinder();
    public static List<Item> initialInventoryItems;
    private GridPainter gridPainter = new GridPainter(true, 5, 5, 100, 35, new Color(255, 255, 255, 225));
    StandardDropPattern dp = new StandardDropPattern(new int[] {0, 4, 8, 12, 16, 20, 24, 25, 21, 17, 13, 9, 5, 1, 2, 6, 10, 14, 18, 22, 26, 27, 23, 19, 15, 11, 7, 3});
    private int startingFarmingExp;
    private int startingHuntingExp;
    private Frame gui;

    public static String formatElapsedTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;


        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }


    @Override
    public void onPause() {
        if (timer != null) {
            timer.pause();
        }
    }
//    @Override
//    public void onExit() {
//        gui.dispose();
//    }

    @Override
    public void onStart() {
        if(Client.isLoggedIn()) {
            try {
                Logger.log("Trying to load settings");
                FarmerSettings settings = ScriptSettings.load(FarmerSettings.class, SAVENAME);
                Logger.log("Settings: " + settings);
                if(settings != null) {
                    settings.seedsToPlant = SeedsToPlant.DEFAULT;
                    setSettings(settings);
                }else {
                    Logger.log("Settings was null");
                }
                Logger.log("Loaded save!");
            }catch (NoClassDefFoundError e) {
                Logger.log("unable to load save file");
            }
        }else {
            Logger.log("Should start the script logged in to retrieve saveFile");
        }
        Inventory.setDropPattern(dp);
        initialInventoryItems = Inventory.all();
        timer = new Timer();
        startTime = System.currentTimeMillis();
        timer.start();
        initialHerbs = Inventory.count(0);
        initialSecondaries = Inventory.count(0);
        gridPainter.addCell(
                new Cell(
                        "Time",
                        1,
                        "52",
                        2,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "State",
                        2,
                        "Unknown",
                        3,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "Farming EXP",
                        3,
                        "0",
                        3,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "Herbs",
                        4,
                        "0",
                        3,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "Secondaries",
                        5,
                        "0",
                        3,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "Hunter EXP",
                        6,
                        "0",
                        3,
                        false
                )
        );
        gridPainter.addCell(
                new Cell(
                        "Agility EXP",
                        7,
                        "0",
                        3,
                        false
                )
        );
        if (Client.isLoggedIn()) {
            SkillTracker.start(Skill.FARMING);
            SkillTracker.start(Skill.HUNTER);
            SkillTracker.start(Skill.AGILITY);
            startingFarmingExp = SkillTracker.getStartExperience(Skill.FARMING);
            startingHuntingExp = SkillTracker.getStartExperience(Skill.HUNTER);
        }
        this.getRoot().addBranches(

                new DeathBranch().addLeaves(new DoHandleDeath()),
                new SecondaryActivityBranch().addLeaves(
                        new DoMineVolcanicAsh(),
                        new AgilityRooftopBranch().addLeaves(new DoRellekaRooftop(), new DoArdougneRooftop()),
                        new HerbiboarMainBranch().addLeaves(
                                new HandleHerbiboarInventoryBranch().addLeaves(
                                        new HerbiUimFullInvHandlerLeaf()
                                ),
                                new DoHerbiboarBranch(). addLeaves(
                                        new DoWalkToFossilIsland(),
                                        new DoHerbiboarLeaf()
                                )
                        ),
                        new VolcanicAshBranch().addLeaves(
                                new DoMineVolcanicAsh()
                        ),
                        new MakeCompostBranch().addLeaves(
                                new DoMakeCompost()
                        )
                ),
                new SelectPatchBranch().addLeaves(new SelectPatch()),
                new FarmRunBranch().addLeaves(
                        new InventoryHandlerBranch().addLeaves(new ClearInventoryLeaf()),
                        new DoWalkToPatchBranch().addLeaves(new DoWalkToPatch()),
                        new FarmGiantSeaweed().addLeaves(
                                new DoWalkTogiantSeaweedPatch(),
                                new DoFarmGiantSeaweedPatchOne(),
                                new DoFarmGiantSeaweedPatchTwo(),
                                new FinishGiantSeaweed()
                        ),
                        new FarmDraynorBranch().addLeaves(
                                new DoDraynorHerbPatch(),
                                new DoDraynorNorthAllotmentPatch(),
                                new DoDraynorSouthAllotmentPatch(),
                                new DoDraynorFlowerPatch(),
                                new TestDraynorLeaf()
                        ),
                        new FarmCanafisBranch().addLeaves(
                                new DoEscapeFromCombat(),
                                new DoCanafisHerbPatch(),
                                new DoCanafisNorthAllotmentPatch(),
                                new DoCanafisSouthAllotmentPatch(),
                                new DoCanafisFlowerPatch(),
                                new TestCanafisLeaf()
                        ),
                        new FarmCatherbyBranch().addLeaves(
                                new DoCatherbyHerbPatch(),
                                new DoCatherbyNorthAllotmentPatch(),
                                new DoCatherbySouthAllotmentPatch(),
                                new DoCatherbyFlowerPatch(),
                                new TestCatherbyLeaf()
                        ),
                        new FarmArdougneBranch().addLeaves(
                                new DoArdougneHerbPatch(),
                                new DoArdougneNorthAllotmentPatch(),
                                new DoArdougneSouthAllotmentPatch(),
                                new DoArdougneFlowerPatch(),
                                new TestArdougneLeaf()
                        ),
                        new FarmVarlamoreBranch().addLeaves(
                                new DoVarlamoreHerbPatch(),
                                new DoVarlamoreNorthAllotmentPatch(),
                                new DoVarlamoreSouthAllotmentPatch(),
                                new DoVarlamoreFlowerPatch(),
                                new TestVarlamoreLeaf()
                        ),
                        new FarmHosidiousBranch().addLeaves(
                                new DoHosidiusHerbPatch(),
                                new DoHosidiusNorthAllotmentPatch(),
                                new DoHosidiusSouthAllotmentPatch(),
                                new DoHosidiusFlowerPatch(),
                                new TestHosidusLeaf()
                        ),
                        new FarmFarmingGuildBranch().addLeaves(
                                new DoFarmingGuildHerbPatch(),
                                new DoFarmingGuildNorthAllotmentPatch(),
                                new DoFarmingGuildSouthAllotmentPatch(),
                                new DoFarmingGuildFlowerPatch(),
                                new TestFarmingGuildLeaf()
                        ),
                        new FarmTrollheimBranch().addLeaves(
                                new DoTrollheimHerbPatch(),
                                new TestTrollheimLeaf()
                        ),
                        new FarmWeissPatchBranch().addLeaves(
                                new DoWeissHerbPatch(),
                                new FinishWeissLeaf()
                        )


                )
        );

        Logger.log("STARTING: Current patch = " + getSettings().currentPatch);
        WebnodeAdditionTrollheimPatch(webFinder);
        WebnodeAdditionFarmingCapeTeleport(webFinder);
        AddFossilIslandNodes(webFinder);

    }

    @Override
    public int onLoop() {
        if(!Inventory.isOpen() && Calculations.random(0,1000) >= 990) Inventory.open();
        if (timer != null && timer.isPaused() && !breakIsActive) {
            timer.resume();
        }
        if (!SkillTracker.hasStarted(Skill.FARMING)) SkillTracker.start(Skill.FARMING);
        if(startingFarmingExp == 0) startingFarmingExp = SkillTracker.getStartExperience(Skill.FARMING);
        if(startingHuntingExp == 0) startingHuntingExp = SkillTracker.getStartExperience(Skill.HUNTER);


        if(getSettings().shouldLoop) {
            return this.getRoot().onLoop();
        }


        return 200;
    }

    @Override
    public void onBreakStart(BreakEvent event) {
        breakIsActive = true;
        this.timer.pause();
    }
    @Override
    public void onBreakEnd(BreakEvent event) {
        breakIsActive = false;
        this.timer.resume();
    }

    @Override
    public void onGained(ExperienceEvent event) {
        if (event.getSkill() == Skill.FARMING) getSettings().successfullFarms += 1;
    }


    @Override
    public void onPaint(Graphics2D graphics) {
        // Paint the Ardougne area
        PaintArdy(graphics);

        // Update various display cells with information
        gridPainter.updateCell("Time", formatElapsedTime(timer.elapsed()) + " TTL: (" + Math.round(((float) SkillTracker.getTimeToLevel(Skill.FARMING) / 1000) / 60) + ")");
        gridPainter.updateCell("State", this.getCurrentBranchName() + " " + this.getCurrentLeafName());
        gridPainter.updateCell("Farming EXP", String.valueOf(SkillTracker.getGainedExperience(Skill.FARMING)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.FARMING) + ") " + "Lvl: " + Skills.getRealLevel(Skill.FARMING));
        gridPainter.updateCell("Steals", getSettings().successfullFarms + " thieves (" + getItemsPerHour(0, getSettings().successfullFarms, (int) timer.elapsed()) + ") ");
        gridPainter.updateCell("GP", " 0");
        gridPainter.updateCell("Hunter EXP", String.valueOf(SkillTracker.getGainedExperience(Skill.HUNTER)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.HUNTER) + ") " + "Lvl: " + Skills.getRealLevel(Skill.HUNTER));
        gridPainter.updateCell("Agility EXP", String.valueOf(SkillTracker.getGainedExperience(Skill.AGILITY)) + "exp (" + SkillTracker.getGainedExperiencePerHour(Skill.AGILITY) + ") " + "Lvl: " + Skills.getRealLevel(Skill.AGILITY));
        gridPainter.draw(graphics);

        // Specific actions for herbiboar activity
        if (getSecondSettings().action.equals("herbi")) {
            // Paint footprints
            graphics.setColor(Color.CYAN);
            for (GameObject step : GameObjects.all(i -> i.getName().equalsIgnoreCase("Footprints"))) {
                PaintGameObject(graphics, step);
            }

            // Get the active trail
            HerbiTrails trail = HerbiTrails.getActiveTrail();
            if (trail != null) {
                int currentPath = trail.getVarbitValue();
                Tile[] trailLocs = trail.getObjectLocs(currentPath);

                // Draw trail objects (mushrooms, mud, etc)
                if (trailLocs != null && trailLocs.length > 0) {
                    Tile correctTrailTile = DoHerbiboarLeaf.getCorrectTrailTile();
                    for (Tile t : trailLocs) {
                        if (t != null && t.distance(Players.getLocal()) < 15 && t.equals(correctTrailTile)) {
                            Optional<GameObject> optionalGameObject = Arrays.stream(GameObjects.getObjectsOnTile(t))
                                    .filter(i -> i != null && i.hasAction("Inspect") && i.isOnScreen())
                                    .findFirst();
                            // If a GameObject is present, paint it
                            optionalGameObject.ifPresent(gameObject -> {
                                graphics.setColor(Color.RED); // Paint the correct trail object in red
                                PaintGameObject(graphics, gameObject);
                            });
                        }
                    }
                }
            }

            // Draw finish tunnels if applicable
            int finishId = HerbiTrails.getHerbiFinishValue();
            if (finishId > 0) {
                Tile finishLoc = HerbiTrails.HERBI_END_LOCATIONS.get(finishId - 1);
                GameObject object = GameObjects.getTopObjectOnTile(finishLoc);
                if (object != null) {
                    graphics.setColor(Color.RED);
                    PaintGameObject(graphics, object);
                }
            }
        }
    }

    private static void PaintGameObject(Graphics2D graphics, GameObject gameObject) {
        if (gameObject == null || !gameObject.isOnScreen()) return;
        Shape shape = gameObject.getModel().getHullBounds(1.f);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(2.f));
        graphics.draw(shape);
    }

    public static int getItemsPerHour(int initialItems, int currentItems, int timeRunning) {
        // Ensure timeRunning is not 0 to avoid division by zero
        if (timeRunning == 0) {
            return 0;
        }
        double coinsPerHour = ((double) (currentItems - initialItems)) / (timeRunning / 3600000.0);
        return (int) Math.round(coinsPerHour); // Round to the nearest integer
    }



}