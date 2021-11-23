package model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.cells.UncrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * A classic blue wall.
 */
public class Wall extends UncrossableCell {

    public Wall(int x, int y) {
        super(x, y, new Rectangle(Color.BLUE));
    }
}
