package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.ghosts.GhostAI;

public class Ghost extends Entity {

    private final MapLevel currentMap;
    private final Sprite normalSprite, scaredSprite;
    private boolean isScared;

    public Ghost(String name, MapLevel currentMap, Sprite normalSprite, Sprite scaredSprite, GhostAI ghostAI) {
        super(name, new Coordinates<>(0.0, 0.0), normalSprite, new Dimension(1, 1));
        this.currentMap = currentMap;
        this.normalSprite = normalSprite;
        this.scaredSprite = scaredSprite;

        this.isScared = false;

        try {
            setAI(ghostAI);
        } catch (AIAlreadyAttachedException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }
    }

    public void setScared(boolean isScared) {
        this.isScared = isScared;
    }

    public boolean isScared() {
        return isScared;
    }

    public MapLevel getMapLevel() {
        return currentMap;
    }

    @Override
    public Sprite getSprite() {
        return (isScared) ? scaredSprite : normalSprite;
    }
}
