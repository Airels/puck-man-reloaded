package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import model.ai.ghosts.*;

public enum Ghosts {

    BLINKY("Blinky", new Rectangle(Color.RED), new Rectangle(Color.BLUE), new BlinkyAI()),
    PINKY("Pinky", new Rectangle(view.colors.Color.PINK.getEngineColor()), new Rectangle(Color.BLUE), new PinkyAI()),
    INKY("Inky", new Rectangle(Color.CYAN), new Rectangle(Color.BLUE), new InkyAI()),
    CLYDE("Clyde", new Rectangle(view.colors.Color.ORANGE.getEngineColor()), new Rectangle(Color.BLUE), new ClydeAI());

    private final String name;
    private final Sprite normalSprite, scaredSprite;
    private final GhostAI ai;

    Ghosts(String name, Sprite normalSprite, Sprite scaredSprite, GhostAI ai) {
        this.name = name;
        this.normalSprite = normalSprite;
        this.scaredSprite = scaredSprite;
        this.ai = ai;
    }

    public Ghost build(MapLevel currentMap) {
        return new Ghost(name, currentMap, normalSprite, scaredSprite, ai);
    }
}
