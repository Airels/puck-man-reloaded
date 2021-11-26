package model.elements.cells;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.RestrictiveCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import model.elements.entities.ghosts.Ghost;
import view.colors.Color;

/**
 * The GhostDoor. Only Ghosts can cross this cell.
 */
public class GhostDoor extends Cell {

    /**
     * Default constructor
     * @param x abscissa position of the cell in the map
     * @param y ordinate position of the cell in the map
     */
    public GhostDoor(int x, int y) {
        super(x, y, new Rectangle(Color.GAINSBORO.getEngineColor()));
    }

    @Override
    public boolean isCrossableBy(Entity entity) {
        if (!entity.getClass().equals(Ghost.class)) return false;

        Ghost ghost = ((Ghost) entity);
        if (ghost.isFleeing())
            return true;

        Coordinates<Double> ghostCoords = ghost.getCoordinates();
        Dimension ghostDimension = ghost.getDimension();
        Coordinates<Double> botRightCollisionPoint = CollisionPositions.RIGHT_BOT.calcPosition(ghostCoords, ghostDimension);

        return (botRightCollisionPoint.getY() > this.getCoordinates().getY());
    }
}
