package model.elements.entities.ghosts;

import config.ghosts.GhostConfiguration;
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
    private final List<Sprite> normalSprites, deadSprites;
    private boolean isScared, isDead;
    private final double normalSpeed, scaredSpeed;

    public Ghost(String name, MapLevel currentMap, List<Sprite> normalSprites, Sprite scaredSprite, GhostAI ghostAI, double normalSpeed, double scaredSpeed) {
        super(name, new Coordinates<>(0.0, 0.0), normalSprites.get(0), new Dimension(0.9, 0.9));
        this.currentMap = currentMap;
        this.scaredSprite = scaredSprite;
        this.deadSprites = GhostConfiguration.CONF_DEAD_GHOST_EYES_SPRITES;
        this.normalSprites = normalSprites;
        this.isScared = false;
        this.isDead = false;


        try {
            setAI(ghostAI);
        } catch (AIAlreadyAttachedException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }

        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;

        setSpeed(normalSpeed);
    }

    public void setScared(boolean isScared) {
        this.isScared = isScared;

        setSpeed((isScared) ? scaredSpeed : normalSpeed);
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
        if (isDead) return switch(this.getDirection()){
                case RIGHT -> deadSprites.get(1);
                case UP -> deadSprites.get(2);
                case DOWN -> deadSprites.get(3);
                default -> deadSprites.get(0);
        };
        else return switch (this.getDirection()) {
                case RIGHT -> normalSprites.get(1);
                case UP -> normalSprites.get(2);
                case DOWN -> normalSprites.get(3);
                default -> normalSprites.get(0);
        };
    }
}
