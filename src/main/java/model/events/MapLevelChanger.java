package model.events;

import config.events.MapLevelChangerConfiguration;
import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.Game;
import model.elements.entities.PacMan;

public class MapLevelChanger extends Event {

    private final MapLevel mapLevel;
    private final Cell teleporterCell;
    private final Sprite defaultSpriteCell;
    private boolean isActivated;

    public MapLevelChanger(MapLevel mapLevel) {
        this.mapLevel = mapLevel;
        Coordinates<Integer> coordsCell = MapLevelChangerConfiguration.CONF_CELL_COORDINATES_TELEPORT;
        teleporterCell = mapLevel.getCell(coordsCell.getX(), coordsCell.getY());
        defaultSpriteCell = teleporterCell.getSprite();
    }

    @Override
    public void checkEvent() {
        if (!isActivated) return;

        PacMan pacMan = (PacMan) mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        // TODO Collision
        /*
            if (collision)
                game.nextLevel();
         */

        if (false)
            runTriggers();
    }

    public boolean isPortalActivated() {
        return isActivated;
    }

    public void setPortalActivated(boolean isActivated) {
        this.isActivated = isActivated;

        Sprite newSprite = (isActivated) ? MapLevelChangerConfiguration.CONF_ACTIVATED_TELEPORTER_CELL : defaultSpriteCell;
        teleporterCell.setSprite(newSprite);
    }
}
