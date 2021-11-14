package model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.UncrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import view.colors.Color;

public class GhostDoor extends UncrossableCell {

    public GhostDoor(int x, int y) {
        super(x, y, new Rectangle(Color.GAINSBORO.getEngineColor()));
    }
}
