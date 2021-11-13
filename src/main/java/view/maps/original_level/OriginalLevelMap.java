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
            MapLevelBuilder levelBuilder = new MapLevelBuilder("OriginalMap", 18, 22);

            List<Cell> borders = new ArrayList<>();

            for (int x = 0; x < 18; x++) {
                borders.add(new Wall(x, 0)); // Upper border
                borders.add(new Wall(x, 21)); // Down border
            }

            for (int y = 0; y < 22; y++) {
                borders.add(new Wall(0, y)); // Left border
                borders.add(new Wall(17, y)); // Right border
            }

            for (Cell c : borders)
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
