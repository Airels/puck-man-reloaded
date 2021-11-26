package config.events;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

public final class MapLevelChangerConfiguration {

    public final static Coordinates<Double> CONF_CELL_COORDINATES_TELEPORT = new Coordinates<>(9.0, 12.0);
    public final static Sprite CONF_ACTIVATED_TELEPORTER_CELL = new Rectangle(Color.GREEN);
    public final static Dimension CONF_DEFAULT_TELEPORTER_DIMENSION = new Dimension(1.0, 1.0);
}
