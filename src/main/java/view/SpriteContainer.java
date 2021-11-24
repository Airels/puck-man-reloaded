package view;

import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public final class SpriteContainer {

    private final Sprite leftSprite, upSprite, downSprite, rightSprite;
    private Sprite lastSpriteGet;

    public SpriteContainer(Sprite leftSprite, Sprite upSprite, Sprite downSprite, Sprite rightSprite) {
        this.leftSprite = leftSprite;
        this.upSprite = upSprite;
        this.downSprite = downSprite;
        this.rightSprite = rightSprite;

        this.lastSpriteGet = leftSprite;
    }

    public Sprite getLeftSprite() {
        try {
            return leftSprite;
        } finally {
            lastSpriteGet = leftSprite;
        }
    }

    public Sprite getUpSprite() {
        try {
            return upSprite;
        } finally {
            lastSpriteGet = upSprite;
        }
    }

    public Sprite getDownSprite() {
        try {
            return downSprite;
        } finally {
            lastSpriteGet = downSprite;
        }
    }

    public Sprite getRightSprite() {
        try {
            return rightSprite;
        } finally {
            lastSpriteGet = rightSprite;
        }
    }

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
