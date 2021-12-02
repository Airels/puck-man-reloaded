package model.levels.generators.vidal;

import config.game.GlobalHUDConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelBuilderCellCoordinatesStackingException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.VoidCell;
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
import model.events.GhostRegenerationPoint;
import model.levels.Level;
import model.levels.generators.vidal.GenericTunnel;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RandomLevel implements LoadableMap {

    private static final Random RANDOM = new Random();
    private static int ID = 0;

    private static final int WIDTH = 19;
    private static final int HEIGHT = 22;

    private static final int GHOST_PRISON_WIDTH = 7;
    private static final int GHOST_PRISON_HEIGHT = 5;

    private static final double TUNNEL_CHECK = 0.1;
    private static final double TUNNEL_TELEPORT_SECURITY = 0.01;
    private static final int TUNNEL_WIDTH = 4;
    private static final int TUNNEL_HEIGHT = 3;

    private final int id;
    private GenericTunnel leftTunnel;
    private GenericTunnel rightTunnel;
    private int ghostPrisonX;
    private int ghostPrisonY;


    private final Collection<Coordinates<Integer>> zonesSpawnPacGumsProhibited;
    private final Collection<Coordinates<Integer>> zonesSpawnSuperPacGum;
    private final Collection<Ghost> generatedGhosts;
    private final PacMan pacMan;
    private final Level level;
    private final Map<Entity,Coordinates<Double>> spawnPoints;
    private final Collection<GhostRegenerationPoint> ghostRegenerationPoints;
    private MapLevel originalMapLevel;
    private int nbOfGeneratedPacGums;

    public RandomLevel (Level level) {
        this.id = ID;
        ID++;

        this.level = level;
        this.pacMan = level.getGame().getPacMan();
        zonesSpawnPacGumsProhibited = new ArrayList<>();
        zonesSpawnSuperPacGum = new ArrayList<>();

        generatedGhosts = new LinkedList<>();
        spawnPoints = new HashMap<>();
        //bordersTunnelTeleportEvents = new LinkedList<>();
        ghostRegenerationPoints = new LinkedList<>();
    }

    @Override
    public void load (KernelEngine engine) {
        generateLevel();

        /*
        spawnPoints.put(pacMan, pacMan.getCoordinates());
        for (Ghost ghost : getGeneratedGhosts())
            spawnPoints.put(ghost, ghost.getSpawnPoint());

        for (BordersTunnelTeleportEvent event : bordersTunnelTeleportEvents)
            event.load(engine);

        try {
            engine.setCurrentMap(originalMapLevel.getName());
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
            TODO Remplace ton print stack trace par ça :
            new Dialog(Main.APPLICATION_NAME, "", e).show();
        }
        */
    }

    @Override
    public void unload (KernelEngine engine) {
        engine.removeMap(originalMapLevel.getName());
    }

    private void generateLevel () {
        boolean[][] cellsCreate = new boolean[WIDTH][HEIGHT];
        boolean[][] canPutPacGum = new boolean[WIDTH][HEIGHT];
        MapLevelBuilder mapLevelBuilder = generatePlainMap(cellsCreate);

        generateTunnels(mapLevelBuilder,cellsCreate);
        generateGhostPrison(mapLevelBuilder,cellsCreate,canPutPacGum);

        generatePath(mapLevelBuilder,cellsCreate,canPutPacGum);

        createTunnelEvent();
        /*
        generateWalls(engine);
        generatePacGums();
        generateGhosts();
        generateGhostRegenerationPoint();

        try {
            engine.addMap(originalMapLevel);
        } catch (MapLevelAlreadyExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
        */
    }

    private static void setCell (MapLevelBuilder mapLevelBuilder, boolean[][] cellsCreate, Cell cell) {
        try {
            mapLevelBuilder.setCell(cell);
        } catch (MapLevelBuilderCellCoordinatesStackingException e) {
            //e.printStackTrace();
        }
        Coordinates<Integer> coordinates = cell.getCoordinates();
        cellsCreate[coordinates.getX()][coordinates.getY()] = true;
    }

    private MapLevelBuilder generatePlainMap (boolean[][] cellsCreate) {
        MapLevelBuilder mapLevelBuilder = new MapLevelBuilder("random" + id,WIDTH,HEIGHT);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCell(mapLevelBuilder, cellsCreate, new Wall(x, y));
            }
        }
        return mapLevelBuilder;
    }

    private void generateTunnels (MapLevelBuilder mapLevelBuilder, boolean[][] cellsCreate) {
        /**
         * TODO
         * faire en sorte de passer par des events pour ameliorer la complexite des calculs
         */

        // entre 1 et HEIGHT-1
        int leftPosition = 1 + RANDOM.nextInt(HEIGHT-2);
        int rightPosition = 1 + RANDOM.nextInt(HEIGHT-2);

        leftTunnel = GenericTunnel.createTunnel(
                -1+TUNNEL_CHECK,leftPosition,
                WIDTH-TUNNEL_CHECK-TUNNEL_TELEPORT_SECURITY,rightPosition);
        rightTunnel = GenericTunnel.createTunnel(
                WIDTH-TUNNEL_CHECK,rightPosition,
                TUNNEL_CHECK+TUNNEL_TELEPORT_SECURITY,leftPosition);


        setCell(mapLevelBuilder,cellsCreate,new VoidCell(0,leftPosition));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(HEIGHT-1,rightPosition));
        for (int i = 0 ; i < TUNNEL_WIDTH ; i++) {
            setCell(mapLevelBuilder,cellsCreate,new Wall(i, leftPosition - 1));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(i, leftPosition));
            setCell(mapLevelBuilder,cellsCreate,new Wall(i, leftPosition + 1));

            setCell(mapLevelBuilder,cellsCreate,new Wall(HEIGHT - 1 - i, rightPosition - 1));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(HEIGHT - 1 - i, rightPosition));
            setCell(mapLevelBuilder,cellsCreate,new Wall(HEIGHT - 1 - i, rightPosition + 1));
        }

    }

    private void generateGhostPrison (MapLevelBuilder mapLevelBuilder, boolean[][] cellsCreate,
                                      boolean[][] canPutPacGum) {

        // entre GHOST_PRISON_WIDTH + 2 et WIDTH - GHOST_PRISON_WIDTH - 2
        ghostPrisonX = GHOST_PRISON_WIDTH + 2 + RANDOM.nextInt(WIDTH-2*(GHOST_PRISON_WIDTH+2));
        // entre GHOST_PRISON_HEIGHT + 2 et HEIGHT - GHOST_PRISON_HEIGHT - 2
        ghostPrisonY = GHOST_PRISON_HEIGHT + 2 + RANDOM.nextInt(HEIGHT-2*(GHOST_PRISON_HEIGHT+2));

        /**
         * GENERE LA CAGE
         */

        for (int i = 0 ; i < GHOST_PRISON_WIDTH ; i++) {
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX + i, ghostPrisonY));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX + i, ghostPrisonY + GHOST_PRISON_HEIGHT - 1));
        }
        for (int i = 1 ; i < GHOST_PRISON_HEIGHT-1 ; i++) {
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX, ghostPrisonY + i));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX + GHOST_PRISON_WIDTH - 1, ghostPrisonY + i));
        }
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+2,ghostPrisonY+2));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+3,ghostPrisonY+2));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+4,ghostPrisonY+2));
        setCell(mapLevelBuilder,cellsCreate,new GhostDoor(ghostPrisonX+3,ghostPrisonY+1));
        for (int i = 1 ; i < GHOST_PRISON_WIDTH-1 ; i++) {
            for (int j = 1; j < GHOST_PRISON_HEIGHT - 1; j++) {
                setCell(mapLevelBuilder,cellsCreate,new Wall(ghostPrisonX + i, ghostPrisonY + j));
            }
        }

        /**
         * GENERE LES BORDURES ET LES PORTES
         */

        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-1,ghostPrisonY-1));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-1,ghostPrisonY+HEIGHT));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH,ghostPrisonY-1));
        setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH,ghostPrisonY+HEIGHT));

        int wallCreate = 0;
        double probability = 1. / WIDTH;
        for (int i = 0 ; i < WIDTH ; i++) {
            if (RANDOM.nextInt() < probability) {
                wallCreate++;
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY));
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY-1));
                canPutPacGum[ghostPrisonX+i][ghostPrisonY-1] = true;
                i++;
            } else setCell(mapLevelBuilder,cellsCreate,new Wall(ghostPrisonX+i,ghostPrisonY));
        }
        if (wallCreate == 0) {
            int i = RANDOM.nextInt(WIDTH);
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY-1));
            canPutPacGum[ghostPrisonX+i][ghostPrisonY-1] = true;
        }

        wallCreate = 0;
        probability = 1. / WIDTH;
        for (int i = 0 ; i < WIDTH ; i++) {
            if (RANDOM.nextInt() < probability) {
                wallCreate++;
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY+HEIGHT-1));
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY+HEIGHT));
                canPutPacGum[ghostPrisonX+i][ghostPrisonY+HEIGHT] = true;
                i++;
            } else setCell(mapLevelBuilder,cellsCreate,new Wall(ghostPrisonX+i,ghostPrisonY+HEIGHT-1));
        }
        if (wallCreate == 0) {
            int i = RANDOM.nextInt(WIDTH);
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY+HEIGHT-1));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+i,ghostPrisonY+HEIGHT));
            canPutPacGum[ghostPrisonX+i][ghostPrisonY+HEIGHT] = true;
        }

        wallCreate = 0;
        probability = 1. / HEIGHT;
        for (int i = 0 ; i < HEIGHT ; i++) {
            if (RANDOM.nextInt() < probability) {
                wallCreate++;
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-1,ghostPrisonY+i));
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-2,ghostPrisonY+i));
                canPutPacGum[ghostPrisonX-2][ghostPrisonY+i] = true;
            } else setCell(mapLevelBuilder,cellsCreate,new Wall(ghostPrisonX-1,ghostPrisonY+i));
        }
        if (wallCreate == 0) {
            int i = RANDOM.nextInt(WIDTH);
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-1,ghostPrisonY+i));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX-2,ghostPrisonY+i));
            canPutPacGum[ghostPrisonX-2][ghostPrisonY+i] = true;
        }

        wallCreate = 0;
        probability = 1. / HEIGHT;
        for (int i = 0 ; i < HEIGHT ; i++) {
            if (RANDOM.nextInt() < probability) {
                wallCreate++;
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH,ghostPrisonY+i));
                setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH+1,ghostPrisonY+i));
                canPutPacGum[ghostPrisonX+WIDTH+1][ghostPrisonY+i] = true;
            } else setCell(mapLevelBuilder,cellsCreate,new Wall(ghostPrisonX+WIDTH,ghostPrisonY+i));
        }
        if (wallCreate == 0) {
            int i = RANDOM.nextInt(WIDTH);
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH,ghostPrisonY+i));
            setCell(mapLevelBuilder,cellsCreate,new VoidCell(ghostPrisonX+WIDTH+1,ghostPrisonY+i));
            canPutPacGum[ghostPrisonX+WIDTH+1][ghostPrisonY+i] = true;
        }

    }

    private void generatePath (MapLevelBuilder mapLevelBuilder, boolean[][] cellsCreate, boolean[][] canPutPacGum) {

        System.out.println("generation aleatoire non focntionnel");
        System.exit(41);

        int x = RANDOM.nextInt(WIDTH);
        int y = RANDOM.nextInt(HEIGHT);
        while (cellsCreate[x][y]) {
            x = RANDOM.nextInt(WIDTH);
            y = RANDOM.nextInt(HEIGHT);
        }

        setCell(mapLevelBuilder,cellsCreate,new VoidCell(x,y));
        spawnPoints.put(pacMan,new Coordinates<>((double)x,(double)y));



        ArrayList<Coordinates<Integer>> cells = new ArrayList<>();
        for (x = 0 ; x < WIDTH ; x++) {
            for (y = 0 ; y < HEIGHT ; y++) {
                if ( ! cellsCreate[x][y]) cells.add(new Coordinates<>(x,y));
            }
        }

        while ( ! cells.isEmpty()) {

        }

    }

    private enum Dir {
        A(1,0),B(-1,0),C(0,1),D(0,-1);
        int x,y;
        Dir(int x,int y){this.x=x;this.y=y;}
        Coordinates<Integer> calc(Coordinates<Integer> a){return new Coordinates<>(a.getX()+x,a.getY()+y);}
    }

    private Set<Coordinates<Integer>> createPath (Coordinates<Integer> node, boolean[][] cellsCreate,
                                                  boolean[][] canPutPacGum) {
        HashSet<Coordinates<Integer>> set = new HashSet<>();

        return set;
    }

    private void createTunnelEvent () {
        leftTunnel.addCollisionEvent(pacMan);
        rightTunnel.addCollisionEvent(pacMan);
        for (Ghost ghost : generatedGhosts) {
            leftTunnel.addCollisionEvent(ghost);
            rightTunnel.addCollisionEvent(ghost);
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
