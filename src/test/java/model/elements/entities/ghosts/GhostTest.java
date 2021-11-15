package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTest {

    @Test
    void scared() {
        Sprite normalSprite = new Rectangle(Color.RED);
        Sprite scaredSprite = new Rectangle(Color.BLUE);

        Ghost ghost = new Ghost("Ghost", null, normalSprite, scaredSprite, null);

        assertFalse(ghost.isScared());
        assertEquals(normalSprite, ghost.getSprite());

        ghost.setScared(true);

        assertTrue(ghost.isScared());
        assertEquals(scaredSprite, ghost.getSprite());
    }
}