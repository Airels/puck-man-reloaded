package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.ghosts.GhostAI;
import java.util.List;

public class Ghost extends Entity {

    private final MapLevel currentMap;
    private final Sprite scaredSprite;
    private final List<Sprite> normalSprites;
    private boolean isScared;

    public Ghost(String name, MapLevel currentMap, List<Sprite> normalSprites, Sprite scaredSprite, GhostAI ghostAI) {
        super(name, new Coordinates<>(0.0, 0.0), normalSprites.get(0), new Dimension(0.9, 0.9));
        this.currentMap = currentMap;
        this.scaredSprite = scaredSprite;
        this.normalSprites = normalSprites;
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
        if (isScared) return scaredSprite;
        else switch(this.getDirection()){
            case RIGHT:
                return normalSprites.get(1);
            case UP:
                return normalSprites.get(2);
            case DOWN:
                return normalSprites.get(3);
            default:
                return normalSprites.get(0);
        }
    }
}
