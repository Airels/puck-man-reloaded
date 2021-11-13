package view.maps.original_level;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.elements.Cell;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import model.elements.cells.Wall;
import model.elements.entities.PacMan;
import model.loadables.LoadableMap;

import java.util.ArrayList;
import java.util.List;

public class OriginalLevelMap implements LoadableMap {

    private PacMan pacMan;
    private MapLevel originalLevel;

    public OriginalLevelMap(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void load(KernelEngine engine) {
        if (originalLevel == null) {
            int width = 19, height = 22;
            MapLevelBuilder levelBuilder = new MapLevelBuilder("OriginalMap", width, height);

            List<Cell> walls = new ArrayList<>();

            for (int x = 0; x < width; x++) {
                walls.add(new Wall(x, 0)); // Upper border
                walls.add(new Wall(x, height-1)); // Down border
            }

            for (int y = 0; y < height; y++) {
                walls.add(new Wall(0, y)); // Left border
                walls.add(new Wall(width-1, y)); // Right border
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
            walls.add(new Wall(5, 6));
            walls.add(new Wall(9, 6));
            walls.add(new Wall(13, 6));


            // y = 7
            for (int x = 1; x <= 3; x++) {
                walls.add(new Wall(x, 7));
            }
            walls.add(new Wall(5, 7));
            walls.add(new Wall(9, 7));
            walls.add(new Wall(13, 7));
            for (int x = 15; x <= 17; x++) {
                walls.add(new Wall(x, 7));
            }

            // TODO

            for (Cell c : walls)
                levelBuilder.setCell(c);

            originalLevel = levelBuilder.build();

            try {
                originalLevel.addEntity(pacMan);
                engine.addMap(originalLevel);
            } catch (MapLevelAlreadyExistException | MapLevelEntityNameStackingException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            engine.setCurrentMap(originalLevel.getName());
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void unload(KernelEngine engine) {

    }
}
