package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.ghosts.*;

import java.util.List;

import static config.ghosts.GhostConfiguration.*;

public enum Ghosts {

    BLINKY(CONF_BLINKY_NAME, CONF_BLINKY_NORMAL_SPRITES, CONF_BLINKY_SCARED_SPRITE, CONF_BLINKY_AI),
    PINKY(CONF_PINKY_NAME, CONF_PINKY_NORMAL_SPRITES, CONF_PINKY_SCARED_SPRITE, CONF_PINKY_AI),
    INKY(CONF_INKY_NAME, CONF_INKY_NORMAL_SPRITES, CONF_INKY_SCARED_SPRITE, CONF_INKY_AI),
    CLYDE(CONF_CLYDE_NAME, CONF_CLYDE_NORMAL_SPRITES, CONF_CLYDE_SCARED_SPRITE, CONF_CLYDE_AI);

    private final String name;
    private final Sprite scaredSprite;
    private final GhostAI ai;
    private final List<Sprite> normalSprites;

    Ghosts(String name, List<Sprite> normalSprites, Sprite scaredSprite, GhostAI ai) {
        this.name = name;
        this.normalSprites = normalSprites;
        this.scaredSprite = scaredSprite;
        this.ai = ai;
    }

    public Ghost build(MapLevel currentMap) {
        return new Ghost(name, currentMap, normalSprites, scaredSprite, ai);
    }

    public Ghost build(MapLevel currentMap, Coordinates<Double> coords) {
        Ghost g = build(currentMap);
        g.setCoordinates(coords);
        return g;
    }
}
