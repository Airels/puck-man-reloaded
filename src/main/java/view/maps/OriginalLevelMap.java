package view.maps;

import config.game.GlobalHUDConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelBuilderCellCoordinatesStackingException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import model.actions.events.PacGumEatEvent;
import model.elements.cells.GhostDoor;
import model.elements.cells.Wall;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.Ghosts;
import model.elements.entities.items.PacGum;
import model.events.BordersTunnelTeleportEvent;
import model.events.GhostRegenerationPoint;
import model.levels.Level;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * The Original PacMan map.
 */
public class OriginalLevelMap implements LoadableMap {

    private final Collection<Coordinates<Integer>> zonesSpawnPacGumsProhibited, zonesSpawnSuperPacGum;
    private final Collection<Ghost> generatedGhosts;
    private final PacMan pacMan;
    private final Level level;
    private final Map<Entity, Coordinates<Double>> spawnPoints;
    private final Collection<BordersTunnelTeleportEvent> bordersTunnelTeleportEvents;
    private final Collection<GhostRegenerationPoint> ghostRegenerationPoints;
    private MapLevel originalMapLevel;
    private int nbOfGeneratedPacGums;
    private boolean generated;

    /**
     * Default constructor
     *
     * @param level  The Level of the Map
     * @param pacMan PacMan
     */
    public OriginalLevelMap(Level level, PacMan pacMan) {
        this.level = level;
        this.pacMan = pacMan;
        zonesSpawnPacGumsProhibited = new ArrayList<>();
        zonesSpawnSuperPacGum = new ArrayList<>();

        generatedGhosts = new LinkedList<>();
        spawnPoints = new HashMap<>();
        bordersTunnelTeleportEvents = new LinkedList<>();
        ghostRegenerationPoints = new LinkedList<>();
        this.generated = false;
    }

