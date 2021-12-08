package model.levels.generators;

import config.game.GlobalHUDConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.VoidCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import fr.r1r0r0.deltaengine.view.colors.Color;
import main.Main;
import model.actions.events.PacGumEatEvent;
import model.elements.cells.GhostDoor;
import model.elements.cells.Wall;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.items.PacGum;
import model.levels.Level;

import java.util.*;

/**
 * Generator of Map Level
 */
public class MapLevelGenerator {

    private static int id = 0;
    public final static int MAP_WIDTH = 19, MAP_HEIGHT = 22, GHOST_PRISON_WIDTH = 7, GHOST_PRISON_HEIGHT = 5;
    public final static Coordinates<Integer> GHOST_PRISON_POSITION = new Coordinates<>(6, 8);
    private final Collection<Coordinates<Integer>> zonesSpawnPacGumsProhibited, zonesSpawnSuperPacGum;
    private final PacMan pacMan;
    private int nbOfGeneratedPacGums;
    private final Level level;

    private MapLevel generatedMap;

    public MapLevelGenerator(Level level, PacMan pacMan) {
        this.pacMan = pacMan;
        this.level = level;
        zonesSpawnPacGumsProhibited = new ArrayList<>();
        zonesSpawnSuperPacGum = new ArrayList<>();
        nbOfGeneratedPacGums = 0;
    }

    public MapLevel generate() {
        id++;

        generateWalls();
        generatePacGums();
        return generatedMap;
    }

    public int getNbOfGeneratedPacGums() {
        return 0;
    }

    public Collection<Ghost> getGeneratedGhosts() {
        return null;
    }

    public Map<Entity, Coordinates<Double>> getSpawnPoint() {
        return null;
    }

    /**
     * Generate all pac-gums and super-pac-gums
     */
    private void generatePacGums() {
        addForbiddenZones();
        // addSuperPacGumsZones();

        nbOfGeneratedPacGums = 0;

        try {
            for (Cell cell : generatedMap.getCells()) {
                if (cell.getCoordinates().getY() >= generatedMap.getHeight() - GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_SIZE)
                    continue;
                if (!cell.isCrossableBy(pacMan)) continue;
                if (zonesSpawnPacGumsProhibited.contains(cell.getCoordinates())) continue;
                boolean superPacGum = (zonesSpawnSuperPacGum.contains(cell.getCoordinates()));

                Coordinates<Integer> cellCoords = cell.getCoordinates();
                PacGum pacGum = new PacGum(new Coordinates<>(cellCoords.getX().doubleValue(), cellCoords.getY().doubleValue()), superPacGum);
                // pacGum.setCollisionEvent(pacMan, new PacGumEatEvent(level, pacGum, superPacGum));
                generatedMap.addEntity(pacGum);
                nbOfGeneratedPacGums += 1;
            }
        } catch (MapLevelEntityNameStackingException e) {
            new Dialog(Main.APPLICATION_NAME, "Random level map pacgums generation error", e).show();
        }
    }

    /**
     * Add zones where pac-gums are prohibited (like PacMan spawn location for example)
     */
    private void addForbiddenZones() {
        zonesSpawnPacGumsProhibited.add(new Coordinates<>(pacMan.getCoordinates().getX().intValue(), pacMan.getCoordinates().getY().intValue()));

        int prisonGhostX = GHOST_PRISON_POSITION.getX(),
                prisonGhostY = GHOST_PRISON_POSITION.getY();

        for (int x = prisonGhostX; x < GHOST_PRISON_WIDTH+prisonGhostX; x++) {
            for (int y = prisonGhostY; y < GHOST_PRISON_HEIGHT+prisonGhostY; y++) {
                zonesSpawnPacGumsProhibited.add(new Coordinates<>(x, y));
            }
        }
    }

    private void generateWalls() {
        try {
            MapLevelBuilder levelBuilder = new MapLevelBuilder("PuckMan - Level" + id, MAP_WIDTH, MAP_HEIGHT + GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_SIZE);

            Cell[][] map = new Cell[17+2][21+2];
            for (int i = 0; i < map.length; i++)
                for (int j = 0; j < map[i].length; j++)
                    map[i][j] = new Wall(i, j);

            for (int x = 1; x <= 15; x += 2) {
                for (int y = 1; y <= 17; y += 2) {
                    Chunk[][] cases = test();
                    Cell[][] cells = generateChunk(x, y, cases[0][0]);
                    for (int i = 0; i < cells.length; i++) {
                        for (int j = 0; j < cells.length; j++) {
                            Cell c = cells[i][j];
                            if (c instanceof VoidCell)
                                map[i+x][j+y] = c;
                        }
                    }
                }
            }

            int coordX = GHOST_PRISON_POSITION.getX(),
                    coordY = GHOST_PRISON_POSITION.getY();
            for (int x = coordX; x < GHOST_PRISON_WIDTH+coordX; x++) {
                for (int y = coordY; y < GHOST_PRISON_HEIGHT+coordY; y++) {
                    if (x == coordX+3 && y == coordY+1)
                        map[x][y] = new GhostDoor(x, y);
                    else if (y == coordY || y == coordY+GHOST_PRISON_HEIGHT-1)
                        map[x][y] = new VoidCell(x, y);
                    else if (x == coordX || x == coordX+GHOST_PRISON_WIDTH-1)
                        map[x][y] = new VoidCell(x, y);
                    else if (x == coordX+1 || x == coordX+GHOST_PRISON_WIDTH-2)
                        map[x][y] = new Wall(x, y);
                    else if (y == coordY+1 || y == coordY+GHOST_PRISON_HEIGHT-2)
                        map[x][y] = new Wall(x, y);
                    else
                        map[x][y] = new VoidCell(x, y);
                }
            }

            for (Cell[] value : map)
                for (Cell cell : value) levelBuilder.setCell(cell);

            generatedMap = levelBuilder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Cell[][] generateChunk(int x, int y, Chunk c) {
        Cell[][] cells = new Cell[3][3];
        for (int a = 0; a < 3; a++)
            for (int b = 0; b < 3; b++)
                cells[a][b] = new Wall(x+a, y+b);

        if (c.isUp()) {
            for (int i = 0; i < cells.length; i++)
                cells[i][0] = new VoidCell(x+i, y);
        }

        if (c.isDown()) {
            for (int i = 0; i < cells.length; i++)
                cells[i][2] = new VoidCell(x+i, y+2);
        }

        if (c.isLeft()) {
            for (int i = 0; i < cells.length; i++)
                cells[0][i] = new VoidCell(x, y+i);
        }

        if (c.isRight()) {
            for (int i = 0; i < cells.length; i++)
                cells[2][i] = new VoidCell(x+2, y+i);
        }

        return cells;
    }

    public static Chunk[][] test() {
        Chunk[][] cases = new Chunk[1][2];

        for (int x = 0; x < cases.length; x++) {
            for (int y = 0; y < cases[x].length; y++) {
                cases[x][y] = new Chunk();
            }
        }

        return cases;
    }
}

class WhiteCell extends CrossableCell {
    public WhiteCell(int x, int y) {
        super(x, y, new Rectangle(Color.WHITE));
    }
}

class GreenCell extends CrossableCell {
    public GreenCell(int x, int y) {
        super(x, y, new Rectangle(Color.GREEN));
    }
}