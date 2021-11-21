package config.events;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

public class MapLevelChangerConfiguration {

    public final static Coordinates<Integer> CONF_CELL_COORDINATES_TELEPORT = new Coordinates<>(9, 12);
    public final static Sprite CONF_ACTIVATED_TELEPORTER_CELL = new Rectangle(Color.GREEN);
}
