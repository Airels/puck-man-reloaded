package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTest {

    @Test
    void state() {
        Sprite normalSprite = new Rectangle(Color.RED);
        Sprite scaredSprite = new Rectangle(Color.BLUE);

        Ghost ghost = null; // new Ghost("Ghost", null, Arrays.asList(normalSprite), scaredSprite, null, 2.0, 1.0, 4.0);

        assertFalse(ghost.isScared());
        assertEquals(normalSprite, ghost.getSprite());

        ghost.setState(GhostState.SCARED);

        assertTrue(ghost.isScared());
        assertEquals(scaredSprite, ghost.getSprite());
    }
}