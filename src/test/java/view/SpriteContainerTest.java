package view;

import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpriteContainerTest {

    final Sprite
            leftSprite = new Rectangle(Color.RED),
            upSprite = new Rectangle(Color.GREEN),
            downSprite = new Rectangle(Color.BLUE),
            rightSprite = new Rectangle(Color.CYAN);

    final SpriteContainer spriteContainer = new SpriteContainer(leftSprite, upSprite, downSprite, rightSprite);


    @Test
    void getLeftSprite() {
        assertEquals(leftSprite, spriteContainer.getLeftSprite());
    }

    @Test
    void getUpSprite() {
        assertEquals(upSprite, spriteContainer.getUpSprite());
    }

    @Test
    void getDownSprite() {
        assertEquals(downSprite, spriteContainer.getDownSprite());
    }

    @Test
    void getRightSprite() {
        assertEquals(rightSprite, spriteContainer.getRightSprite());
    }

    @Test
    void getSprite() {
        assertEquals(leftSprite, spriteContainer.getSprite(Direction.LEFT));
        assertEquals(upSprite, spriteContainer.getSprite(Direction.UP));
        assertEquals(downSprite, spriteContainer.getSprite(Direction.DOWN));
        assertEquals(rightSprite, spriteContainer.getSprite(Direction.RIGHT));
    }
}