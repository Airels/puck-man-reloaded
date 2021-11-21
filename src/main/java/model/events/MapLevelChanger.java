package model.events;

import config.events.MapLevelChangerConfiguration;
import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.Game;
import model.elements.entities.PacMan;

public class MapLevelChanger extends Event {

    private final Game game;
    private final MapLevel mapLevel;
    private final Cell teleporterCell;
    private final Sprite defaultSpriteCell;
    private boolean isActivated;

    public MapLevelChanger(Game game, MapLevel mapLevel) {
        this.game = game;
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
            game.nextLevel();
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
