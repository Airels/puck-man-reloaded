package config.events;

import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * Configurations of the Map Changer, allowing player to change levels
 */
public final class LevelChangerConfiguration {

    public final static Sprite CONF_ACTIVATED_TELEPORTER_CELL = new Rectangle(Color.GREEN);
    public final static Dimension CONF_DEFAULT_TELEPORTER_DIMENSION = new Dimension(1.0, 1.0);
    public final static Dimension CONF_DEFAULT_TELEPORTER_HITBOX_SIZE = new Dimension(0.1, 0.1);
}
