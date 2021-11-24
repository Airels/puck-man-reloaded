package view;

import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * Stores multiples sprites, one for each direction.
 * Used to store entities sprites for each direction.
 * Then, when an entity will change its direction, it will also change the sprite easily.
 */
public final class SpriteContainer {

    private final Sprite leftSprite, upSprite, downSprite, rightSprite;
    private Sprite lastSpriteGet;

    /**
     * Default constructor, giving one Sprite for each direction
     * @param leftSprite Sprite for left direction
     * @param upSprite Sprite for up direction
     * @param downSprite Sprite for down direction
     * @param rightSprite Sprite for right direction
     */
    public SpriteContainer(Sprite leftSprite, Sprite upSprite, Sprite downSprite, Sprite rightSprite) {
        this.leftSprite = leftSprite;
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.rightSprite = rightSprite;

        this.lastSpriteGet = leftSprite;
    }

    /**
     * Returns Sprite for left direction
     * @return Sprite
     */
    public Sprite getLeftSprite() {
        try {
            return leftSprite;
        } finally {
            lastSpriteGet = leftSprite;
        }
    }

    /**
     * Returns Sprite for up direction
     * @return Sprite
     */
    public Sprite getUpSprite() {
        try {
            return upSprite;
        } finally {
            lastSpriteGet = upSprite;
        }
    }

    /**
     * Returns Sprite for down direction
     * @return Sprite
     */
    public Sprite getDownSprite() {
        try {
            return downSprite;
        } finally {
            lastSpriteGet = downSprite;
        }
    }

    /**
     * Returns Sprite for right direction
     * @return Sprite
     */
    public Sprite getRightSprite() {
        try {
            return rightSprite;
        } finally {
            lastSpriteGet = rightSprite;
        }
    }

    /**
     * Returns sprite for given direction
     * @param direction Sprite direction to get
     * @return Sprite
     */
    public Sprite getSprite(Direction direction) {
        return switch(direction) {
            case UP -> getUpSprite();
            case DOWN -> getDownSprite();
            case LEFT -> getLeftSprite();
            case RIGHT -> getRightSprite();
            default -> lastSpriteGet;
        };
    }
}