    @Override
    public void load(KernelEngine engine) {
        if (!generated) {
            generateLevel(engine);

            spawnPoints.put(pacMan, pacMan.getCoordinates());
            for (Ghost ghost : getGeneratedGhosts())
                spawnPoints.put(ghost, ghost.getSpawnPoint());

            for (BordersTunnelTeleportEvent event : bordersTunnelTeleportEvents)
                event.load(engine);

            generated = true;
        }

        try {
            engine.setCurrentMap(originalMapLevel.getName());
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void unload(KernelEngine engine) {

    }

    /**
     * Generate the whole level
     * @param engine DeltaEngine
     */
    private void generateLevel(KernelEngine engine) {
        generateWalls(engine);
        generatePacGums();
        generateGhosts();
        generateBorderTunnel();
        generateGhostRegenerationPoint();

        try {
            engine.addMap(originalMapLevel);
        } catch (MapLevelAlreadyExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Generate points where ghost need to go to transform themselves from fleeing to normal state
     */
    private void generateGhostRegenerationPoint() {
        try {
            for (Ghost g : getGeneratedGhosts()) {
                GhostRegenerationPoint ghostRegenerationPoint = new GhostRegenerationPoint(g);
                ghostRegenerationPoints.add(ghostRegenerationPoint);
                originalMapLevel.addEntity(ghostRegenerationPoint);
            }
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Generate tunnel on each side of the map
     */
    private void generateBorderTunnel() {
        bordersTunnelTeleportEvents.add(new BordersTunnelTeleportEvent(this.getMapLevel(), pacMan));

        for (Ghost ghost : getGeneratedGhosts())
            bordersTunnelTeleportEvents.add(new BordersTunnelTeleportEvent(this.getMapLevel(), ghost));
    }

    /**
     * Generate ghosts
     */
    private void generateGhosts() {
        try {
            Ghost blinky = Ghosts.BLINKY.build(level, new Coordinates<>(9., 8.), new Coordinates<>(9.5, 10.5));
            originalMapLevel.addEntity(blinky);
            generatedGhosts.add(blinky);

            Ghost pinky = Ghosts.PINKY.build(level, new Coordinates<>(9., 10.), new Coordinates<>(9.5, 10.5));
            originalMapLevel.addEntity(pinky);
            generatedGhosts.add(pinky);

            Ghost inky = Ghosts.INKY.build(level, new Coordinates<>(8., 10.), new Coordinates<>(9.5, 10.5));
            originalMapLevel.addEntity(inky);
            generatedGhosts.add(inky);

            Ghost clyde = Ghosts.CLYDE.build(level, new Coordinates<>(10., 10.), new Coordinates<>(9.5, 10.5));
            originalMapLevel.addEntity(clyde);
            generatedGhosts.add(clyde);
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Generate all pac-gums and super-pac-gums
     */
    private void generatePacGums() {
        addForbiddenZones();
        addSuperPacGumsZones();

        nbOfGeneratedPacGums = 0;

        try {
            for (Cell cell : originalMapLevel.getCells()) {
                if (cell.getCoordinates().getY() >= originalMapLevel.getHeight() - GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_SIZE)
                    continue;
                if (zonesSpawnPacGumsProhibited.contains(cell.getCoordinates())) continue;
                boolean superPacGum = (zonesSpawnSuperPacGum.contains(cell.getCoordinates()));

                Coordinates<Integer> cellCoords = cell.getCoordinates();
                PacGum pacGum = new PacGum(new Coordinates<>(cellCoords.getX().doubleValue(), cellCoords.getY().doubleValue()), superPacGum);
                pacGum.setCollisionEvent(pacMan, new PacGumEatEvent(level, pacGum, superPacGum));
                originalMapLevel.addEntity(pacGum);
                nbOfGeneratedPacGums += 1;
            }
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Set coordinates of super-pacgums
     */
    private void addSuperPacGumsZones() {
        zonesSpawnSuperPacGum.add(new Coordinates<>(1, 2));
        zonesSpawnSuperPacGum.add(new Coordinates<>(17, 2));
        zonesSpawnSuperPacGum.add(new Coordinates<>(1, 16));
        zonesSpawnSuperPacGum.add(new Coordinates<>(17, 16));
    }

    /**
     * Add zones where pac-gums are prohibited (like PacMan spawn location for example)
     */
    private void addForbiddenZones() {
        zonesSpawnPacGumsProhibited.add(new Coordinates<>(pacMan.getCoordinates().getX().intValue(), pacMan.getCoordinates().getY().intValue()));

        for (int x = 5; x <= 13; x++) {
            for (int y = 7; y <= 13; y++) {
                zonesSpawnPacGumsProhibited.add(new Coordinates<>(x, y));
            }
        }

        for (int y = 8; y <= 12; y++) {
            for (int x = 0; x <= 3; x++)
                zonesSpawnPacGumsProhibited.add(new Coordinates<>(x, y));

            for (int x = 15; x <= 18; x++)
                zonesSpawnPacGumsProhibited.add(new Coordinates<>(x, y));
        }
    }

    /**
     * Generate walls of the map
     * @param engine DeltaEngine
     */
    private void generateWalls(KernelEngine engine) {
        int width = 19, height = 22;
        MapLevelBuilder levelBuilder = new MapLevelBuilder("PuckMan - Original Map", width, height + GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_SIZE);

        List<Cell> walls = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0)); // Upper border
            walls.add(new Wall(x, height - 1)); // Down border
        }

        for (int y = 1; y < height - 1; y++) {
            if (y == 10 || y == 8 || y == 12) continue;
            walls.add(new Wall(0, y)); // Left border
            walls.add(new Wall(width - 1, y)); // Right border
        }

        // y = 1
        walls.add(new Wall(9, 1));


        // y = 2 & 3
        for (int y = 2; y <= 3; y++) {
            for (int x = 2; x <= 3; x++) {
                walls.add(new Wall(x, y));
            }

            for (int x = 5; x <= 7; x++) {
                walls.add(new Wall(x, y));
            }

            walls.add(new Wall(9, y));

            for (int x = 11; x <= 13; x++) {
                walls.add(new Wall(x, y));
            }

            for (int x = 15; x <= 16; x++) {
                walls.add(new Wall(x, y));
            }
        }

        // y = 4 free

        // y = 5
        for (int x = 2; x <= 3; x++) {
            walls.add(new Wall(x, 5));
        }

        walls.add(new Wall(5, 5));

        for (int x = 7; x <= 11; x++) {
            walls.add(new Wall(x, 5));
        }

        walls.add(new Wall(13, 5));

        for (int x = 15; x <= 16; x++) {
            walls.add(new Wall(x, 5));
        }


        // y = 6
        for (int x = 5; x <= 13; x += 4)
            walls.add(new Wall(x, 6));


        // y = 7
        for (int x = 1; x <= 3; x++) {
            walls.add(new Wall(x, 7));
        }

        for (int x = 5; x <= 7; x++)
            walls.add(new Wall(x, 7));
        walls.add(new Wall(9, 7));
        for (int x = 11; x <= 13; x++)
            walls.add(new Wall(x, 7));
        for (int x = 15; x <= 17; x++) {
            walls.add(new Wall(x, 7));
        }

        // y = 8
        walls.add(new Wall(3, 8));
        walls.add(new Wall(5, 8));
        walls.add(new Wall(13, 8));
        walls.add(new Wall(15, 8));

        // y = 9
        walls.add(new Wall(1, 9));
        walls.add(new Wall(2, 9));
        walls.add(new Wall(3, 9));
        walls.add(new Wall(5, 9));
        walls.add(new Wall(7, 9));
        walls.add(new Wall(8, 9));
        walls.add(new GhostDoor(9, 9));
        walls.add(new Wall(10, 9));
        walls.add(new Wall(11, 9));
        walls.add(new Wall(13, 9));
        walls.add(new Wall(15, 9));
        walls.add(new Wall(16, 9));
        walls.add(new Wall(17, 9));

        // y = 10
        walls.add(new Wall(7, 10));
        walls.add(new Wall(11, 10));

        // y = 11
        walls.add(new Wall(1, 11));
        walls.add(new Wall(2, 11));
        walls.add(new Wall(3, 11));
        walls.add(new Wall(5, 11));
        walls.add(new Wall(7, 11));
        walls.add(new Wall(8, 11));
        walls.add(new Wall(9, 11));
        walls.add(new Wall(10, 11));
        walls.add(new Wall(11, 11));
        walls.add(new Wall(13, 11));
        walls.add(new Wall(15, 11));
        walls.add(new Wall(16, 11));
        walls.add(new Wall(17, 11));

        // y = 12
        walls.add(new Wall(3, 12));
        walls.add(new Wall(5, 12));
        walls.add(new Wall(13, 12));
        walls.add(new Wall(15, 12));

        // y = 13
        for (int x = 1; x <= 3; x++)
            walls.add(new Wall(x, 13));
        walls.add(new Wall(5, 13));
        for (int x = 7; x <= 11; x++)
            walls.add(new Wall(x, 13));
        walls.add(new Wall(13, 13));
        for (int x = 15; x <= 17; x++)
            walls.add(new Wall(x, 13));

        // y = 14
        walls.add(new Wall(9, 14));

        // y = 15
        for (int x = 2; x <= 3; x++)
            walls.add(new Wall(x, 15));
        for (int x = 5; x <= 7; x++)
            walls.add(new Wall(x, 15));
        walls.add(new Wall(9, 15));
        for (int x = 11; x <= 13; x++)
            walls.add(new Wall(x, 15));
        for (int x = 15; x <= 16; x++)
            walls.add(new Wall(x, 15));

        // y = 16
        walls.add(new Wall(3, 16));
        walls.add(new Wall(15, 16));

        // y = 17
        for (int x = 1; x <= 7; x += 2)
            walls.add(new Wall(x, 17));
        for (int x = 8; x <= 10; x++)
            walls.add(new Wall(x, 17));
        for (int x = 11; x <= 17; x += 2)
            walls.add(new Wall(x, 17));

        // y = 18
        walls.add(new Wall(5, 18));
        walls.add(new Wall(9, 18));
        walls.add(new Wall(13, 18));

        // y = 19
        for (int x = 2; x <= 7; x++)
            walls.add(new Wall(x, 19));
        walls.add(new Wall(9, 19));
        for (int x = 11; x <= 16; x++)
            walls.add(new Wall(x, 19));

        try {
            for (Cell c : walls) {
                levelBuilder.setCell(c);
                zonesSpawnPacGumsProhibited.add(c.getCoordinates());
            }
        } catch (MapLevelBuilderCellCoordinatesStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }

        originalMapLevel = levelBuilder.build();

        try {
            originalMapLevel.addEntity(pacMan);
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public @NotNull MapLevel getMapLevel() {
        return originalMapLevel;
    }

    @Override
    public int getNbOfGeneratedPacGums() {
        return nbOfGeneratedPacGums;
    }

    @Override
    public @NotNull Collection<Ghost> getGeneratedGhosts() {
        return generatedGhosts;
    }

    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return spawnPoints;
    }
}
